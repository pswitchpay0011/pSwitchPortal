package net.in.pSwitch.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.utility.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.bytebuddy.utility.RandomString;
import net.in.pSwitch.model.Menu;
import net.in.pSwitch.model.Product;
import net.in.pSwitch.model.ProductFlexiCommission;
import net.in.pSwitch.model.ProductType;
import net.in.pSwitch.model.Status;
import net.in.pSwitch.model.UserInfo;
import net.in.pSwitch.dto.UserRegistrationDTO;
import net.in.pSwitch.repository.MenuRepository;
import net.in.pSwitch.repository.ProductFlexiCommissionRepository;
import net.in.pSwitch.repository.ProductRepository;
import net.in.pSwitch.repository.ProductTypeRepository;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.FileStorageService;
import net.in.pSwitch.service.UtilService;
import net.in.pSwitch.utility.StringLiteral;
import net.in.pSwitch.utility.Utility;

@Controller
@RequestMapping("/service")
public class ServicesController {

	Logger logger = LoggerFactory.getLogger(ServicesController.class);

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductFlexiCommissionRepository pFCRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private BinderService binderService;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private ProductTypeRepository productTypeRepository;
	@Autowired
	private FileStorageService storageService;
	@Autowired
	private UtilService utilService;

	@RequestMapping("/associates")
	public String associates(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_ASSOCIATES);
		return "associateList";
	}

	@RequestMapping("/addAssociate")
	public String addAssociate(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_ASSOCIATES);
		return "addAssociate";
	}

	@PostMapping("/saveAssociate")
	public String saveAssociate(Model model, UserRegistrationDTO user, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_ASSOCIATES);

		UserInfo parentUser = (UserInfo) model.getAttribute("user");

		UserInfo userProfile = new UserInfo();
		userProfile.setAddress(user.getAddress());
		userProfile.setCity(Long.parseLong(user.getCity()));
		userProfile.setUsername(user.getUsername());
		userProfile.setFirstName(user.getFirstName());
		userProfile.setLastName(user.getLastName());
		userProfile.setMobileNumber(user.getMobileNumber());
		userProfile.setState(Long.parseLong(user.getState()));
		userProfile.setCountry(Long.parseLong(user.getCountry()));
		userProfile.setZipcode(user.getZipcode());
		userProfile.setPwd(utilService.encodedData(PasswordGenerator.generatePassword()));
		userProfile.setRoles(roleRepository.findByRoleCode(Utility.getAssociateRole(parentUser.getRoles().getRoleCode())));
		userProfile.setIsActive(0l);
		userProfile.setParent(parentUser);

		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UserInfo>> violations = validator.validate(userProfile);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<UserInfo> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		List<UserInfo> existUser = userInfoRepository.isUserExist(user.getUsername(), user.getMobileNumber());
		if (isValid && CollectionUtils.isEmpty(existUser)) {

//			String vCode = RandomString.make(64);
			userProfile.setVerificationCode(utilService.encodedData(utilService.generateOTP()));

			UserInfo savedUserProfile = userInfoRepository.save(userProfile);
			model.addAttribute("confirmationMessage",
					"A confirmation e-mail has been sent to " + userProfile.getUsername());
			try {
				utilService.sendVerificationMobileAndEmail(savedUserProfile);
			} catch (Exception e) {
				logger.error("Error", e);
			}
			user = new UserRegistrationDTO();
			return "redirect:/service/associateList";
		} else {
			errorMessage.add("Oops! There is already a user registered with the email provided.");
			model.addAttribute("errors", errorMessage);
		}
		model.addAttribute("data", user);
		return "addAssociate";
	}

	@RequestMapping("/shareProduct/{productId}")
	@ResponseBody
	public String shareProduct(Model model, @PathVariable("productId") Integer productId, String emails) {
		try {
			Optional<Product> optional = productRepository.findById(productId);
			if (optional != null && optional.isPresent()) {
				List<String> emailList = Arrays.asList(emails.replaceAll(" ", "").split(","));
				utilService.shareProduct(optional.get(), emailList);
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
		}

		return "error";
	}

	@RequestMapping("/product/{productTypeId}")
	public String addMenu(Model model, @PathVariable("productTypeId") Integer productTypeId, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);

		UserInfo userInfo = (UserInfo) model.getAttribute("user");

		try {
			Optional<ProductType> optional = productTypeRepository.findById(productTypeId);
			ProductType productType = optional.isPresent() ? optional.get() : null;

			List<Product> list = productRepository.findAllByProductTypeAndStatus(productType, Status.ACTIVE);

			List<Map<String, String>> data = new ArrayList<>();
			list.forEach(item -> {
				HashMap<String, String> product = new HashMap<>();

				product.put("id", "" + item.getId());
				product.put("MRP", ""+item.getMRP());
				product.put("brochureFile", "" + item.getBrochureFile());
				product.put("productImageFile", "" + item.getProductImageFile());
				product.put("productName", "" + item.getProductName());
				product.put("productDescription", "" + item.getProductDescription());
				product.put("amount", item.getAmount() != null ? "" + item.getAmount() : "NA");

				ProductFlexiCommission commission = pFCRepository.findAllByProductAndUser(item, userInfo);
				if (commission != null) {
					product.put("amount", "" + commission.getAmount());
					product.put("oldAmount", item.getAmount() != null ? "" + item.getAmount() : "");
				}

				data.add(product);
			});

			model.addAttribute("productList", data);

			List<String> activeLink = new ArrayList<String>();

			if (productType != null) {
				String childMenu = productType.getName();
				Menu menu = menuRepository.findByMenuName(childMenu);
				addParentMenuLink(menu, activeLink);

				model.addAttribute("productMenu", menu);
				model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, activeLink);
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		return "all-products";
	}

	@RequestMapping("/irctc")
	public String irctc(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		List<String> activeLink = new ArrayList<String>();
		Menu menu = menuRepository.findByMenuName(StringLiteral.MENU_B2C_TICKETING_IRCTC);
		addParentMenuLink(menu, activeLink);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, activeLink);
		return "index";
	}

	private void addParentMenuLink(Menu childMenu, List<String> activeLink) {

		if (childMenu != null) {
			activeLink.add(childMenu.getMenuName());
			if (childMenu.getParentMenuId() != null)
				addParentMenuLink(menuRepository.findById(childMenu.getParentMenuId()).get(), activeLink);
		}
	}

}
