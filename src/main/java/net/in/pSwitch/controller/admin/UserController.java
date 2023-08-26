package net.in.pSwitch.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.dto.AttachmentDTO;
import net.in.pSwitch.dto.UserRegistrationDTO;
import net.in.pSwitch.model.BusinessDetails;
import net.in.pSwitch.model.States;
import net.in.pSwitch.model.user.Role;
import net.in.pSwitch.model.user.UserMapping;
import net.in.pSwitch.repository.CityRepository;
import net.in.pSwitch.repository.StatesRepository;
import net.in.pSwitch.repository.UserMappingRepository;
import net.in.pSwitch.service.UtilService;
import net.in.pSwitch.utility.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.in.pSwitch.model.user.UserInfo;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.UserInfoService;
import net.in.pSwitch.utility.StringLiteral;
import net.in.pSwitch.utility.Utility;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private BinderService binderService;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserMappingRepository userMappingRepository;
    @Autowired
    private StatesRepository statesRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value = "/data", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getDataForDatatable(@RequestParam Map<String, Object> params, @LoginUser LoginUserInfo loginUserInfo) {
        return userInfoService.getDataForDatatable(params, loginUserInfo);
    }

    @RequestMapping(value = "/userIdData", produces = "application/json")
    @ResponseBody
    public String menuList(Model model, String _type, String q) {

        Specification<UserInfo> specification = new Specification<UserInfo>() {

            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

//				ArrayList<Predicate> orPredicates = new ArrayList<>();
//
//				ArrayList<Predicate> predicates = new ArrayList<>();
//
//				if (q != null && q != "") {
//					orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + q + "%"));
//					orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + q + "%"));
//					orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + q + "%"));
//					orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + q + "%"));
//					orPredicates
//							.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("mobileNumber")), "%" + q + "%"));
//					orPredicates
//							.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("userPSwitchId")), "%" + q + "%"));
//
//					predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
//				}
//
////				predicates.add(criteriaBuilder.notEqual(root.get("roles"),
////						roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_ADMIN)));
//
//				return (!predicates.isEmpty()
//						? criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
//						: null);

                return criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("userPSwitchId")), "'%" + q + "%'");

                /*
                 * ArrayList<Predicate> orPredicates = new ArrayList<>();
                 *
                 * if (q != null && q != "") {
                 * orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(
                 * "firstName")), '%' + q + '%'));
                 * orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(
                 * "lastName")), '%' + q + '%'));
                 * orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(
                 * "username")), '%' + q + '%')); orPredicates
                 * .add(criteriaBuilder.like(criteriaBuilder.lower(root.get("mobileNumber")),
                 * '%' + q + '%')); orPredicates
                 * .add(criteriaBuilder.like(criteriaBuilder.lower(root.get("userPSwitchId")),
                 * '%' + q + '%')); }
                 *
                 * return (!orPredicates.isEmpty() ?
                 * criteriaBuilder.and(orPredicates.toArray(new Predicate[orPredicates.size()]))
                 * : null);
                 */
            }
        };

        List<UserInfo> userList = userInfoRepository.search(q == null ? "" : q);
        List<Map<String, Object>> data = new ArrayList<>();
        userList.forEach(user -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", user.getUserPSwitchId());
            item.put("text", user.getFullName()
                    + (user.getUserPSwitchId() != null ? " (" + user.getUserPSwitchId() + ")" : " (NA)"));
            data.add(item);
        });

        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    @RequestMapping("/")
    public String userList(Model model, @LoginUser LoginUserInfo loginUserInfo) {
        model = binderService.bindUserDetails(model, loginUserInfo);
        model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_USER);
        return "user/newUserList";
    }

//    @RequestMapping("/{role}")
//    public String userList(Model model, @PathVariable("role") String role, @LoginUser LoginUserInfo loginUserInfo) {
//        model = binderService.bindUserDetails(model, loginUserInfo);
//
//        model.addAttribute("searchRole", role);
//        model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_USER);
//        return "user/newUserList";
//    }

    @RequestMapping("/changeManager/{userId}")
    public String changeManager(Model model, @PathVariable("userId") Integer userId, @LoginUser LoginUserInfo loginUserInfo) {
        model = binderService.bindUserDetails(model, loginUserInfo);

        Optional<UserInfo> optional = userInfoRepository.findById(userId);
        UserInfo userInfo = null;
        if (optional != null) {
            userInfo = optional.get();
            List<UserInfo> list = userInfoRepository.findByRolesAndState(
                    roleRepository.findByRoleCode(Utility.getParentRole(userInfo.getRoles().getRoleCode())),
                    userInfo.getState());
            model.addAttribute("userList", list);
            model.addAttribute("userInfo", userInfo);
        }

        model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_USER);
        return "changeManager";
    }

    @RequestMapping("/updateManager")
    public String changeManager(Model model, String id, String managerId) {
        // model = binderService.bindUserDetails(model);

        Optional<UserInfo> userOptional = userInfoRepository.findById(Integer.parseInt(id));
        Optional<UserInfo> managerOptional = userInfoRepository.findById(Integer.parseInt(managerId));
        UserInfo userInfo = null;
        UserInfo managerInfo = null;
        if (userOptional != null) {
            userInfo = userOptional.get();
        }
        if (managerOptional != null) {
            managerInfo = managerOptional.get();
        }

//		userInfo.setParent(managerInfo);
        userInfoRepository.save(userInfo);
        return "redirect:/admin/user/";
    }

    @GetMapping("/enable/{userId}")
    public String enableUser(Model model, @PathVariable("userId") Integer userId) {
        Optional<UserInfo> optional = userInfoRepository.findById(userId);
        if (optional != null) {
            UserInfo info = optional.get();
            info.setIsActive(1l);
            userInfoRepository.save(info);
        }
        return "redirect:/admin/user/";
    }

    @GetMapping("/disable/{userId}")
    public String disableUser(Model model, @PathVariable("userId") Integer userId) {
        Optional<UserInfo> optional = userInfoRepository.findById(userId);
        if (optional != null) {
            UserInfo info = optional.get();
            info.setIsActive(0l);
            // info.setIsVerified(0);
            userInfoRepository.save(info);
        }
        return "redirect:/admin/user/";
    }

    @GetMapping("/verified/{userId}")
    public String verifiedUser(Model model, @PathVariable("userId") Integer userId) {
        Optional<UserInfo> optional = userInfoRepository.findById(userId);
        if (optional != null) {
            UserInfo info = optional.get();
            info.setIsVerified(1l);
            userInfoRepository.save(info);
        }
        return "redirect:/admin/user/";
    }

    @GetMapping("/reject/{userId}")
    public String notVerifiedUser(Model model, @PathVariable("userId") Integer userId) {
        Optional<UserInfo> optional = userInfoRepository.findById(userId);
        if (optional != null) {
            UserInfo info = optional.get();
            info.setIsVerified(2l);
            userInfoRepository.save(info);
        }
        return "redirect:/admin/user/";
    }

    @GetMapping(value = "/distributors/{masterDistributorId}")
    @ResponseBody
    public Map<Integer, String> getDataForDatatable(Model model, @LoginUser LoginUserInfo loginUserInfo, @PathVariable("masterDistributorId") Integer masterDistributorId) {
        List<UserInfo> distributor = userInfoRepository.findChildUser(masterDistributorId,
                roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_DISTRIBUTOR), Sort.by(Sort.Direction.ASC, "firstName"));

        return distributor.stream().collect
                (Collectors.toMap(UserInfo::getUserId, userInfo -> (userInfo.getUserPSwitchId() == null ?
                        userInfo.getFullName() : userInfo.getUserPSwitchId() + " - " + userInfo.getFullName())));

    }

    @GetMapping(value = "/retailer/{distributorId}")
    @ResponseBody
    public Map<Integer, String> getRetailerOfDistributor(Model model, @LoginUser LoginUserInfo loginUserInfo,
                                                    @PathVariable("distributorId") Integer distributorId) {
        List<UserInfo> retailers = userInfoRepository.findChildUser(distributorId,
                roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_RETAILER), Sort.by(Sort.Direction.ASC, "firstName"));

        return retailers.stream().collect
                (Collectors.toMap(UserInfo::getUserId, userInfo -> (userInfo.getUserPSwitchId() == null ?
                        userInfo.getFullName() : userInfo.getUserPSwitchId() + " - " + userInfo.getFullName())));

    }

    @RequestMapping("/new")
    public String addAssociate(Model model, @LoginUser LoginUserInfo loginUserInfo) {
        model = binderService.bindUserDetails(model, loginUserInfo);
        model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_USER);

        UserInfo currentUser = (UserInfo) model.getAttribute("user");

        List<Role> roles = null;
        if (currentUser.getRoles().getRoleCode().equals("AD")) {
            roles = roleRepository.findByRoleCodes(Arrays.asList("SE", "OAD"));
        } else if (currentUser.getRoles().getRoleCode().equals("SE")) {
            roles = roleRepository.findByRoleCodes(Arrays.asList("S", "D", "R"));

            List<UserInfo> masterDistributor = userInfoRepository.findByRoles(currentUser.getUserId(), roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR), Sort.by(Sort.Direction.ASC, "firstName"));
            List<UserInfo> distributor = userInfoRepository.findByRoles(currentUser.getUserId(), roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_DISTRIBUTOR), Sort.by(Sort.Direction.ASC, "firstName"));

            model.addAttribute("masterDistributors", masterDistributor.stream().collect
                    (Collectors.toMap(UserInfo::getUserId, userInfo -> (userInfo.getUserPSwitchId() == null ? userInfo.getFullName() : userInfo.getUserPSwitchId() + " - " + userInfo.getFullName()))));
//            model.addAttribute("distributors", distributor.stream().collect
//                    (Collectors.toMap(UserInfo::getUserId, userInfo -> (userInfo.getUserPSwitchId() == null ? userInfo.getFullName() : userInfo.getUserPSwitchId() + " - " + userInfo.getFullName()))));

        }

        model.addAttribute("states", statesRepository.findByCountryIdOrderByNameAsc(101l));
        model.addAttribute("cities", cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));

        model.addAttribute("roles", roles);

        return "addAssociate";
    }


    @PostMapping("/save")
    public String saveAssociate(Model model, UserRegistrationDTO user, @LoginUser LoginUserInfo loginUserInfo) {
        model = binderService.bindUserDetails(model, loginUserInfo);
        List<String> errorMessage = new ArrayList<>();
//        model.addAttribute("addUser", new UserRegistrationDTO());
        UserInfo currentUser = binderService.getCurrentUser(loginUserInfo);
        try {

            boolean isValid = true;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(user);

            for (ConstraintViolation<UserRegistrationDTO> violation : violations) {
                logger.error(violation.getMessage());
                errorMessage.add(violation.getMessage());
                isValid = false;
            }

            if (isValid) {
                UserInfo userProfile = new UserInfo();
                userProfile.setAddress(user.getAddress());
                userProfile.setCity(Long.parseLong(user.getCity()));
                userProfile.setUsername(user.getEmail());
                userProfile.setFirstName(user.getFirstName());
                userProfile.setLastName(user.getLastName());
                userProfile.setMobileNumber(user.getMobileNumber());
                userProfile.setMiddleName(user.getMiddleName());
                userProfile.setState(Long.parseLong(user.getState()));
                userProfile.setCountry(101l);
                userProfile.setDob(user.getDob());
                userProfile.setZipcode(user.getZipcode());
                userProfile.setPwd(utilService.encodedData(PasswordGenerator.generatePassword()));
                userProfile.setRoles(roleRepository.findByRoleCode(user.getRole()));

                userProfile.setUserPSwitchId(user.getRole()+user.getMobileNumber());
                userProfile.setIsActive(1l);

                List<UserInfo> existUser = userInfoRepository.isUserExist(user.getEmail(), user.getMobileNumber(), user.getRole());
//		String navigationPage = "login";
                boolean isUserCreated = false;
                if (isValid && CollectionUtils.isEmpty(existUser)) {

//                UserInfo savedUserProfile =null;
                    try {
                        userProfile = userInfoRepository.save(userProfile);
                        isUserCreated = true;

                        UserMapping userMapping = new UserMapping();
                        userMapping.setUser(userProfile);
                        userMapping.setCreatedBy(currentUser.getUserId());
                        userMapping.setUpdatedBy(currentUser.getUserId());

                        if (!StringUtils.isEmpty(user.getDistributor())) {
                            Optional<UserInfo> dsOptional = userInfoRepository.findById(Integer.parseInt(user.getDistributor()));
                            if (dsOptional.isPresent()) {
                                userMapping.setParentUser(dsOptional.get().getUserId());
                            }
                        } else if (!StringUtils.isEmpty(user.getMasterDistributor())) {
                            Optional<UserInfo> mdOptional = userInfoRepository.findById(Integer.parseInt(user.getMasterDistributor()));
                            if (mdOptional.isPresent()) {
                                userMapping.setParentUser(mdOptional.get().getUserId());
                            }
                        }

                        if (userMapping.getParentUser() == null) {
                            userMapping.setParentUser(currentUser.getUserId());
                        }

                        userMapping = userMappingRepository.save(userMapping);
                        userProfile.setUserMapping(userMapping);
                        userProfile.setAccountState(StringLiteral.ACCOUNT_CREATION_STATE_STEP_1);
                        utilService.sendLoginDetailMail(userProfile);
                    } catch (Exception e) {
                        logger.error("Error while sending OTP : {}", e);
                        userInfoRepository.delete(userProfile);

                        errorMessage.add("Failed to create user, Please try again later");
                        model.addAttribute("errors", errorMessage);

                        isUserCreated = false;
                    }

                    if (isUserCreated) {
                        model.addAttribute("confirmationMessage",
                                "To Complete verification process user should providing the OTP sent to registered email and mobile number. Do check the spam folder as well.");

                        model.addAttribute("confirmationHeader", "Thanks you for new user registration!");
                        user = new UserRegistrationDTO();

                    }
//                user = new UserRegistrationDTO();
//			navigationPage = "redirect:/index";
//			return "success";
                } else {
                    String msg = "Oops! There is already a user registered with the same Role, email and mobile number provided.";
                    try {
                        boolean isMobExist = false;
//                    UserRegistrationDTO finalUser = user;
                        UserRegistrationDTO finalUser = user;
                        if (existUser.stream().anyMatch(u -> u.getMobileNumber().equals(finalUser.getMobileNumber()))) {
                            isMobExist = true;
                            msg = "Oops! There is already a user registered with the mobile number provided.";
                        }

                        if (existUser.stream().anyMatch(u -> u.getUsername().equals(finalUser.getUsername()))) {
                            if (!isMobExist)
                                msg = "Oops! There is already a user registered with the Email provided.";
                            else
                                msg = "Oops! There is already a user registered with the email and mobile number provided.";
                        }
                    } catch (Exception e) {
                        logger.error("Error: ", e);
                    }

                    errorMessage.add(msg);
                    model.addAttribute("errors", errorMessage);
                }
            }

            model.addAttribute("addUser", user);
            List<Role> roles = null;
            if (currentUser.getRoles().getRoleCode().equals("AD")) {
                roles = roleRepository.findByRoleCodes(Arrays.asList("SE", "OAD"));
            } else if (currentUser.getRoles().getRoleCode().equals("SE")) {
                roles = roleRepository.findByRoleCodes(Arrays.asList("S", "D", "R"));
                List<UserInfo> masterDistributor = userInfoRepository.findByRoles(currentUser.getUserId(), roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR), Sort.by(Sort.Direction.ASC, "firstName"));
                List<UserInfo> distributor = userInfoRepository.findByRoles(currentUser.getUserId(), roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_DISTRIBUTOR), Sort.by(Sort.Direction.ASC, "firstName"));

                model.addAttribute("masterDistributors", masterDistributor.stream().collect
                        (Collectors.toMap(UserInfo::getUserId, userInfo -> (userInfo.getUserPSwitchId() == null ? userInfo.getFullName() : userInfo.getUserPSwitchId() + " - " + userInfo.getFullName()))));
                model.addAttribute("distributors", distributor.stream().collect
                        (Collectors.toMap(UserInfo::getUserId, userInfo -> (userInfo.getUserPSwitchId() == null ? userInfo.getFullName() : userInfo.getUserPSwitchId() + " - " + userInfo.getFullName()))));

            }

            model.addAttribute("states", statesRepository.findByCountryIdOrderByNameAsc(101l));
            model.addAttribute("cities", cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));

            model.addAttribute("roles", roles);
            return "addAssociate";

//            if (isUserCreated) {
//                model.addAttribute("mob", "(+91*****" + userProfile.getMobileNumber().substring(6) + ")");
//                model.addAttribute("mail", "(" + userProfile.getUsername().substring(0, 2) + "*****" +
//                        userProfile.getUsername().substring(userProfile.getUsername().indexOf("@")) + ")");
//                return "redirect:/user/";
//            } else {
//                return "addAssociate";
//            }
        } catch (Exception e) {
            logger.error("Error: {} ", e);
            errorMessage.add("Oops! " + e.getLocalizedMessage());
            model.addAttribute("errors", errorMessage);
        }
        return "addAssociate";
    }
//	public String saveAssociate(Model model, UserRegistrationDTO user, @LoginUser LoginUserInfo loginUserInfo) {
//		model = binderService.bindUserDetails(model,loginUserInfo);
//		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_USER);
//
//		UserInfo parentUser = (UserInfo) model.getAttribute("user");
//
//		UserInfo userProfile = new UserInfo();
//		userProfile.setAddress(user.getAddress());
//		userProfile.setCity(Long.parseLong(user.getCity()));
//		userProfile.setUsername(user.getUsername());
//		userProfile.setFirstName(user.getFirstName());
//		userProfile.setLastName(user.getLastName());
//		userProfile.setMobileNumber(user.getMobileNumber());
//		userProfile.setState(Long.parseLong(user.getState()));
//		userProfile.setCountry(Long.parseLong(user.getCountry()));
//		userProfile.setZipcode(user.getZipcode());
//		userProfile.setPwd(utilService.encodedData(PasswordGenerator.generatePassword()));
//		userProfile.setRoles(roleRepository.findByRoleCode(Utility.getAssociateRole(parentUser.getRoles().getRoleCode())));
//		userProfile.setIsActive(0l);
//
//
////		userProfile.setParent(parentUser);
//
//		boolean isValid = true;
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		Validator validator = factory.getValidator();
//		Set<ConstraintViolation<UserInfo>> violations = validator.validate(userProfile);
//		List<String> errorMessage = new ArrayList<>();
//		for (ConstraintViolation<UserInfo> violation : violations) {
//			logger.error(violation.getMessage());
//			errorMessage.add(violation.getMessage());
//			isValid = false;
//		}
//
//		List<UserInfo> existUser = userInfoRepository.isUserExist(user.getUsername(), user.getMobileNumber());
//		if (isValid && CollectionUtils.isEmpty(existUser)) {
//
////			String vCode = RandomString.make(64);
//			userProfile.setVerificationCode(utilService.encodedData(utilService.generateOTP()));
//
//			UserInfo savedUserProfile = userInfoRepository.save(userProfile);
//			model.addAttribute("confirmationMessage",
//					"A confirmation e-mail has been sent to " + userProfile.getUsername());
//			try {
//				utilService.sendVerificationMobileAndEmail(savedUserProfile);
//			} catch (Exception e) {
//				logger.error("Error", e);
//			}
//			user = new UserRegistrationDTO();
//			return "redirect:/admin/user/";
//		} else {
//			errorMessage.add("Oops! There is already a user registered with the email provided.");
//			model.addAttribute("errors", errorMessage);
//		}
//		model.addAttribute("data", user);
//		return "addAssociate";
//	}
}
