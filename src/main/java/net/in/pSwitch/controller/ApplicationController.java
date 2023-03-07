package net.in.pSwitch.controller;

import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.model.Banner;
import net.in.pSwitch.model.City;
import net.in.pSwitch.model.MPIN;
import net.in.pSwitch.model.Product;
import net.in.pSwitch.model.States;
import net.in.pSwitch.model.UserInfo;
import net.in.pSwitch.repository.CityRepository;
import net.in.pSwitch.repository.MPINRepository;
import net.in.pSwitch.repository.ProductRepository;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.StatesRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.BannerService;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.UtilService;
import net.in.pSwitch.utility.SMSManager;
import net.in.pSwitch.utility.StringLiteral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
public class ApplicationController {

	Logger logger = LoggerFactory.getLogger(ApplicationController.class);
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private BinderService binderService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private BannerService bannerService;
	@Autowired
	private UtilService utilService;
	@Autowired
	private StatesRepository statesRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private SMSManager smsManager;
	@Autowired
	private MPINRepository mpinRepository;

//	private void sendOtp(UserInfo userInfo) {
//		userInfo.setVerificationCode(utilService.encodedData(utilService.generateOTP()));
//		userInfo.setContactOTP(utilService.encodedData(smsManager.sendOTP(userInfo.getMobileNumber().trim())));
//		userInfoRepository.save(userInfo);
//		utilService.sendOTPMail(userInfo);
//	}

	@RequestMapping({ "/kyc"})
	public String kyc(Model model) {
		return "redirect:/kyc/";
	}

	@GetMapping({ "/", "index" })
	public String index(Model model, @LoginUser LoginUserInfo loginUserInfo) {
//		model = binderService.bindUserDetails(model);
		UserInfo userInfo = binderService.getCurrentUser(loginUserInfo);
		if (userInfo != null) {
			if(userInfo.getRoles().getRoleCode().equals(StringLiteral.ROLE_CODE_API_ROLE)){
				return "redirect:/userLogout";
			}
			boolean isNotAdmin = !StringLiteral.ROLE_CODE_ADMIN.equalsIgnoreCase(userInfo.getRoles().getRoleCode());
			if(isNotAdmin && (StringUtils.isEmpty(userInfo.getAccountState()) || StringLiteral.ACCOUNT_CREATION_STATE_STEP_1.equals(userInfo.getAccountState()))){
				model.addAttribute("mob","(+91*****"+userInfo.getMobileNumber().substring(6)+")");
				model.addAttribute("mail","("+userInfo.getUsername().substring(0,2)+"*****"+
						userInfo.getUsername().substring(userInfo.getUsername().indexOf("@"))+")");
				utilService.sendVerificationMobileAndEmail(userInfo);
				return "loginPage/otp";
			}if(isNotAdmin && StringLiteral.ACCOUNT_CREATION_STATE_STEP_2.equals(userInfo.getAccountState())){
				return "redirect:/kyc/";
			}else {
				if(isNotAdmin && StringLiteral.ACCOUNT_CREATION_STATE_STEP_5.equals(userInfo.getAccountState())) {
//					if (!userInfo.getUserPSwitchId().contains("XXX")) {
//						utilService.sendAccountCreatedMail(userInfo);
//					}
				}

				model = binderService.bindUserDetails(model, loginUserInfo);
				Page<Product> page = productRepository
						.findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "lastUpdatedTs")));
				model.addAttribute("latestProducts", page.getContent());
				model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_DASHBOARD);

				List<Banner> list = bannerService.findAllActive();
				final AtomicInteger counter = new AtomicInteger();
				final Collection<List<Banner>> result = list.stream()
						.collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 3)).values();
				model.addAttribute("banners", list);
				/*
				 * final List<List<Object>> banners = new
				 * ArrayList<>(bannerService.findAllActive().stream()
				 * .collect(Collectors.groupingBy(el -> listToSplit.indexOf(el) / 3)).values());
				 *
				 *
				 */
				return "index";
			}
		} else {
			return "loginPage/signIn";
		}
	}

	@GetMapping("/profile")
	public String profile(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		UserInfo userInfo = (UserInfo) model.getAttribute("user");
		MPIN mpin = mpinRepository.findByUser(userInfo);
		model.addAttribute("mpinSet", mpin != null && mpin.getPin() != null);
		return "profile";
	}

	@GetMapping(path = "/userProfile/{userId}")
	public String getMessage(@PathVariable("userId") Integer userId, Model model, @LoginUser LoginUserInfo loginUserInfo) {

		model = binderService.bindUserDetails(model, loginUserInfo);

		UserInfo userInfo = userInfoRepository.findById(userId).orElse(null);
		model.addAttribute("userProfile", userInfo);
		model.addAttribute("userRole", userInfo.getRoles());
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_USER);
		return "user-details";
	}

	@GetMapping("/states")
	@ResponseBody
	public List<States> state(@RequestParam(value = "countryId", required = true) Long countryId) {
		return statesRepository.findByCountryIdOrderByNameAsc(countryId);
	}

	@GetMapping("/cities")
	@ResponseBody
	public List<City> cities(@RequestParam(value = "stateId", required = true) Long stateId) {
		return cityRepository.findByStateId(stateId);
	}

//	@GetMapping("/test/changePassword")
//	public String test() {
//		Optional<UserInfo> user = userInfoRepository.findById(4);
//		utilService.sendVerificationMail(user.get());
//		return "Done";
//	}

}
