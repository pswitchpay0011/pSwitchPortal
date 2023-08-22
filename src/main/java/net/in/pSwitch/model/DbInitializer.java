package net.in.pSwitch.model;

import net.in.pSwitch.model.user.Role;
import net.in.pSwitch.model.user.UserInfo;
import net.in.pSwitch.repository.CompanyBankDetailsRepository;
import net.in.pSwitch.repository.MenuRepository;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.UtilServiceImpl;
import net.in.pSwitch.utility.DefaultMenu;
import net.in.pSwitch.utility.StringLiteral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DbInitializer implements CommandLineRunner
{
	private static final Logger logger = LoggerFactory.getLogger(DbInitializer.class);

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private CompanyBankDetailsRepository companyBankDetailsRepository;

	@Autowired
	private UtilServiceImpl utilService;

	@Override
	public void run(String... args) throws Exception {
		logger.info("Inside the DbInitializer");

		if (companyBankDetailsRepository.findByAccountNumber("COMPANY1010101") == null) {
			CompanyBankDetails companyBankDetails = new CompanyBankDetails();
			companyBankDetails.setAccountHolderName("PSwitch Account 1");
			companyBankDetails.setAccountNumber("COMPANY1010101");
			companyBankDetails.setBANK_NAME("SBI");
			companyBankDetails.setIFSC_CODE("SBI101010");
			companyBankDetailsRepository.save(companyBankDetails);
		}

		DefaultMenu.addDefaultMenu(menuRepository);

		List<Role> roleList = new ArrayList<>();
		if (roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_RETAILER) == null) {
			Role role = new Role();
			role.setRoleCode(StringLiteral.ROLE_CODE_RETAILER);
			role.setRoleName(StringLiteral.ROLE_NAME_RETAILER);
			roleList.add(role);
		}

		if (roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_DISTRIBUTOR) == null) {
			Role role = new Role();
			role.setRoleCode(StringLiteral.ROLE_CODE_DISTRIBUTOR);
			role.setRoleName(StringLiteral.ROLE_NAME_DISTRIBUTOR);
			roleList.add(role);
		}

		if (roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR) == null) {
			Role role = new Role();
			role.setRoleCode(StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR);
			role.setRoleName(StringLiteral.ROLE_NAME_SUPER_DISTRIBUTOR);
			roleList.add(role);
		}
		if (roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_BUSINESS_ASSOCIATE) == null) {
			Role role = new Role();
			role.setRoleCode(StringLiteral.ROLE_CODE_BUSINESS_ASSOCIATE);
			role.setRoleName(StringLiteral.ROLE_NAME_BUSINESS_ASSOCIATE);
			roleList.add(role);
		}
		if (roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_ADMIN) == null) {
			Role role = new Role();
			role.setRoleCode(StringLiteral.ROLE_CODE_ADMIN);
			role.setRoleName(StringLiteral.ROLE_NAME_ADMIN);
			roleList.add(role);
		}

		if (roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_OFFICE_ADMIN) == null) {
			Role role = new Role();
			role.setRoleCode(StringLiteral.ROLE_CODE_OFFICE_ADMIN);
			role.setRoleName(StringLiteral.ROLE_NAME_OFFICE_ADMIN);
			roleList.add(role);
		}

		if (roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_MANAGER_BUSINESS_ASSOCIATE) == null) {
			Role role = new Role();
			role.setRoleCode(StringLiteral.ROLE_CODE_MANAGER_BUSINESS_ASSOCIATE);
			role.setRoleName(StringLiteral.ROLE_NAME_MANAGER_BUSINESS_ASSOCIATE);
			roleList.add(role);
		}

		if (roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_FINANCE) == null) {
			Role role = new Role();
			role.setRoleCode(StringLiteral.ROLE_CODE_FINANCE);
			role.setRoleName(StringLiteral.ROLE_NAME_FINANCE);
			roleList.add(role);
		}

		if (roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_MANAGER_FINANCE) == null) {
			Role role = new Role();
			role.setRoleCode(StringLiteral.ROLE_CODE_MANAGER_FINANCE);
			role.setRoleName(StringLiteral.ROLE_NAME_MANAGER_FINANCE);
			roleList.add(role);
		}

		if (roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_API_ROLE) == null) {
			Role role = new Role();
			role.setRoleCode(StringLiteral.ROLE_CODE_API_ROLE);
			role.setRoleName(StringLiteral.ROLE_NAME_API_ROLE);
			roleList.add(role);
		}

//        roleRepository.deleteAll();
		roleRepository.saveAll(roleList);

//        UserInfo user = new UserInfo();
//        user.setUsername("user");
//        user.setAddress("Sector 17A");
//        user.setFirstName("Abhishek");
//        user.setLastName("Tripathi");
//        user.setMobileNumber("7053007405");
//        user.setRoles(roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_RETAILER));
//        user.setPwd(passwordEncoder.encode("pass"));
//		user.setState(13l);
//		user.setCountry(101l);
//		user.setCity(1126l);
//		user.setIsActive(1l);
//        user.setZipcode("122001");
//
//        if (userInfoRepository.findByUsername("user") == null) {
//            userInfoRepository.save(user);
//        }

		UserInfo admin = new UserInfo();
		admin.setUsername("admin");
		admin.setUserPSwitchId("admin");
		admin.setAddress("Sector 17A");
		admin.setFirstName("Admin");
		admin.setLastName("Admin");
		admin.setMobileNumber("7053007405");
		admin.setRoles(roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_ADMIN));
		admin.setPwd(passwordEncoder.encode("B/5[2-HWL;NQg>zu$mYv"));
		admin.setState(13l);
		admin.setCountry(101l);
		admin.setCity(1126l);
		admin.setIsActive(1l);
		admin.setZipcode("122001");
		if (userInfoRepository.findByUserPSwitchId("admin") == null) {
			userInfoRepository.save(admin);
		}



		/*
		 * List<UserInfo> userList = new ArrayList<>(); for (int i = 0; i < 100; i++) {
		 * 
		 * UserInfo user = new UserInfo(); user.setUsername("user" + i);
		 * user.setUserPSwitchId("user" + i); user.setAddress("Sector 17A");
		 * user.setFirstName("user" + i); user.setLastName("user");
		 * user.setMobileNumber("7053007405"); if (i % 3 == 0)
		 * user.setRoles(roleRepository.findByRoleCode(StringLiteral.
		 * ROLE_CODE_DISTRIBUTOR)); else if (i % 5 == 0)
		 * user.setRoles(roleRepository.findByRoleCode(StringLiteral.
		 * ROLE_CODE_SUPER_DISTRIBUTOR)); else if (i % 7 == 0)
		 * user.setRoles(roleRepository.findByRoleCode(StringLiteral.
		 * ROLE_CODE_AGGREGATOR)); else
		 * user.setRoles(roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_RETAILER)
		 * ); user.setPwd(passwordEncoder.encode("user" + i)); user.setState(13);
		 * user.setCountry(101); user.setCity(1126); user.setIsActive(1);
		 * user.setZipcode("122001"); userList.add(user); }
		 * 
		 * userInfoRepository.saveAll(userList);
		 */

//        UserInfo Distributor = new UserInfo();
//        Distributor.setUsername("Distributor");
//        Distributor.setAddress("Sector 17A");
//        Distributor.setCity("Gurgaon");
//        Distributor.setFirstName("Distributor");
//        Distributor.setLastName("User");
//        Distributor.setMobileNumber("7053007405");
//        Distributor.setRoles(roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_DISTRIBUTOR));
//        Distributor.setPwd(passwordEncoder.encode("test"));
//        Distributor.setState("Haryana");
//        Distributor.setIsActive(1);
//        Distributor.setZipcode("122001");
//        if (userInfoRepository.findByUsername("Distributor") == null) {
//            userInfoRepository.save(Distributor);
//        }
//
//        UserInfo MDistributor = new UserInfo();
//        MDistributor.setUsername("MDistributor");
//        MDistributor.setAddress("Sector 17A");
//        MDistributor.setCity("Gurgaon");
//        MDistributor.setFirstName("Master");
//        MDistributor.setLastName("Distributor");
//        MDistributor.setMobileNumber("7053007405");
//        MDistributor.setRoles(roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR));
//        MDistributor.setPwd(passwordEncoder.encode("test"));
//        MDistributor.setState("Haryana");
//        MDistributor.setIsActive(1);
//        MDistributor.setZipcode("122001");
//        if (userInfoRepository.findByUsername("MDistributor") == null) {
//            userInfoRepository.save(MDistributor);
//        }
//
//        UserInfo Aggregator = new UserInfo();
//        Aggregator.setUsername("Aggregator");
//        Aggregator.setAddress("Sector 17A");
//        Aggregator.setCity("Gurgaon");
//        Aggregator.setFirstName("Aggregator");
//        Aggregator.setLastName("User");
//        Aggregator.setMobileNumber("7053007405");
//        Aggregator.setRoles(roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_AGGREGATOR));
//        Aggregator.setPwd(passwordEncoder.encode("test"));
//        Aggregator.setState("Haryana");
//        Aggregator.setIsActive(1);
//        Aggregator.setZipcode("122001");
//        if (userInfoRepository.findByUsername("Aggregator") == null) {
//            userInfoRepository.save(Aggregator);
//        }

		logger.info("Successfully inserted records inside user table!");
	}
}
