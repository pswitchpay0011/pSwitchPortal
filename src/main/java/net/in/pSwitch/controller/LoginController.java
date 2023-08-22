package net.in.pSwitch.controller;

import net.in.pSwitch.authentication.CustomJwtAuthenticationFilter;
import net.in.pSwitch.authentication.JWTService;
import net.in.pSwitch.authentication.LoginCheck;
import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.dto.UserRegistrationDTO;
import net.in.pSwitch.model.PasswordResetToken;
import net.in.pSwitch.model.user.UserInfo;
import net.in.pSwitch.repository.PasswordResetRepository;
import net.in.pSwitch.repository.ProductRepository;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.UtilService;
import net.in.pSwitch.utility.LoginTracker;
import net.in.pSwitch.utility.SMSManager;
import net.in.pSwitch.utility.StringLiteral;
import net.in.pSwitch.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UtilService utilService;
    @Autowired
    private PasswordResetRepository mPasswordResetRepository;
    @Autowired
    private LoginTracker loginTracker;
    @Autowired
    private SMSManager smsManager;
    @Autowired
    private BinderService binderService;

    @Autowired
    private JWTService jwtService;

    @Value("${pSwitch.token.timeOutInMinutes}")
    private Integer tokenTimeout;

    @Value("${pSwitch.refreshToken.timeOutInMinutes}")
    private Integer refreshTokenTimeOut;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/userLogout")
    public String fetchSignOutSite(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(LoginCheck.COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/login";
    }

    @RequestMapping("/loginUser")
    public String loginUser(HttpServletRequest request, HttpServletResponse response,
                            Model model, @RequestParam String username,
                            @RequestParam String password,
                            @RequestParam String latLng,
                            @RequestParam(required = false) String remember) throws Exception {

        model.addAttribute("username", username);
        model.addAttribute("password", password);
        Authentication authentication = null;
        String errorMessage = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, password));
        } catch (DisabledException e) {
            errorMessage = "your is disabled please contact the support";
        } catch (BadCredentialsException e) {
            errorMessage = "Invalid Username and password";
        }
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                UserInfo userInfo = userInfoRepository.findUserForLogin(username);
                loginTracker.updateTracker(userInfo.getUserId(), latLng);
//                boolean isNotAdmin = !StringLiteral.ROLE_CODE_ADMIN.equalsIgnoreCase(userInfo.getRoles().getRoleCode());
//                if(isNotAdmin && (StringUtils.isEmpty(userInfo.getAccountState()) || StringLiteral.ACCOUNT_CREATION_STATE_STEP_1.equals(userInfo.getAccountState()))){
//                    utilService.sendVerificationMobileAndEmail(userInfo);
//                    return "loginPage/otp";
//                }else {


//                    logger.info(Inet4Address.getLocalHost().getHostAddress());
                    setCookie(userInfo, response);

                    return "redirect:/index";
                }
//            }
        } catch (Exception e) {
            logger.error("Error: {}", e);
            model.addAttribute("errorMsg", "Login failed please try again later");
        }
        model.addAttribute("user", new UserRegistrationDTO());
        model.addAttribute("loginTab", "show active");
        model.addAttribute("resetPasswordTab", "");
        model.addAttribute("registerTab", "");
        model.addAttribute("errorMsg", errorMessage);

        if (model.getAttribute("resetMessage") != null) {
            model.addAttribute("msg", model.getAttribute("resetMessage"));
        } else if (model.getAttribute("resetError") != null) {
            model.addAttribute("errorMsg", model.getAttribute("resetError"));
        }

        return "loginPage/signIn";
    }

    private void setCookie(UserInfo userInfo, HttpServletResponse response){
        binderService.bindCookieData(response);

        Map<String, Object> user = new HashMap<>();
        user.put("id", userInfo.getUserId());
        user.put("fullName", userInfo.getFullName());
        user.put("pSwitchId", userInfo.getUserPSwitchId());
        user.put("email", userInfo.getUsername());
        user.put("userRole", userInfo.getRoles());

        Cookie cookie = new Cookie(CustomJwtAuthenticationFilter.COOKIE_TOKEN,
                jwtService.token(user, Optional.of(LocalDateTime.now().plusMinutes(tokenTimeout))));
        UserDetails userDetails = new User(userInfo.getUsername(), "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userInfo.getRoles().getRoleCode())));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        cookie.setPath("/");
        cookie.setMaxAge(tokenTimeout * 60);

        response.addCookie(cookie);
        response.addCookie(new Cookie(CustomJwtAuthenticationFilter.COOKIE_REFRESH_TOKEN, jwtService.doGenerateRefreshToken(user, Optional.of(LocalDateTime.now().plusMinutes(refreshTokenTimeOut)))));

    }

    @GetMapping("/login")
    public String login(Model model) {

        String errorMessage = null;
        model.addAttribute("user", new UserRegistrationDTO());
        model.addAttribute("loginTab", "show active");
        model.addAttribute("resetPasswordTab", "");
        model.addAttribute("registerTab", "");
        model.addAttribute("errorMsg", errorMessage);

        model.addAttribute("username", "");
        model.addAttribute("password", "");

        if (model.getAttribute("resetMessage") != null) {
            model.addAttribute("msg", model.getAttribute("resetMessage"));
        } else if (model.getAttribute("resetError") != null) {
            model.addAttribute("errorMsg", model.getAttribute("resetError"));
        }

        return "loginPage/signIn";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getLocalizedMessage();
            }
        }

        model.addAttribute("user", new UserRegistrationDTO());
        model.addAttribute("rolesList", roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_RETAILER));
//		model.addAttribute("countries", countryRepository.findAll());
//		model.addAttribute("loginTab", "");
//		model.addAttribute("resetPasswordTab", "");
//		model.addAttribute("registerTab", "show active");
        model.addAttribute("errorMsg", errorMessage);
        return "loginPage/signUp";
    }

    @PostMapping("/account/registration")
    public String registerUserAccount(UserRegistrationDTO user, HttpServletResponse response, Model model) {
        List<String> errorMessage = new ArrayList<>();
        model.addAttribute("user", new UserRegistrationDTO());
        try {
            UserInfo userProfile = new UserInfo();
//        userProfile.setAddress(user.getAddress());
//		userProfile.setCity(Long.parseLong(user.getCity()));
            userProfile.setUsername(user.getUsername());
            userProfile.setFirstName(user.getFirstName());
            userProfile.setLastName(user.getLastName());
            userProfile.setMobileNumber(user.getMobileNumber());
            userProfile.setMiddleName(user.getMiddleName());
//		userProfile.setState(Long.parseLong(user.getState()));
//		userProfile.setCountry(Long.parseLong(user.getCountry()));
//		userProfile.setZipcode(user.getZipcode());
            userProfile.setPwd(utilService.encodedData(user.getPassword()));
            userProfile.setRoles(roleRepository.findByRoleCode(user.getRole()));
            userProfile.setIsActive(1l);

            boolean isValid = true;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<UserInfo>> violations = validator.validate(userProfile);

            for (ConstraintViolation<UserInfo> violation : violations) {
                logger.error(violation.getMessage());
                errorMessage.add(violation.getMessage());
                isValid = false;
            }

            List<UserInfo> existUser = userInfoRepository.isUserExist(user.getUsername(), user.getMobileNumber());
//		String navigationPage = "login";
            boolean isUserCreated = false;
            if (isValid && CollectionUtils.isEmpty(existUser)) {

                UserInfo savedUserProfile =null;
                try {
                    utilService.sendVerificationMobileAndEmail(userProfile);
//                String vCode = utilService.generateOTP();
//                userProfile.setVerificationCode(utilService.encodedData(vCode));
                    userProfile.setAccountState(StringLiteral.ACCOUNT_CREATION_STATE_STEP_1);
                     savedUserProfile = userInfoRepository.save(userProfile);
                    isUserCreated = true;
                    loginTracker.updateTracker(savedUserProfile.getUserId(), user.getLatLng());
//                    userProfile.setContactOTP(utilService.encodedData(smsManager.sendOTP(userProfile.getMobileNumber().trim())));
//                    userInfoRepository.save(userProfile);
                } catch (Exception e) {
                    logger.error("Error while sending OTP : {}", e);
                    userInfoRepository.delete(userProfile);
                    isUserCreated = false;
                }


                if (isUserCreated) {
//			utilService.clearAllCache();
//                    UserDetailsImpl authorities = new UserDetailsImpl(savedUserProfile);

//                    Authentication authentication = new UsernamePasswordAuthenticationToken(authorities, null,
//                            authorities.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
                	
                	
                	 model.addAttribute("confirmationMessage",
                             "Complete verification by providing the OTP sent to your email and mobile number. Do check the spam folder as well.");
                			
        			 model.addAttribute("confirmationHeader","Thanks you for signing up!");
                    setCookie(savedUserProfile, response);

//                    try {
//                        utilService.sendVerificationMail(savedUserProfile);
//                    } catch (Exception e) {
//                        logger.error("Error", e);
//                        errorMessage.add("Oops! Error while sending verification email. please contact admin.");
//                        model.addAttribute("errors", errorMessage);
//                    }
                }
                user = new UserRegistrationDTO();
//			navigationPage = "redirect:/index";
//			return "success";
            } else {
                String msg = "Oops! There is already a user registered with the email or mobile number provided.";
                try {
                    boolean isMobExist = false;
                    UserRegistrationDTO finalUser = user;
                    if(existUser.stream().filter(u-> u.getMobileNumber().equals(finalUser.getMobileNumber())).findAny().isPresent()){
                        isMobExist = true;
                        msg = "Oops! There is already a user registered with the mobile number provided.";
                    }

                    if(existUser.stream().filter(u-> u.getUsername().equals(finalUser.getUsername())).findAny().isPresent()){
                        if(!isMobExist)
                            msg = "Oops! There is already a user registered with the Email provided.";
                        else
                            msg = "Oops! There is already a user registered with the email and mobile number provided.";
                    }
                } catch (Exception e) {
                    logger.error("Error {}", e);
                }

                errorMessage.add(msg);
                model.addAttribute("errors", errorMessage);
//			return "error";
            }

            model.addAttribute("user", user);
//		model.addAttribute("loginTab", "");
//		model.addAttribute("countries", countryRepository.findAll());
//		model.addAttribute("resetPasswordTab", "");
//		model.addAttribute("registerTab", "show active");
            model.addAttribute("rolesList", roleRepository.findByRoleCodeNot(StringLiteral.ROLE_CODE_ADMIN));
            if (isUserCreated) {
                model.addAttribute("mob","(+91*****"+userProfile.getMobileNumber().substring(6)+")");
                model.addAttribute("mail","("+userProfile.getUsername().substring(0,2)+"*****"+
                        userProfile.getUsername().substring(userProfile.getUsername().indexOf("@"))+")");
                return "loginPage/otp";
            } else {
                return "loginPage/signUp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error: {} ", e);
            errorMessage.add("Oops! " + e.getLocalizedMessage());
            model.addAttribute("errors", errorMessage);
        }
        return "loginPage/signUp";
    }

    @RequestMapping("/account/otp")
    public String accountOptVerification(HttpServletRequest request, Model model, @LoginUser LoginUserInfo loginUserInfo) {

        try {
            String mOtp = request.getParameter("motp");
            String eOtp = request.getParameter("eotp");
            if (!StringUtils.isEmpty(mOtp) && !StringUtils.isEmpty(eOtp)) {
                //model = binderService.bindUserDetails(model, loginUserInfo);
                if (model != null) {
                    UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);
                    if (utilService.decodedData(userInfo.getContactOTP()).equals(mOtp) && utilService.decodedData(userInfo.getVerificationCode()).equals(eOtp)) {
                        userInfo.setAccountState(StringLiteral.ACCOUNT_CREATION_STATE_STEP_2);

                        if (userInfo.getUserPSwitchId() == null) {
                            String pSwitchUserId = Utility.getPSwitchUserId("XXX",
                                    userInfo.getRoles().getRoleCode(), userInfo.getUserId());
                            userInfo.setUserPSwitchId(pSwitchUserId);
                        }

//                        setCookie(userInfo, response);

//                        Map<String, Object> userData = new HashMap<>();
//                        userData.put("id", userInfo.getUserId());
//                        userData.put("fullName", userInfo.getFullName());
//                        userData.put("pSwitchId", userInfo.getUserPSwitchId());
//                        userData.put("email", userInfo.getUsername());
//                        userData.put("userRole", userInfo.getRoles());
//
//                        Cookie cookie = new Cookie(CustomJwtAuthenticationFilter.COOKIE_TOKEN,
//                                jwtService.token(userData, Optional.of(LocalDateTime.now().plusMinutes(tokenTimeout))));
//                        UserDetails userDetails = new User(userInfo.getUsername(), "",
//                                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userInfo.getRoles().getRoleCode())));
//
//                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                                userDetails, null, userDetails.getAuthorities());
//                        // After setting the Authentication in the context, we specify
//                        // that the current user is authenticated. So it passes the
//                        // Spring Security Configurations successfully.
//                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                        userInfoRepository.save(userInfo);
                        utilService.sendWelcomeEmail(userInfo);
                        smsManager.sendWelcomeMsg(userInfo);
                        return "redirect:/kyc";
                    } else {
                    	
                    	 model.addAttribute("mob","(+91*****"+userInfo.getMobileNumber().substring(6)+")");
                         model.addAttribute("mail","("+userInfo.getUsername().substring(0,2)+"*****"+
                        		 userInfo.getUsername().substring(userInfo.getUsername().indexOf("@"))+")");
                         
                        model.addAttribute("errorMsg", "Invalid OTP of Email and Mobile!");
                    }
                } else {
                    return "redirect:/login";
                }
            }
        } catch (Exception e) {
            return "redirect:/login";
        }

        return "loginPage/otp";
    }

    @GetMapping("/account/changePassword")
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

//    @GetMapping("/account/verify")
//    public String verify(@RequestParam(name = "code") String code, Model model) {
//        UserInfo userInfo = userInfoRepository.findByVerificationCode(code);
//        if (userInfo != null) {
//            userInfo.setIsActive(1l);
//            // (userInfo.getUserId());
//            try {
//                utilService.sendAccountCreatedMail(userInfo);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            userInfoRepository.save(userInfo);
////			utilService.clearAllCache();
//            model.addAttribute("user", new UserRegistrationDTO());
//            model.addAttribute("rolesList", roleRepository.findByRoleCodeNot(StringLiteral.ROLE_CODE_ADMIN));
//            model.addAttribute("countries", countryRepository.findAll());
//            model.addAttribute("loginTab", "show active");
//            model.addAttribute("resetPasswordTab", "");
//            model.addAttribute("registerTab", "");
//            model.addAttribute("msg",
//                    "Email Verify Successfully. For login please check your mail, Username and password sent to your register email-id");
//            return "loginPage/signIn";
//        } else {
//            return "error\\error-404";
//        }
//    }

    @PostMapping("/account/updatePassword")
    public String updatePassword(Model model, @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword, @RequestParam("token") String token) {

        String result = utilService.validatePasswordResetToken(token);
        if (result != null && !result.equalsIgnoreCase("Success")) {
            model.addAttribute("errorMsg", result);
            model.addAttribute("loginTab", "show active");
            model.addAttribute("changePassword", "");
        } else {
            if (newPassword != null && (newPassword.length() < 6 || newPassword.length() > 32)) {
                model.addAttribute("errorMsg", "Password must be between 6 and 32 characters");
                model.addAttribute("token", token);
                model.addAttribute("changePassword", "show active");
                model.addAttribute("loginTab", "");
            } else if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("errorMsg", "Password and confirm password fields do not match");
                model.addAttribute("changePassword", "show active");
                model.addAttribute("token", token);
                model.addAttribute("loginTab", "");
            } else {
                PasswordResetToken passwordResetToken = mPasswordResetRepository.findByToken(token);
                UserInfo userInfo = passwordResetToken.getUser();
                userInfo.setPwd(utilService.encodedData(newPassword));
                userInfoRepository.save(userInfo);
//				utilService.clearAllCache();
                model.addAttribute("msg", "Password reset successfully");
                model.addAttribute("loginTab", "show active");
                model.addAttribute("changePassword", "");
            }
        }

        model.addAttribute("user", new UserRegistrationDTO());
        model.addAttribute("rolesList", roleRepository.findByRoleCodeNot(StringLiteral.ROLE_CODE_ADMIN));
        model.addAttribute("resetPasswordTab", "");
        model.addAttribute("registerTab", "");

        return "loginPage/signIn";
    }

    @RequestMapping("/account/resetPassword")
    public String forgotPassword(final HttpServletRequest request, @RequestParam("resetEmail") final String userEmail,
                                 Model model) {

        final UserInfo user = userInfoRepository.findByUsername(userEmail);

        model.addAttribute("user", new UserRegistrationDTO());
        model.addAttribute("rolesList", roleRepository.findByRoleCodeNot(StringLiteral.ROLE_CODE_ADMIN));
        model.addAttribute("resetPasswordTab", "show active");
        model.addAttribute("loginTab", "");
        model.addAttribute("registerTab", "");

        if (user != null) {
            final String token = UUID.randomUUID().toString();
            final PasswordResetToken myToken = new PasswordResetToken();
            myToken.setToken(token);
            myToken.setUser(user);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 30);
            myToken.setExpiryDate(cal.getTime());

            mPasswordResetRepository.save(myToken);
//			mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
            utilService.sendResetMail(user, token);
            model.addAttribute("resetPasswordTab", "");
            model.addAttribute("loginTab", "show active");
            model.addAttribute("msg", "Please check your email id to reset your password");
        } else {
            model.addAttribute("errorMsg", "This email is not registered with us. Kindly register first.");
        }

        return "loginPage/signIn";
    }
}
