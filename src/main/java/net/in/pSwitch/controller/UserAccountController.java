package net.in.pSwitch.controller;

import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.dto.AttachmentDTO;
import net.in.pSwitch.dto.FundRequestDTO;
import net.in.pSwitch.dto.RejectDocumentDTO;
import net.in.pSwitch.dto.UserBankDetailsDTO;
import net.in.pSwitch.dto.UserProfileDTO;
import net.in.pSwitch.dto.UserProfileDocumentDTO;
import net.in.pSwitch.dto.UserProfilePasswordDTO;
import net.in.pSwitch.dto.UserRegistrationDTO;
import net.in.pSwitch.model.Attachment;
import net.in.pSwitch.model.CompanyBankDetails;
import net.in.pSwitch.model.FileDB;
import net.in.pSwitch.model.FundRequest;
import net.in.pSwitch.model.MPIN;
import net.in.pSwitch.model.Product;
import net.in.pSwitch.model.Status;
import net.in.pSwitch.model.Transaction;
import net.in.pSwitch.model.UserBankDetails;
import net.in.pSwitch.model.UserInfo;
import net.in.pSwitch.repository.AttachmentRepository;
import net.in.pSwitch.repository.CompanyBankDetailsRepository;
import net.in.pSwitch.repository.FundRequestRepository;
import net.in.pSwitch.repository.MPINRepository;
import net.in.pSwitch.repository.ProductRepository;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.TransactionRepository;
import net.in.pSwitch.repository.UserBankDetailsRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.FileStorageService;
import net.in.pSwitch.service.UserDetailsImpl;
import net.in.pSwitch.service.UtilService;
import net.in.pSwitch.utility.SMSManager;
import net.in.pSwitch.utility.StringLiteral;
import net.in.pSwitch.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserAccountController {

	Logger logger = LoggerFactory.getLogger(UserAccountController.class);
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private UserBankDetailsRepository userBankDetailsRepository;
	@Autowired
	private AttachmentRepository attachmentRepository;
	@Autowired
	private FileStorageService storageService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private FundRequestRepository fundRequestRepository;
	@Autowired
	private BinderService binderService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UtilService utilService;
	@Autowired
	private MPINRepository mpinRepository;
	@Autowired
	private CompanyBankDetailsRepository companyBankDetailsRepository;
	

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private SMSManager smsManager;

	@GetMapping("/setMPIN")
	public String getMPIN(Model model) {
		return "redirect:/profile";
	}

	@PostMapping("/setMPIN")
	public String setMPIN(Model model, String mpin, String mpin_otp, @LoginUser LoginUserInfo loginUserInfo) {

		model = binderService.bindUserDetails(model,loginUserInfo);
		UserInfo userInfo = (UserInfo) model.getAttribute("user");

		boolean isValid = true;
		List<String> errorMessage = new ArrayList<>();
		if (mpin == null || mpin.length() != 6) {
			isValid = false;
			errorMessage.add("MPIN must be 6 numbers long");
		}

		if (mpin_otp == null || mpin_otp.length() != 6) {
			isValid = false;
			errorMessage.add("Invalid OTP");
		}
		MPIN mMpin = mpinRepository.findByUser(userInfo);
		if (mMpin != null && (mMpin.getPin() == null || mMpin.getPin().length() == 0)) {

			if (utilService.encodedData(mpin_otp).equals(mMpin.getResetOTP())) {
				mMpin.setPin(utilService.encodedData("" + mpin));
				mMpin.setUser(userInfo);
				mpinRepository.save(mMpin);
				model.addAttribute("updateMessage", "MPIN Set successfully");
			} else {
				isValid = false;
				errorMessage.add("Invalid OTP, Please check your registered mobile message for otp");
			}

		} else {
			isValid = false;
			errorMessage.add("MPIN already set, if you want you can reset it.");
		}

		if (!isValid) {
			model.addAttribute("updateError", errorMessage);
		}

		model.addAttribute("mpinSet", mMpin != null && mMpin.getPin() != null);

		return "/profile";
	}

	@PostMapping("/resetMPIN")
	public String resetMPIN(Model model, String old_mpin, String mpin, String mpin_otp, @LoginUser LoginUserInfo loginUserInfo) {

		model = binderService.bindUserDetails(model,loginUserInfo);
		UserInfo userInfo = (UserInfo) model.getAttribute("user");

		boolean isValid = true;
		List<String> errorMessage = new ArrayList<>();
		if (mpin == null || mpin.length() != 6) {
			isValid = false;
			errorMessage.add("MPIN must be 6 numbers long");
		}

		if (mpin_otp == null || mpin_otp.length() != 6) {
			isValid = false;
			errorMessage.add("Invalid OTP");
		}
		MPIN mMpin = mpinRepository.findByUser(userInfo);
		if (mMpin != null && utilService.encodedData(old_mpin).equals(mMpin.getPin())) {

			if (utilService.encodedData(mpin_otp).equals(mMpin.getResetOTP())) {
				mMpin.setPin(utilService.encodedData("" + mpin));
				mMpin.setUser(userInfo);
				mpinRepository.save(mMpin);
				model.addAttribute("updateMessage", "MPIN Set successfully");
			} else {
				isValid = false;
				errorMessage.add("Invalid OTP, Please check your registered mobile message for otp");
			}

		} else {
			isValid = false;
			errorMessage.add("Old Pin is invalid please try again");
		}

		if (!isValid) {
			model.addAttribute("updateError", errorMessage);
		}

		model.addAttribute("mpinSet", mMpin != null && mMpin.getPin() != null);

		return "/profile";
	}

	@RequestMapping("/viewWallet")
	public String viewWallet(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		UserInfo userInfo = (UserInfo) model.getAttribute("user");
		List<Transaction> transactions = transactionRepository.searchUserTransactions(userInfo);
		model.addAttribute("transactions", transactions);
		
		
		
		return "viewWallet";
	}

	@RequestMapping("/beneficiary")
	public String beneficiary(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		return "beneficiary";
	}

	@RequestMapping("/commission")
	public String commission(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		return "commission";
	}

	@RequestMapping("/remitter")
	public String remitter(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		return "remitter";
	}

	@RequestMapping("/retailers")
	public String retailers(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		return "retailers";
	}

	@RequestMapping("/userList/{role}")
	public String userList(Model model, @PathVariable("role") String role, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		List<UserInfo> list = userInfoRepository.findByRoles(roleRepository.findByRoleCode(role));
		model.addAttribute("userList", list);

		return "users";
	}

	@RequestMapping("/fundRequestList")
	public String productList(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		UserInfo userInfo = (UserInfo) model.getAttribute("user");
		List<FundRequest> list = fundRequestRepository.findByUser(userInfo);
		model.addAttribute("fundRequestList", list);
//		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_FUND_REQUEST);
		return "fundRequest/requestList";
	}

	@RequestMapping("/addFund")
	public String addFund(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_USER);

		UserInfo userInfo = (UserInfo) model.getAttribute("user");
		if (StringLiteral.ROLE_CODE_RETAILER.equals(userInfo.getRoles().getRoleCode())) {
			return "addFundRetailer";
		}

		model.addAttribute("countryBankList", companyBankDetailsRepository.findAll());
		return "add-fund";
	}

	@RequestMapping("/editFundRequest/{fundRequestId}")
	public String editFundRequest(Model model, @PathVariable("fundRequestId") Integer fundRequestId, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_USER);
		Optional<FundRequest> fundRequestOptional = fundRequestRepository.findById(fundRequestId);
		if (fundRequestOptional.isPresent()) {
			model.addAttribute("fundRequest", fundRequestOptional.get());
		}
		UserInfo userInfo = (UserInfo) model.getAttribute("user");
		if (StringLiteral.ROLE_CODE_RETAILER.equals(userInfo.getRoles().getRoleCode())) {
			return "addFundRetailer";
		}
		return "add-fund";
	}

	@RequestMapping("/addFundRequest")
	public String addFundRequest(Model model, FundRequestDTO fundRequestDTO, @LoginUser LoginUserInfo loginUserInfo) {
//		return "fundRequestList";

		model = binderService.bindUserDetails(model,loginUserInfo);
		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<FundRequestDTO>> violations = validator.validate(fundRequestDTO);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<FundRequestDTO> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		if (isValid) {
			FileDB reciptImageFile = null;
			if (fundRequestDTO.getReciptImage() != null && fundRequestDTO.getReciptImage().getOriginalFilename() != null
					&& !"".equals(fundRequestDTO.getReciptImage().getOriginalFilename())) {
				try {
					reciptImageFile = storageService.store(fundRequestDTO.getReciptImage());
					if (reciptImageFile != null) {
						logger.info("Uploaded the file successfully: " + reciptImageFile.getName());
					} else {
						logger.error("Could not upload the file: "
								+ fundRequestDTO.getReciptImage().getOriginalFilename() + "!");
					}
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}

			FundRequest fundRequest = null;
			if (fundRequestDTO.getFundRequestId() != null) {
				Optional<FundRequest> optional = fundRequestRepository
						.findById(Integer.parseInt(fundRequestDTO.getFundRequestId()));
				if (optional != null) {
					fundRequest = optional.get();
				}
			}

			if (fundRequest == null) {
				fundRequest = new FundRequest();
			}

			UserInfo userInfo = (UserInfo) model.getAttribute("user");
			if (!StringLiteral.ROLE_CODE_RETAILER.equals(userInfo.getRoles().getRoleCode())
					&& fundRequestDTO.getReciptImage() == null && reciptImageFile == null) {
				errorMessage.add("Recipt Image File cannot be blank");
			} else {

				fundRequest.setDmtAndBillPayment(fundRequestDTO.getDmtAndBillPayment());
				fundRequest.setReciptImage(
						reciptImageFile != null ? reciptImageFile.getId() : fundRequestDTO.getReciptImageId());
				fundRequest.setePolicy(fundRequestDTO.getePolicy());
				fundRequest.setGst(fundRequestDTO.getGst());
				fundRequest.setNepalMoneyTransfer(fundRequestDTO.getNepalMoneyTransfer());
				fundRequest.setPancard(fundRequestDTO.getPancard());
				fundRequest.setPancardNo(fundRequestDTO.getPancardNo());
				fundRequest.setPaymentDate(fundRequestDTO.getPaymentDate());
				fundRequest.setReciptNo(fundRequestDTO.getReciptNo());
				fundRequest.setTransactionType(fundRequestDTO.getTransactionType());
				fundRequest.setTotalAmount(fundRequestDTO.getTotalAmount());
				fundRequest.setUser(userInfo);
				fundRequest.setStatus(Status.PENDING);
				fundRequest.setParent(userInfo.getParent());

				CompanyBankDetails bankDetails = null;
				Optional<CompanyBankDetails> optional = companyBankDetailsRepository
						.findById(Integer.parseInt(fundRequestDTO.getBankDetailsId()));
				if (optional.isPresent())
					bankDetails = optional.get();

				fundRequest.setCompanyBankDetails(bankDetails);

//				fundRequest.set(fundRequestDTO.getTotalAmount());

//				product.setProductName(fundRequestDTO.getProductName());
//				product.setProductDescription(fundRequestDTO.getProductDescription());
//				product.setProductType(productTypeRepository.findById(fundRequestDTO.getProductType()).get());
//				product.setProductImageFile(
//						productImageFile != null ? productImageFile.getId() : fundRequestDTO.getProductImageFileId());
//				product.setBrochureFile(brochureFile != null ? brochureFile.getId() : fundRequestDTO.getBrochureFileId());
//
////			product.setCommissionFile(commissionFile.getId());
//				product.setAmount(fundRequestDTO.getAmount());
//				product.setMRP(fundRequestDTO.getMRP());
//				product.setAdminMenu(fundRequestDTO.getAdminMenu());
//				product.setRetailerMenu(fundRequestDTO.getRetailerMenu());
//				product.setDistributorMenu(fundRequestDTO.getDistributorMenu());
//				product.setSuperDistributorMenu(fundRequestDTO.getSuperDistributorMenu());
//				product.setBusinessAssociateMenu(fundRequestDTO.getBusinessAssociateMenu());

				fundRequestRepository.save(fundRequest);

				/*
				 * if (fundRequestDTO.getId() == null) { ProductNews news = new ProductNews();
				 * news.setNews("New Product is added into " +
				 * product.getProductType().getName() + " with name: " +
				 * product.getProductName()); news.setActive(1); newsRepository.save(news); }
				 */

				return "redirect:/user/fundRequestList/";
			}
		}
		model = binderService.bindUserDetails(model, loginUserInfo);
		model.addAttribute("countryBankList", companyBankDetailsRepository.findAll());
		return "add-fund";

	}

	@RequestMapping("/verifyContactNo")
	public String verifyContactNo(Model model, String userId, String otp, @LoginUser LoginUserInfo loginUserInfo) {

		if (userId != null && otp != null) {
//			Optional<UserInfo> optional = userInfoRepository.findById(Integer.parseInt(userId));
//			if (optional.isPresent()) {
				UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);
				if (utilService.decodedData(userInfo.getContactOTP()).equals(otp)) {
					userInfo.setContactVerified(1l);
				} else {
					userInfo.setContactVerified(0l);
				}
				userInfoRepository.save(userInfo);
//			}
		}

		return "redirect:/profile";
	}

	@RequestMapping("/generateOTP")
	@ResponseBody
	public String generateOTP(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);

		UserInfo userInfo = (UserInfo) model.getAttribute("user");
		if (userInfo.getMobileNumber() != null) {
			userInfo.setContactOTP(utilService.encodedData(smsManager.sendOTP(userInfo.getMobileNumber().trim())));
			userInfoRepository.save(userInfo);
		}
		return "success";
	}

	@RequestMapping("/generatePasswordOTP")
	@ResponseBody
	public String generatePasswordOTP(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);

		UserInfo userInfo = (UserInfo) model.getAttribute("user");
		if (userInfo.getMobileNumber() != null) {
			userInfo.setPasswordResetOTP(Long.parseLong(smsManager.sendOTP(userInfo.getMobileNumber().trim())));
			userInfoRepository.save(userInfo);
		}
		return "success";
	}

	@RequestMapping("/generateMPINOTP")
	@ResponseBody
	public String generateMPINOTP(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);

		UserInfo userInfo = (UserInfo) model.getAttribute("user");
		if (userInfo.getMobileNumber() != null && userInfo.getContactVerified() != null
				&& 1l == userInfo.getContactVerified()) {

			MPIN mpin = mpinRepository.findByUser(userInfo);
			if (mpin == null) {
				mpin = new MPIN();
				mpin.setUser(userInfo);
			}

			mpin.setResetOTP(utilService.encodedData(smsManager.sendOTP(userInfo.getMobileNumber().trim())));
			mpinRepository.save(mpin);
			return "success";
		} else {
			return "Contact is not verified";
		}

	}

	@RequestMapping("/ProductsList")
	public String userList(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		List<Product> list = productRepository.findAll();
		model.addAttribute("productsList", list);

		return "all-products";
	}

	@GetMapping("/updateProfileInfo")
	public String getupdateProfile() {
		return "redirect:/profile";
	}

	@GetMapping("/updateProfilePassword")
	public String getUpdateProfilePassword() {
		return "redirect:/profile";
	}

	@GetMapping("/updateProfileDocument")
	public String getUpdateProfileDocument() {
		return "redirect:/profile";
	}

	@GetMapping("/sendForApproval")
	public String getsendForApproval() {
		return "redirect:/profile";
	}

	@PostMapping("/rejectDocument")
	public String rejectDocument(Model model, RejectDocumentDTO rejectDocumentDTO) {

		Optional<Attachment> optional = attachmentRepository
				.findById(Integer.parseInt(rejectDocumentDTO.getAttachmentId()));

		if (optional != null && optional.get() != null) {
			Attachment attachment = optional.get();
			attachment.setDocumentDescription(rejectDocumentDTO.getRejectionReason());
			attachment.setStatus(Status.REJECTED);
			attachmentRepository.save(attachment);
		}

		return "redirect:/userProfile/" + rejectDocumentDTO.getUserId();
	}

	@GetMapping("/deleteDocument/{attachmentId}")
	public String deleteDocument(Model model, @PathVariable("attachmentId") Integer attachmentId) {
		Optional<Attachment> optional = attachmentRepository.findById(attachmentId);
		if (optional != null) {
			Attachment attachment = optional.get();
			storageService.deleteFile(attachment.getFile());
			attachmentRepository.deleteById(attachment.getId());
		}
		return "redirect:/profile";
	}

	@PostMapping("/sendForApproval")
	public String sendForApproval(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
//		Optional<UserInfo> userOptional = userInfoRepository.findById(Integer.parseInt(userId));
//		if (userOptional != null) {
//			UserInfo userProfile = userOptional.get();
			UserInfo userProfile = binderService.getCurrentUser(loginUserInfo);
			userProfile.setAgreementAccept(1l);
			userInfoRepository.save(userProfile);
			model.addAttribute("updateMessage", "Aggrement Accept successfully");
//		} else {
//			List<String> errorMessage = new ArrayList<>();
//			errorMessage.add("user not found, please try again later");
//			model.addAttribute("updateError", errorMessage);
//		}
		return "profile";
	}

	@PostMapping("/updateUserBankDetails")
	public String updateUserBankDetails(Model model, UserBankDetailsDTO bankDetailsDto, @LoginUser LoginUserInfo loginUserInfo) {
		String page = "profile";

		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UserBankDetailsDTO>> violations = validator.validate(bankDetailsDto);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<UserBankDetailsDTO> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		if (!isValid) {
			model.addAttribute("updateError", errorMessage);
		} else {
//			Optional<UserInfo> userOptional = userInfoRepository.findById(Integer.parseInt(bankDetailsDto.getUserId()));
//			if (userOptional != null) {
//				UserInfo userProfile = userOptional.get();
				UserInfo userProfile = binderService.getCurrentUser(loginUserInfo);
				UserBankDetails bankDetails = new UserBankDetails();
				bankDetails.setAccountHolderName(bankDetailsDto.getAccountHolderName());
				bankDetails.setAccountNumber(bankDetailsDto.getAccountNumber());
				bankDetails.setBankName(bankDetailsDto.getBANK_NAME());
				bankDetails.setIfscCode(bankDetailsDto.getIFSC_CODE());
				bankDetails.setUserInfo(userProfile);
				userBankDetailsRepository.save(bankDetails);
				userProfile.setBankDetails(bankDetails);

				userInfoRepository.save(userProfile);
				model.addAttribute("updateMessage", "Bank Details update successfully");
//			}
		}

		model = binderService.bindUserDetails(model, loginUserInfo);
		return page;
	}

	@PostMapping("/updateAttachment")
	public String updateAttachment(Model model, AttachmentDTO attachmentDTO, @LoginUser LoginUserInfo loginUserInfo) {

		String page = "profile";

		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<AttachmentDTO>> violations = validator.validate(attachmentDTO);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<AttachmentDTO> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		if (isValid) {

			switch (attachmentDTO.getDocumentType()) {
			case PANCARD:
				if (Utility.isValidPanCardNo(attachmentDTO.getDocumentNumber())) {
					isValid = false;
					errorMessage.add("Invalid Pancard Number");
				}
				break;
			case AADHAAR:
				if (Utility.isValidAadharNumber(attachmentDTO.getDocumentNumber())) {
					isValid = false;
					errorMessage.add("Invalid Aadhar Number");
				}
				break;
			case VOTERID:
				break;
			case DRIVINGLICENCE:
				break;
			}
		}

		if (isValid) {
			FileDB fileDBDocument = null;
			if (attachmentDTO.getImgDocument() != null && attachmentDTO.getImgDocument().getOriginalFilename() != null
					&& !"".equals(attachmentDTO.getImgDocument().getOriginalFilename())) {
				try {
					fileDBDocument = storageService.store(attachmentDTO.getImgDocument());
					if (fileDBDocument != null) {
						logger.info("Uploaded the file successfully: " + fileDBDocument.getName());
					} else {
						logger.error("Could not upload the file: "
								+ attachmentDTO.getImgDocument().getOriginalFilename() + "!");
					}
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}
//			Optional<UserInfo> userOptional = userInfoRepository.findById(Integer.parseInt(attachmentDTO.getUserId()));
//			if (userOptional != null) {
//				UserInfo userProfile = userOptional.get();
				UserInfo userProfile = binderService.getCurrentUser(loginUserInfo);
				boolean update = false;

				if (fileDBDocument != null) {
					Attachment attachment = new Attachment();
					attachment.setFile(fileDBDocument.getId());
					attachment.setDocumentNumber(attachmentDTO.getDocumentNumber());
					attachment.setUserInfo(userProfile);
					attachment.setDocumentType(attachmentDTO.getDocumentType());
					attachment.setStatus(Status.PENDING);
					attachment.setDocumentDescription("");
					attachmentRepository.save(attachment);
					update = true;
				}

				if (update) {
//					userProfile.setPancardNumber(attachmentDTO.getPancardNumber());
//					userProfile.setAadhaarNumber(attachmentDTO.getAadhaarNumber());
					userInfoRepository.save(userProfile);
					model.addAttribute("updateMessage", "Document update successfully");
					page = "redirect:/profile";
				} else {
					errorMessage.add("No Document found, Please select document for update");
					model.addAttribute("updateError", errorMessage);
				}
//			}
		} else {
			model.addAttribute("updateError", errorMessage);
		}

		model = binderService.bindUserDetails(model, loginUserInfo);
		return page;
	}

	@PostMapping("/updateProfileDocument")
	public String updateProfileDocument(Model model, @Valid UserProfileDocumentDTO documentDto, @LoginUser LoginUserInfo loginUserInfo) {

		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UserProfileDocumentDTO>> violations = validator.validate(documentDto);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<UserProfileDocumentDTO> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		if (isValid) {
			FileDB fileDBAadhaarCard = null;
			if (documentDto.getImgAadhaarCard() != null && documentDto.getImgAadhaarCard().getOriginalFilename() != null
					&& !"".equals(documentDto.getImgAadhaarCard().getOriginalFilename())) {
				try {
					fileDBAadhaarCard = storageService.store(documentDto.getImgAadhaarCard());
					if (fileDBAadhaarCard != null) {
						logger.info("Uploaded the file successfully: " + fileDBAadhaarCard.getName());
					} else {
						logger.error("Could not upload the file: "
								+ documentDto.getImgAadhaarCard().getOriginalFilename() + "!");
					}
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}

			FileDB fileDBPancard = null;
			if (documentDto.getImgPancard() != null && documentDto.getImgPancard().getOriginalFilename() != null
					&& !"".equals(documentDto.getImgPancard().getOriginalFilename())) {
				try {
					fileDBPancard = storageService.store(documentDto.getImgPancard());
					if (fileDBPancard != null) {
						logger.info("Uploaded the file successfully: " + fileDBPancard.getName());
					} else {
						logger.error("Could not upload the file: " + documentDto.getImgPancard().getOriginalFilename()
								+ "!");
					}
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}

			Optional<UserInfo> userOptional = userInfoRepository.findById(Integer.parseInt(documentDto.getUserId()));
			if (userOptional != null) {
				UserInfo userProfile = userOptional.get();
				boolean update = false;
//				if (fileDBPancard != null) {
//					userProfile.setPancardPic(fileDBPancard.getId());
//					update = true;
//				}
//
//				if (fileDBAadhaarCard != null) {
//					userProfile.setAadhaarCardPic(fileDBAadhaarCard.getId());
//					update = true;
//				}

				if (update) {
//					userProfile.setPancardNumber(documentDto.getPancardNumber());
//					userProfile.setAadhaarNumber(documentDto.getAadhaarNumber());
					userInfoRepository.save(userProfile);
					model.addAttribute("updateMessage", "Document update successfully");
				} else {
					errorMessage.add("No Document found, Please select document for update");
					model.addAttribute("updateError", errorMessage);
				}
			}
		} else {
			model.addAttribute("updateError", errorMessage);
		}

		model = binderService.bindUserDetails(model, loginUserInfo);
		return "profile";
	}

	@PostMapping("/updateProfilePassword")
	public String updateProfilePassword(Model model, @Valid UserProfilePasswordDTO passwordDto, @LoginUser LoginUserInfo loginUserInfo) {

		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UserProfilePasswordDTO>> violations = validator.validate(passwordDto);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<UserProfilePasswordDTO> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		if (!isValid) {
			model.addAttribute("updateError", errorMessage);
		} else if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {
			errorMessage.add("Password and confirm password fields do not match");
			model.addAttribute("updateError", errorMessage);
		} else if (passwordDto.getNewPassword().length() < 6 || passwordDto.getNewPassword().length() > 32) {
			errorMessage.add("Password must be between 2 and 32 characters");
			model.addAttribute("updateError", errorMessage);
		} else {
//			Optional<UserInfo> userOptional = userInfoRepository.findById(Integer.parseInt(passwordDto.getUserId()));
//			if (userOptional != null) {
//				UserInfo userProfile = userOptional.get();
				UserInfo userProfile = binderService.getCurrentUser(loginUserInfo);
				if (userProfile.getPasswordResetOTP().equals(passwordDto.getPwd_otp())) {
					userProfile.setPwd(utilService.encodedData(passwordDto.getNewPassword()));
					userInfoRepository.save(userProfile);
					model.addAttribute("updateMessage", "Password update successfully");
				} else {
					errorMessage.add("Invalid OTP, Please enter valid OTP from registered mobile number");
					model.addAttribute("updateError", errorMessage);
				}
//			}
		}

		model = binderService.bindUserDetails(model, loginUserInfo);
		return "profile";
	}

	@PostMapping("/updateProfileInfo")
	public String updateProfile(HttpServletRequest request,
			@Valid @ModelAttribute("user") UserProfileDTO userProfileDto, BindingResult bindingResult, Model model, @LoginUser LoginUserInfo loginUserInfo) {

		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UserProfileDTO>> violations = validator.validate(userProfileDto);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<UserProfileDTO> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		if (!isValid) {
			model.addAttribute("updateError", errorMessage);
		} else {
			FileDB fileDB = null;
			if (userProfileDto.getImgProfile() != null && userProfileDto.getImgProfile().getOriginalFilename() != null
					&& !"".equals(userProfileDto.getImgProfile().getOriginalFilename())) {
				try {
					fileDB = storageService.store(userProfileDto.getImgProfile());
					if (fileDB != null) {
						logger.info("Uploaded the file successfully: " + fileDB.getName());
					} else {
						logger.error("Could not upload the file: "
								+ userProfileDto.getImgProfile().getOriginalFilename() + "!");
					}
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}
//			Optional<UserInfo> userOptional = userInfoRepository.findById(Integer.parseInt(userProfileDto.getUserId()));

//			if (userOptional != null) {
//				UserInfo userProfile = userOptional.get();
				UserInfo userProfile = binderService.getCurrentUser(loginUserInfo);
				userProfile.setAddress(userProfileDto.getAddress());
				userProfile.setCity(Long.parseLong(userProfileDto.getCity()));
				userProfile.setUsername(userProfileDto.getUsername());
				userProfile.setFirstName(userProfileDto.getFirstName());
				userProfile.setLastName(userProfileDto.getLastName());

				userProfile.setGstNo(userProfileDto.getGstNo());
				userProfile.setCompanyName(userProfileDto.getCompanyName());

				if (userProfile.getMobileNumber() != userProfileDto.getMobileNumber()) {
					userProfile.setContactVerified(0l);
				}

				userProfile.setMobileNumber(userProfileDto.getMobileNumber());
				userProfile.setState(Long.parseLong(userProfileDto.getState()));
				userProfile.setCountry(Long.parseLong(userProfileDto.getCountry()));
				userProfile.setZipcode(userProfileDto.getZipcode());

				if (fileDB != null) {
					userProfile.setProfilePicture(fileDB.getId());
				}
				logger.info("userprofile", userProfile);
				userInfoRepository.save(userProfile);
				SecurityContext context = SecurityContextHolder.getContext();
				Authentication authentication = context.getAuthentication();
				if (authentication instanceof UsernamePasswordAuthenticationToken) {
					UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
					auth.setDetails(new UserDetailsImpl(userProfile));
					request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
							SecurityContextHolder.getContext());
					SecurityContextHolder.getContext()
							.setAuthentication(SecurityContextHolder.getContext().getAuthentication());
				}
//				utilService.clearAllCache();
				model.addAttribute("updateMessage", "Profile update successfully");
//			}
		}

		model = binderService.bindUserDetails(model, loginUserInfo);
		UserInfo userInfo = (UserInfo) model.getAttribute("user");
		model.addAttribute("mpinSet", mpinRepository.findByUser(userInfo) != null);
		return "profile";
	}

	@GetMapping("/changePassword")
	public String changePassword(Model model, @RequestParam("token") String token) {
		String result = utilService.validatePasswordResetToken(token);
		if (result != null && !result.equalsIgnoreCase("Success")) {
//			String message = messages.getMessage("auth.message." + result, null, locale);
//			return "redirect:/login.html?&message=" + result;
			model.addAttribute("errorMsg", result);
			model.addAttribute("loginTab", "show active");
			model.addAttribute("changePassword", "");
		} else {
			model.addAttribute("token", token);
			model.addAttribute("loginTab", "");
			model.addAttribute("changePassword", "show active");
		}

		model.addAttribute("user", new UserRegistrationDTO());
		model.addAttribute("rolesList", roleRepository.findByRoleCodeNot(StringLiteral.ROLE_CODE_ADMIN));
		model.addAttribute("resetPasswordTab", "");
		model.addAttribute("registerTab", "");

		return "loginPage/signIn";
	}
}
