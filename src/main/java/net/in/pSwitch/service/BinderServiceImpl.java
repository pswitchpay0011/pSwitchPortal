package net.in.pSwitch.service;

import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.controller.ApplicationController;
import net.in.pSwitch.dto.FundTransferDTO;
import net.in.pSwitch.model.GSTINData;
import net.in.pSwitch.model.Menu;
import net.in.pSwitch.model.user.UserInfo;
import net.in.pSwitch.repository.CityRepository;
import net.in.pSwitch.repository.CountryRepository;
import net.in.pSwitch.repository.GSTINRepository;
import net.in.pSwitch.repository.MenuRepository;
import net.in.pSwitch.repository.ProductNewsRepository;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.StatesRepository;
import net.in.pSwitch.repository.TransactionRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.repository.UserWalletRepository;
import net.in.pSwitch.utility.StringLiteral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	@Autowired
	private TransactionRepository transactionRepository;

	private final EntityManager entityManager;

	@Autowired
	public BinderServiceImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


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
			else if (StringLiteral.ROLE_CODE_SALES_EMPLOYEE.equalsIgnoreCase(userInfo.getRoles().getRoleCode()))
				menuList = menuRepository.findAllSalesEmpMenu(1l, 1l);

			model.addAttribute("menuList", getActiveMenu(menuList));

			String news = newsRepository.findAllByActive(1).stream().map(e -> e.getNews() + " ***** ").reduce("",
					String::concat);
			model.addAttribute("news", news);
			model.addAttribute("role", userInfo.getRoles());

//			UserWallet mUserWallet = userWalletRepository.findByUser(userInfo);

			if(userInfo.isKycCompleted()) {
				GSTINData gstinData = gstinRepository.findByUser(userInfo.getUserId());
				model.addAttribute("gstin", gstinData != null ? gstinData.getGstin() : "");
			}

			model.addAttribute("walletAmount", walletBalance(loginUserInfo));

			return model;
		}
		return null;
	}

	@Override
	public double walletBalance(LoginUserInfo loginUserInfo) {
		try {

			StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PR_GetBalanceAmount");
			query.registerStoredProcedureParameter("userId", Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("result", Integer.class, ParameterMode.OUT);

			query.setParameter("userId", loginUserInfo.getId());
			query.execute();

			return (Integer) query.getOutputParameterValue("result");
		}catch (Exception e){
			logger.error("Error :", e);
		}
		return 0;
	}

	@Override
	public Integer transferFund(LoginUserInfo loginUserInfo, FundTransferDTO fundTransferDTO){
		try {

			StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PR_FundTransfer");
			query.registerStoredProcedureParameter("TransferredFromUserId", Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("TransferredToUserId", Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("Amount", Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("Remarks", String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter("IsActive", Integer.class, ParameterMode.IN);
//			query.registerStoredProcedureParameter("CreatedOn", Timestamp.class, ParameterMode.IN);

			query.registerStoredProcedureParameter("ReturnValue", Integer.class, ParameterMode.OUT);

			query.setParameter("TransferredFromUserId", loginUserInfo.getId());
			query.setParameter("TransferredToUserId", fundTransferDTO.getUserId());
			int amount  = (int) Double.parseDouble(fundTransferDTO.getAmount());
			query.setParameter("Amount", amount);
			query.setParameter("Remarks", fundTransferDTO.getRemark());
			query.setParameter("IsActive", 1);
//			query.setParameter("CreatedOn", Instant.now());
			query.execute();

			return (Integer) query.getOutputParameterValue("ReturnValue");
		}catch (Exception e){
			logger.error("Error :", e);
		}
		return 0;
	}


	@Override
	public String getAxisVirtualAccountNo(LoginUserInfo loginUserInfo) {
		UserInfo userInfo = getCurrentUser(loginUserInfo);
		return "PSPL" + userInfo.getMobileNumber() + String.format("%06d", userInfo.getUserId());
	}

	@Override
	public List<Map> getUserVirtualAccountDetails(LoginUserInfo loginUserInfo) {

		List<Map> virtualAccounts = new ArrayList<>();

		HashMap<String, String> axisVirtualAC = new HashMap<>();
		axisVirtualAC.put("Bank Name", "Axis Bank");
		axisVirtualAC.put("VirtualAccountNo", getAxisVirtualAccountNo(loginUserInfo));
		axisVirtualAC.put("IFSCCode", "UTIB0CCH274");

		virtualAccounts.add(axisVirtualAC);

		return virtualAccounts;
	}
}
