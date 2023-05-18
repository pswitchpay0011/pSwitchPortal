package net.in.pSwitch.service;

import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.controller.ApplicationController;
import net.in.pSwitch.model.GSTINData;
import net.in.pSwitch.model.Menu;
import net.in.pSwitch.model.UserInfo;
import net.in.pSwitch.model.UserWallet;
import net.in.pSwitch.repository.*;
import net.in.pSwitch.utility.StringLiteral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BinderServiceImpl implements BinderService {

	Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private ProductNewsRepository newsRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private StatesRepository statesRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserWalletRepository userWalletRepository;
	@Autowired
	private GSTINRepository gstinRepository;


	@Override
	public boolean bindCookieData(HttpServletResponse response) {

		try {
			Cookie cookie = new Cookie("testData", "");
			response.addCookie(cookie);

			return true;
		}catch (Exception e){
			logger.error("Error : {}", e);
		}

		return false;
	}

	private List getActiveMenu(List<Menu> menuList) {
		List<Menu> menu = new ArrayList<Menu>();
		menuList.forEach(item -> {
			if (item.getIsActive() == 1l) {
				item.setMenus(getActiveMenu(item.getMenus()));
				menu.add(item);
			}
		});
		return menu;
	}

	@Override
	public Boolean isUserLogin() {
		try {
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = context.getAuthentication();
			if (authentication.isAuthenticated()) {
				String username = authentication.getName();
				UserInfo userInfo = userInfoRepository.findByUserPSwitchId(username);
				return userInfo != null;
			}
		} catch (Exception e) {
			logger.error("error {}", e);
		}
		return false;
	}

	@Override
	public UserInfo getCurrentUser(LoginUserInfo loginUserInfo) {
		if (loginUserInfo!=null && loginUserInfo.getId()!=null) {
			Optional<UserInfo> userInfo = userInfoRepository.findById(loginUserInfo.getId());
			return userInfo.isPresent()? userInfo.get(): null;
		}
		return null;
	}

	@Override
	public Model bindUserDetails(Model model, LoginUserInfo loginUserInfo) {

		UserInfo userInfo = getCurrentUser(loginUserInfo);
		if(userInfo!=null) {
			logger.info("username :: " + userInfo.getUserPSwitchId());
			model.addAttribute("user", userInfo);
			List<Menu> menuList = new ArrayList<Menu>();

			if (StringLiteral.ROLE_CODE_ADMIN.equalsIgnoreCase(userInfo.getRoles().getRoleCode())) {

				model.addAttribute("RE_COUNT", userInfoRepository
						.countByRoles(roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_RETAILER)));
				model.addAttribute("DS_COUNT", userInfoRepository
						.countByRoles(roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_DISTRIBUTOR)));
				model.addAttribute("SD_COUNT", userInfoRepository
						.countByRoles(roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR)));
				model.addAttribute("BA_COUNT", userInfoRepository
						.countByRoles(roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_BUSINESS_ASSOCIATE)));

				menuList = menuRepository.findAllByAdminMenuAndIsActiveOrderByMenuOrderAsc(1l, 1l);
			} else if (StringLiteral.ROLE_CODE_RETAILER.equalsIgnoreCase(userInfo.getRoles().getRoleCode()))
				menuList = menuRepository.findAllByRetailerMenuAndIsActiveOrderByMenuOrderAsc(1l, 1l);
			else if (StringLiteral.ROLE_CODE_DISTRIBUTOR.equalsIgnoreCase(userInfo.getRoles().getRoleCode()))
				menuList = menuRepository.findAllByDistributorMenuAndIsActiveOrderByMenuOrderAsc(1l, 1l);
			else if (StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR.equalsIgnoreCase(userInfo.getRoles().getRoleCode()))
				menuList = menuRepository.findAllBySuperDistributorMenuAndIsActiveOrderByMenuOrderAsc(1l, 1l);
			else if (StringLiteral.ROLE_CODE_BUSINESS_ASSOCIATE.equalsIgnoreCase(userInfo.getRoles().getRoleCode()))
				menuList = menuRepository.findAllByBusinessAssociateMenuAndIsActiveOrderByMenuOrderAsc(1l, 1l);

			model.addAttribute("menuList", getActiveMenu(menuList));

			String news = newsRepository.findAllByActive(1).stream().map(e -> e.getNews() + " ***** ").reduce("",
					String::concat);
			model.addAttribute("news", news);
			model.addAttribute("role", userInfo.getRoles());

			UserWallet mUserWallet = userWalletRepository.findByUser(userInfo);

			if(userInfo.isKycCompleted()) {
				GSTINData gstinData = gstinRepository.findByUser(userInfo.getUserId());
				model.addAttribute("gstin", gstinData != null ? gstinData.getGstin() : "");
			}

			model.addAttribute("walletAmount", mUserWallet != null ? mUserWallet.getCurrentAmount() : 0);
			return model;
		}
		return null;
	}
}
