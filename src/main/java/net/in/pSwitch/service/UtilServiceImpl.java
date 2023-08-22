package net.in.pSwitch.service;

import net.in.pSwitch.model.EmailDetails;
import net.in.pSwitch.model.PasswordResetToken;
import net.in.pSwitch.model.Product;
import net.in.pSwitch.model.States;
import net.in.pSwitch.model.user.UserInfo;
import net.in.pSwitch.model.VerificationToken;
import net.in.pSwitch.repository.PasswordResetRepository;
import net.in.pSwitch.repository.StatesRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.utility.SMSManager;
import net.in.pSwitch.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UtilServiceImpl implements UtilService {

	Logger logger = LoggerFactory.getLogger(UtilServiceImpl.class);
	@Autowired
	private Environment env;
	@Autowired
	private PasswordResetRepository mPasswordResetRepository;
	@Autowired
	private StatesRepository statesRepository;
	@Autowired
	private EmailService emailService;
	private static SecretKeySpec secretKey;
	private static byte[] key;
	private static final String ALGORITHM = "AES";
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private SMSManager smsManager;

	@PostConstruct
	public void prepareSecreteKey() {
		MessageDigest sha = null;
		try {
			key = ".#FD4Y';KXOpQL3j!+%^:6_{?8Q^m,".getBytes(StandardCharsets.UTF_8);
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error: {}", e);
		}
	}

	@Override
	public void shareProduct(Product product, List<String> email) {

		try {
			String subject = "pSwtich's Product: " + product.getProductName();
			String senderName = "pSwitch Team";

			String brochureFile = env.getProperty("my.server.name") + "/files/" + product.getBrochureFile();
			String productImage = env.getProperty("my.server.name") + "/files/" + product.getProductImageFile();

			String mailContent = "";
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("templates/email/shareProduct.html");
			try {
				mailContent = readFromInputStream(inputStream);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
			mailContent = mailContent.replaceAll("#PRODUCT_IMAGE#", productImage);
			mailContent = mailContent.replaceAll("#PRODUCT_NAME#", product.getProductName());
			mailContent = mailContent.replaceAll("#PRODUCT_DESCRIPTION#", product.getProductDescription());
			mailContent = mailContent.replaceAll("#PRODUCT_BROCHURE_FILE#", brochureFile);
			mailContent = mailContent.replaceAll("#PRODUCT_MRP#",
					product.getMRP() == null ? "NA" : "" + product.getMRP());

			EmailDetails emailDetails = new EmailDetails();
			emailDetails.setMsgBody(mailContent);
			emailDetails.setRecipient(email.stream().collect(Collectors.joining(",")));
			emailDetails.setSubject(subject);
			emailService.sendSimpleMail(emailDetails);

//			MimeMessage message = mailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(message);
//			helper.setFrom("info@pswitch.in.net", senderName);
//			helper.setTo(email.stream().toArray(String[]::new));
//			helper.setSubject(subject);
//			helper.setText(mailContent, true);
//			mailSender.send(message);
//		} catch (MailException e) {
//			logger.error("Error: ", e);
//		} catch (UnsupportedEncodingException e) {
//			logger.error("Error: ", e);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	@Override
	public void sendAccountCreatedMail(UserInfo userInfo) {
		try {
			String subject = "pSwitch Account Created Successfully";
			String senderName = "pSwitch Team";

			String loginUrl = env.getProperty("my.server.name") + "/login";

//			String password = RandomString.make(16);
			Optional<States> optionalStates = statesRepository.findById(userInfo.getBusinessDetails().getState());
			String pSwitchUserId = Utility.getPSwitchUserId(optionalStates.get().getName(),
					userInfo.getRoles().getRoleCode(), userInfo.getUserId());

//			userInfo.setPwd(passwordEncoder.encode(password));
			userInfo.setUserPSwitchId(pSwitchUserId);
			userInfoRepository.save(userInfo);

			String mailContent = "";
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader
					.getResourceAsStream("templates/email/emailAccountCreatedTemplate.html");
			try {
				mailContent = readFromInputStream(inputStream);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
			mailContent = mailContent.replaceAll("loginUrl", loginUrl);
			mailContent = mailContent.replaceAll("USER-NAME", pSwitchUserId);
			mailContent = mailContent.replaceAll("PASS-WORD", "**********");
			mailContent = mailContent.replaceAll("USERNAME", userInfo.getFullName());
			
			logger.info("sendMail start");
			sendMail(userInfo.getUsername(),subject, mailContent);
			logger.info("sendMail End");
			
		} catch (MailException e) {
			logger.error("Error: ", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Error: ", e);
		} catch (MessagingException e) {
			logger.error("Error: ", e);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	@Override
	public String encodedData(String data){
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
		} catch (Exception e) {
			logger.error("Error: {}", e);
		}
		return null;
	}
	@Override
	public String decodedData(String data){
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes("UTF-8"))));
		} catch (Exception e) {
			logger.error("Error: {}", e.getLocalizedMessage());
		}
		return null;
	}

	@Override
	public void sendVerificationMail(UserInfo userInfo) {
		try {
			logger.info("sendVerificationMail");
			String subject = "Please verify your registration";
			String senderName = "pSwitch Team";

			String mailContent = "";
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("templates/email/emailVerificationTemplate.html");
			try {
				mailContent = readFromInputStream(inputStream);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
			mailContent = mailContent.replaceAll("VerificationCode", decodedData(userInfo.getVerificationCode()));
			mailContent = mailContent.replaceAll("USERNAME", userInfo.getFirstName());
			logger.info("sendMail start");
			sendMail(userInfo.getUsername(),subject, mailContent);
			logger.info("sendMail End");
		} catch (MailException e) {
			logger.error("Error: ", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Error: ", e);
		} catch (MessagingException e) {
			logger.error("Error: ", e);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	@Override
	public void sendOTPMail(UserInfo userInfo) {
		try {
			logger.info("sendVerificationMail");
			String subject = "Please verify your registration";

			String mailContent = "";
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("templates/email/otp.html");
			try {
				mailContent = readFromInputStream(inputStream);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
			mailContent = mailContent.replaceAll("#OTPCODE#", decodedData(userInfo.getVerificationCode()));
			mailContent = mailContent.replaceAll("#USERNAME#", userInfo.getFirstName());
			logger.info("sendMail start");
			sendMail(userInfo.getUsername(),subject, mailContent);
			logger.info("sendMail End");
		} catch (MailException e) {
			logger.error("Error: ", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Error: ", e);
		} catch (MessagingException e) {
			logger.error("Error: ", e);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	public void sendLoginDetailsEmail(UserInfo userInfo) {
		try {
			logger.info("sendLoginDetailsEmail");
			String subject = "pSwitch - Account Created";

			String mailContent = "";
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("templates/email/associateCredential.html");
			try {
				mailContent = readFromInputStream(inputStream);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
			mailContent = mailContent.replaceAll("#FNAME#", userInfo.getFirstName());
			mailContent = mailContent.replaceAll("#ROLE#", userInfo.getRoles().getRoleName());

			Optional<UserInfo> createdBy =  userInfoRepository.findById(userInfo.getUserMapping().getCreatedBy());

			mailContent = mailContent.replaceAll("#CREATEDBY#", createdBy.get().getFullName().trim());
			mailContent = mailContent.replaceAll("#PASSWORD#", decodedData(userInfo.getPwd()));
			logger.info("sendMail start");
			sendMail(userInfo.getUsername(),subject, mailContent);
			logger.info("sendMail End");
		} catch (MailException e) {
			logger.error("Error: ", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Error: ", e);
		} catch (MessagingException e) {
			logger.error("Error: ", e);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}
	
	@Override
	public void sendWelcomeEmail(UserInfo userInfo) {
		try {
			logger.info("sendWelcomeEmail");
			String subject = "Welcome to pSwitch";

			String mailContent = "";
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("templates/email/welcome.html");
			try {
				mailContent = readFromInputStream(inputStream);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
			mailContent = mailContent.replaceAll("#FNAME#", userInfo.getFirstName());
			mailContent = mailContent.replaceAll("#ROLE#", userInfo.getRoles().getRoleName());
			mailContent = mailContent.replaceAll("#FULLNAME#", userInfo.getFullName());
			mailContent = mailContent.replaceAll("#MOBILE#", userInfo.getMobileNumber());
			mailContent = mailContent.replaceAll("#EMAIL#", userInfo.getUsername());
			logger.info("sendMail start");
			sendMail(userInfo.getUsername(),subject, mailContent);
			logger.info("sendMail End");
		} catch (MailException e) {
			logger.error("Error: ", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Error: ", e);
		} catch (MessagingException e) {
			logger.error("Error: ", e);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	@Override
	public void sendLoginDetailMail(UserInfo userInfo) {
		sendLoginDetailsEmail(userInfo);

		userInfo.setVerificationCode(encodedData(generateOTP()));
		userInfo.setContactOTP(encodedData(smsManager.sendOTP(userInfo.getMobileNumber().trim())));
		userInfoRepository.save(userInfo);
		sendOTPMail(userInfo);
	}
	@Override
	public void sendVerificationMobileAndEmail(UserInfo userInfo) {
		userInfo.setVerificationCode(encodedData(generateOTP()));
		userInfo.setContactOTP(encodedData(smsManager.sendOTP(userInfo.getMobileNumber().trim())));
		userInfo.setUserId(userInfoRepository.save(userInfo).getUserId());
		sendOTPMail(userInfo);
	}

	@Override
	public void sendKYCCompleteMail(UserInfo userInfo) {
		try {
			logger.info("sendVerificationMail");
			String subject = "Pswitch - KYC completed";

			String mailContent = "";
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("templates/email/kycCompletedMail.html");

			Optional<States> optionalStates = statesRepository.findById(userInfo.getBusinessDetails().getState());
			String pSwitchUserId = Utility.getPSwitchUserId(optionalStates.get().getName(),
					userInfo.getRoles().getRoleCode(), userInfo.getUserId());
			userInfo.setUserPSwitchId(pSwitchUserId);
			userInfoRepository.save(userInfo);
			try {
				mailContent = readFromInputStream(inputStream);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
			mailContent = mailContent.replaceAll("#PSWITCHID#", userInfo.getUserPSwitchId());
			mailContent = mailContent.replaceAll("#BUSINESSNAME#", userInfo.getBusinessDetails().getBusinessName());
			mailContent = mailContent.replaceAll("#ADDRESS#", userInfo.getBusinessDetails().getAddress());
			mailContent = mailContent.replaceAll("#EMAIL#", userInfo.getUsername());
			mailContent = mailContent.replaceAll("#MOBILENUMBER#", userInfo.getMobileNumber());
			logger.info("sendMail start");
			sendMail(userInfo.getUsername(),subject, mailContent);
			logger.info("sendMail End");
		} catch (MailException e) {
			logger.error("Error: ", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Error: ", e);
		} catch (MessagingException e) {
			logger.error("Error: ", e);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	@Override
	public String generateOTP() {
		int otpLength = 6; // length of the OTP
		String numbers = "0123456789"; // characters to be used for generating the OTP
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		// generate the OTP
		for (int i = 0; i < otpLength; i++) {
			int index = random.nextInt(numbers.length());
			char digit = numbers.charAt(index);
			sb.append(digit);
		}

		return sb.toString();
	}

	private void sendMail(String mailTo, String mailSubject,
			String mailBody) throws Exception {

		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setMsgBody(mailBody);
		emailDetails.setRecipient(mailTo);
		emailDetails.setSubject(mailSubject);
		emailService.sendSimpleMail(emailDetails);
//		Properties config = createConfiguration();
//		logger.info("config {}", config);

		// Creates a mail session. We need to supply username and
		// password for Gmail authentication.
//		Session session = Session.getInstance(config, new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(
//						"info@pswitch.in.net",
//						"9zX!PqqB$a%V"
//						);
//			}
//		});
//
//		logger.info("MimeMessage creating");
//		// Creates email message
//
//		MimeMessage message = new MimeMessage(session);
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//		helper.setFrom("info@pswitch.in.net", "pSwitch Team");
//		helper.setTo(mailTo);
//		helper.setSubject(mailSubject);
//		helper.setText(mailText, true);
//
//		logger.info("sending...");
//		// Send a message
//		Transport.send(message);
//		logger.info("Email Send");
	}

	@Override
	public void sendResetMail(UserInfo savedUserProfile, String token) {
		try {
			String subject = "Reset Account Password";
			String senderName = "pSwitch Team";
			String resetUrl = env.getProperty("my.server.name") + "/account/changePassword?token=" + token;
			String mailContent = "";
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("templates/email/emailResetTemplate.html");
			try {
				mailContent = readFromInputStream(inputStream);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
			mailContent = mailContent.replaceAll("resetUrl", resetUrl);
			mailContent = mailContent.replaceAll("USERNAME", savedUserProfile.getFirstName());

			logger.info("sendMail start");
			sendMail(savedUserProfile.getUsername(),subject, mailContent);
			logger.info("sendMail End");
		} catch (MailException e) {
			logger.error("Error: ", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Error: ", e);
		} catch (MessagingException e) {
			logger.error("Error: ", e);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	private String readFromInputStream(InputStream inputStream) throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	private SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final Locale locale,
			final VerificationToken newToken, final UserInfo user) {
		final String confirmationUrl = contextPath + "/registrationConfirm.html?token=" + newToken.getToken();
		final String message = "We will send an email with a new registration token to your email account";// messages.getMessage("message.resendToken",
		// null,
		// locale);
		return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
	}

	private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale,
			final String token, final UserInfo user) {
		final String url = contextPath + "/account/changePassword?token=" + token;
		final String message = "Reset Password";// messages.getMessage("message.resetPassword", null, locale);
		return constructEmail("Reset Password", message + " \r\n" + url, user);
	}

	private SimpleMailMessage constructEmail(String subject, String body, UserInfo user) {
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getUsername());
		email.setFrom(env.getProperty("support.email"));
		return email;
	}

	@Override
	public String validatePasswordResetToken(String token) {
		final PasswordResetToken passToken = mPasswordResetRepository.findByToken(token);

		return !isTokenFound(passToken) ? "Invalid token"
				: isTokenExpired(passToken) ? "Your registration token has expired. Please register again." : "Success";
	}

	private boolean isTokenFound(PasswordResetToken passToken) {
		return passToken != null;
	}

	private boolean isTokenExpired(PasswordResetToken passToken) {
		final Calendar cal = Calendar.getInstance();
		return passToken.getExpiryDate().before(cal.getTime());
	}

}
