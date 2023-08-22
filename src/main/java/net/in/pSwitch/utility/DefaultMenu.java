package net.in.pSwitch.utility;

import java.util.ArrayList;
import java.util.List;

import net.in.pSwitch.model.Menu;
import net.in.pSwitch.repository.MenuRepository;

public class DefaultMenu {

	/***
	 * 
	 * @param menuRepository
	 */
	public static void addDefaultMenu(MenuRepository menuRepository) {

//		menuRepository.deleteAll();

		Menu dashboard = new Menu();
		dashboard.setShowMenuAll(1);
		dashboard.setIsActive(1);
		dashboard.setMenuName(StringLiteral.MENU_DASHBOARD);
		dashboard.setPageUrl("/index");
		dashboard.setMenuIcon("icon-home4");
		if (menuRepository.findByMenuName(StringLiteral.MENU_DASHBOARD) == null) {
			menuRepository.save(dashboard);
		}

		Menu manageMenu = new Menu();
		manageMenu.setAdminMenu(1);
		manageMenu.setIsActive(1);
		manageMenu.setMenuIcon("fas fa-file-alt");
		manageMenu.setMenuName(StringLiteral.MENU_MANAGE_MENU);
		manageMenu.setPageUrl("/admin/menu/");
		if (menuRepository.findByMenuName(StringLiteral.MENU_MANAGE_MENU) == null) {
			menuRepository.save(manageMenu);
		}
		
		Menu manageFundRequest = new Menu();
		manageFundRequest.setDistributorMenu(1);
		manageFundRequest.setIsActive(1);
		manageFundRequest.setMenuIcon("fas fa-file-alt");
		manageFundRequest.setMenuName(StringLiteral.MENU_MANAGE_FUND_REQUEST);
		manageFundRequest.setPageUrl("/user/manageFundRequest");
		if (menuRepository.findByMenuName(StringLiteral.MENU_MANAGE_FUND_REQUEST) == null) {
			menuRepository.save(manageFundRequest);
		}

		Menu manageProduct = new Menu();
		manageProduct.setAdminMenu(1);
		manageProduct.setIsActive(1);
		manageProduct.setMenuIcon("fab fa-product-hunt");
		manageProduct.setMenuName(StringLiteral.MENU_MANAGE_PRODUCT);
		manageProduct.setPageUrl("/admin/product/");

		if (menuRepository.findByMenuName(StringLiteral.MENU_MANAGE_PRODUCT) == null) {
			menuRepository.save(manageProduct);
		}
		

		Menu manageBanner = new Menu();
		manageBanner.setAdminMenu(1);
		manageBanner.setIsActive(1);
		manageBanner.setMenuIcon("fas fa-flag");
		manageBanner.setMenuName(StringLiteral.MENU_MANAGE_BANNER);
		manageBanner.setPageUrl("/admin/bannerList");

		if (menuRepository.findByMenuName(StringLiteral.MENU_MANAGE_BANNER) == null) {
			menuRepository.save(manageBanner);
		}

		Menu manageProductType = new Menu();
		manageProductType.setAdminMenu(1);
		manageProductType.setIsActive(1);
		manageProductType.setMenuIcon("fas fa-clipboard-list");
		manageProductType.setMenuName(StringLiteral.MENU_MANAGE_PRODUCT_TYPE);
		manageProductType.setPageUrl("/admin/productTypeList");

		if (menuRepository.findByMenuName(StringLiteral.MENU_MANAGE_PRODUCT_TYPE) == null) {
			menuRepository.save(manageProductType);
		}

		Menu manageNews = new Menu();
		manageNews.setAdminMenu(1);
		manageNews.setIsActive(1);
		manageNews.setMenuIcon("fas fa-bell");
		manageNews.setMenuName(StringLiteral.MENU_MANAGE_NEWS);
		manageNews.setPageUrl("/admin/productNews");

		if (menuRepository.findByMenuName(StringLiteral.MENU_MANAGE_NEWS) == null) {
			menuRepository.save(manageNews);
		}

		Menu manageUser = new Menu();
		manageUser.setAdminMenu(1);
		manageUser.setIsActive(1);
		manageUser.setMenuIcon("icon-users");
		manageUser.setMenuName(StringLiteral.MENU_MANAGE_USER);
		manageUser.setPageUrl("/admin/user/");
		if (menuRepository.findByMenuName(StringLiteral.MENU_MANAGE_USER) == null) {
			menuRepository.save(manageUser);
		}

		Menu manageAssociate = new Menu();
		manageAssociate.setShowMenuAll(1);
		manageAssociate.setAdminMenu(0);
		manageAssociate.setRetailerMenu(0);
		manageAssociate.setIsActive(1);
		manageAssociate.setMenuIcon("icon-users");
		manageAssociate.setMenuName(StringLiteral.MENU_MANAGE_ASSOCIATES);
		manageAssociate.setPageUrl("/service/associates");
		if (menuRepository.findByMenuName(StringLiteral.MENU_MANAGE_ASSOCIATES) == null) {
			menuRepository.save(manageAssociate);
		}

		Menu dmtMenu = new Menu();
		dmtMenu.setRetailerMenu(1);
		dmtMenu.setIsActive(1);
		dmtMenu.setMenuIcon("fas fa-rupee-sign");
		dmtMenu.setMenuName(StringLiteral.MENU_DMT);
		dmtMenu.setPageUrl("/retailer/dmtHome.html");
		if (menuRepository.findByMenuName(StringLiteral.MENU_DMT) == null) {
			menuRepository.save(dmtMenu);
		}

		Menu aepsMenu = new Menu();
		aepsMenu.setRetailerMenu(1);
		aepsMenu.setIsActive(1);
		aepsMenu.setMenuIcon("fas fa-credit-card");
		aepsMenu.setMenuName(StringLiteral.MENU_AEPS);
		aepsMenu.setPageUrl("/retailer/aeps");
		if (menuRepository.findByMenuName(StringLiteral.MENU_AEPS) == null) {
			menuRepository.save(aepsMenu);
		}

		Menu bbpsMenu = new Menu();
		bbpsMenu.setRetailerMenu(1);
		bbpsMenu.setIsActive(1);
		bbpsMenu.setMenuIcon("fas fa-file-invoice-dollar");
		bbpsMenu.setMenuName(StringLiteral.MENU_BBPS);
		bbpsMenu.setPageUrl("/retailer/bbps");
		if (menuRepository.findByMenuName(StringLiteral.MENU_BBPS) == null) {
			menuRepository.save(bbpsMenu);
		}

		Menu aadhaarPayMenu = new Menu();
		aadhaarPayMenu.setRetailerMenu(1);
		aadhaarPayMenu.setIsActive(1);
		aadhaarPayMenu.setMenuIcon("fas fa-fingerprint");
		aadhaarPayMenu.setMenuName(StringLiteral.MENU_AADHAARPAY);
		aadhaarPayMenu.setPageUrl("/retailer/aadhaarPay");
		if (menuRepository.findByMenuName(StringLiteral.MENU_AADHAARPAY) == null) {
			menuRepository.save(aadhaarPayMenu);
		}

		Menu insurancesMenu = new Menu();
		insurancesMenu.setRetailerMenu(1);
		insurancesMenu.setIsActive(1);
		insurancesMenu.setMenuIcon("fas fa-car-crash");
		insurancesMenu.setMenuName(StringLiteral.MENU_INSURANCES);
		insurancesMenu.setPageUrl("/retailer/insurances");
		if (menuRepository.findByMenuName(StringLiteral.MENU_INSURANCES) == null) {
			menuRepository.save(insurancesMenu);
		}

		Menu ticketingMenu = new Menu();
		ticketingMenu.setRetailerMenu(1);
		ticketingMenu.setIsActive(1);
		ticketingMenu.setMenuIcon("icon-ticket");
		ticketingMenu.setMenuName(StringLiteral.MENU_TICKETING);
		ticketingMenu.setPageUrl("/retailer/ticketing");
		if (menuRepository.findByMenuName(StringLiteral.MENU_TICKETING) == null) {
			menuRepository.save(ticketingMenu);
		}

		Menu bookingMenu = new Menu();
		bookingMenu.setRetailerMenu(1);
		bookingMenu.setIsActive(1);
		bookingMenu.setMenuIcon("fa fa-hotel");
		bookingMenu.setMenuName(StringLiteral.MENU_BOOKING);
		bookingMenu.setPageUrl("/retailer/booking");
		if (menuRepository.findByMenuName(StringLiteral.MENU_BOOKING) == null) {
			menuRepository.save(bookingMenu);
		}

		addG2CMenu(menuRepository);
		addB2CMenu(menuRepository);
		addFinancialInclusionMenu(menuRepository);
		addATMMenu(menuRepository);
		addPOSTerminalMenu(menuRepository);
		addOtherServicesMenu(menuRepository);

	}

	/***
	 * 
	 * @param menuRepository
	 */
	private static void addFinancialInclusionMenu(MenuRepository menuRepository) {

		Menu finMenu = new Menu();
		finMenu.setShowMenuAll(1);
		finMenu.setIsActive(1);
		finMenu.setMenuIcon("icon-cash4");
		finMenu.setMenuName(StringLiteral.MENU_FINANCIAL_INCLUSION);
		finMenu.setPageUrl("#");

		List<Menu> fin = new ArrayList<Menu>();

		Menu finBankingMenu = getFINBankingMenu(menuRepository);
		Menu finCSPMenu = getFINCSPMenu(menuRepository);

		Menu finPensionMenu = new Menu();
		finPensionMenu.setShowMenuAll(1);
		finPensionMenu.setIsActive(1);
		finPensionMenu.setMenuIcon("fas fa-car-crash");
		finPensionMenu.setMenuName(StringLiteral.MENU_FIN_PENSIONS);
		finPensionMenu.setPageUrl("/service/pension");

		Menu finCashCollectionRecoveryMenu = new Menu();
		finCashCollectionRecoveryMenu.setShowMenuAll(1);
		finCashCollectionRecoveryMenu.setIsActive(1);
		finCashCollectionRecoveryMenu.setMenuIcon("fas fa-car-crash");
		finCashCollectionRecoveryMenu.setMenuName(StringLiteral.MENU_FIN_CASH_COLLECTION_RECOVERY);
		finCashCollectionRecoveryMenu.setPageUrl("/service/cashCollection");

		fin.add(finBankingMenu);
		fin.add(finCSPMenu);
		fin.add(finCashCollectionRecoveryMenu);
		fin.add(finPensionMenu);

		finMenu.setMenus(fin);

		if (menuRepository.findByMenuName(StringLiteral.MENU_FINANCIAL_INCLUSION) == null) {
			finMenu = menuRepository.save(finMenu);
		}
	}

	private static Menu getFINCSPMenu(MenuRepository menuRepository) {
		Menu finCSPMenu = new Menu();
		finCSPMenu.setShowMenuAll(1);
		finCSPMenu.setIsActive(1);
		finCSPMenu.setMenuName(StringLiteral.MENU_FIN_CSP);
		finCSPMenu.setPageUrl("#");

		List<Menu> finCSP = new ArrayList<Menu>();

		Menu finCSPSBIMenu = new Menu();
		finCSPSBIMenu.setShowMenuAll(1);
		finCSPSBIMenu.setIsActive(1);
		finCSPSBIMenu.setMenuIcon("fas fa-car-crash");
		finCSPSBIMenu.setMenuName(StringLiteral.MENU_FIN_CSP_SBI);
		finCSPSBIMenu.setPageUrl("/service/sbi");

		finCSP.add(finCSPSBIMenu);

		finCSPMenu.setMenus(finCSP);

		return finCSPMenu;
	}

	private static Menu getFINBankingMenu(MenuRepository menuRepository) {
		Menu finBankingMenu = new Menu();
		finBankingMenu.setShowMenuAll(1);
		finBankingMenu.setIsActive(1);
		finBankingMenu.setMenuName(StringLiteral.MENU_FIN_BANKING);
		finBankingMenu.setPageUrl("#");

		List<Menu> finBankingTicketing = new ArrayList<Menu>();

		Menu finBankingLoanMenu = new Menu();
		finBankingLoanMenu.setShowMenuAll(1);
		finBankingLoanMenu.setIsActive(1);
		finBankingLoanMenu.setMenuIcon("fas fa-car-crash");
		finBankingLoanMenu.setMenuName(StringLiteral.MENU_FIN_BANK_LOAN);
		finBankingLoanMenu.setPageUrl("/service/loan");

		Menu finBankingAccountOpenMenu = getFINBankingAccountOpenMenu(menuRepository);

		finBankingTicketing.add(finBankingLoanMenu);
		finBankingTicketing.add(finBankingAccountOpenMenu);

		finBankingMenu.setMenus(finBankingTicketing);

		return finBankingMenu;
	}

	private static Menu getFINBankingAccountOpenMenu(MenuRepository menuRepository) {
		Menu finBankingAccountOpenMenu = new Menu();
		finBankingAccountOpenMenu.setShowMenuAll(1);
		finBankingAccountOpenMenu.setIsActive(1);
		finBankingAccountOpenMenu.setMenuIcon("fas fa-car-crash");
		finBankingAccountOpenMenu.setMenuName(StringLiteral.MENU_FIN_BANK_ACCOUNT_OPENING);
		finBankingAccountOpenMenu.setPageUrl("#");

		List<Menu> finBankingAccountOpen = new ArrayList<Menu>();

		Menu finAccountSavingMenu = new Menu();
		finAccountSavingMenu.setShowMenuAll(1);
		finAccountSavingMenu.setIsActive(1);
		finAccountSavingMenu.setMenuIcon("fas fa-car-crash");
		finAccountSavingMenu.setMenuName(StringLiteral.MENU_FIN_BANK_ACCOUNT_OPENING_SAVING);
		finAccountSavingMenu.setPageUrl("/service/savingAccount");

		Menu finAccountCurrentMenu = new Menu();
		finAccountCurrentMenu.setShowMenuAll(1);
		finAccountCurrentMenu.setIsActive(1);
		finAccountCurrentMenu.setMenuIcon("fas fa-car-crash");
		finAccountCurrentMenu.setMenuName(StringLiteral.MENU_FIN_BANK_ACCOUNT_OPENING_CURRENT);
		finAccountCurrentMenu.setPageUrl("/service/currentAccount");

		finBankingAccountOpen.add(finAccountSavingMenu);
		finBankingAccountOpen.add(finAccountCurrentMenu);

		finBankingAccountOpenMenu.setMenus(finBankingAccountOpen);

		return finBankingAccountOpenMenu;
	}

	/***
	 * 
	 * @param menuRepository
	 */
	private static void addATMMenu(MenuRepository menuRepository) {

		Menu atmMenu = new Menu();
		atmMenu.setShowMenuAll(1);
		atmMenu.setIsActive(1);
		atmMenu.setMenuIcon("icon-credit-card2");
		atmMenu.setMenuName(StringLiteral.MENU_ATM);
		atmMenu.setPageUrl("#");

		List<Menu> atm = new ArrayList<Menu>();

		Menu atmMicroATMMenu = new Menu();
		atmMicroATMMenu.setShowMenuAll(1);
		atmMicroATMMenu.setIsActive(1);
		atmMicroATMMenu.setMenuIcon("fas fa-car-crash");
		atmMicroATMMenu.setMenuName(StringLiteral.MENU_ATM_MICRO_ATM);
		atmMicroATMMenu.setPageUrl("/service/microATM");

		Menu atmPaymentSoundboxMenu = new Menu();
		atmPaymentSoundboxMenu.setShowMenuAll(1);
		atmPaymentSoundboxMenu.setIsActive(1);
		atmPaymentSoundboxMenu.setMenuIcon("fas fa-car-crash");
		atmPaymentSoundboxMenu.setMenuName(StringLiteral.MENU_ATM_PAYMENT_SOUNDBOX);
		atmPaymentSoundboxMenu.setPageUrl("/service/atmPaymentSoundbox");

		Menu atmCRMMenu = new Menu();
		atmCRMMenu.setShowMenuAll(1);
		atmCRMMenu.setIsActive(1);
		atmCRMMenu.setMenuIcon("fas fa-car-crash");
		atmCRMMenu.setMenuName(StringLiteral.MENU_ATM_CRM);
		atmCRMMenu.setPageUrl("/service/CRM");

		atm.add(atmMicroATMMenu);
		atm.add(atmPaymentSoundboxMenu);
		atm.add(atmCRMMenu);

		atmMenu.setMenus(atm);

		if (menuRepository.findByMenuName(StringLiteral.MENU_ATM) == null) {
			atmMenu = menuRepository.save(atmMenu);
		}
	}

	/***
	 * 
	 * @param menuRepository
	 */

	private static void addPOSTerminalMenu(MenuRepository menuRepository) {

		Menu terminalMenu = new Menu();
		terminalMenu.setShowMenuAll(1);
		terminalMenu.setIsActive(1);
		terminalMenu.setMenuIcon("fas fa-mobile-alt");
		terminalMenu.setMenuName(StringLiteral.MENU_POS_TERMINAL);
		terminalMenu.setPageUrl("#");

		List<Menu> terminal = new ArrayList<Menu>();

//		Menu terminalMPOSMenu = new Menu();
//		terminalMPOSMenu.setShowMenuAll(1);
//		terminalMPOSMenu.setIsActive(1);
//		terminalMPOSMenu.setMenuIcon("fas fa-car-crash");
//		terminalMPOSMenu.setMenuName(StringLiteral.MENU_TERMINAL_MPOS);
//		terminalMPOSMenu.setPageUrl("/MPOS");
//
//		Menu terminalAndroidPOSMenu = new Menu();
//		terminalAndroidPOSMenu.setShowMenuAll(1);
//		terminalAndroidPOSMenu.setIsActive(1);
//		terminalAndroidPOSMenu.setMenuIcon("fas fa-car-crash");
//		terminalAndroidPOSMenu.setMenuName(StringLiteral.MENU_TERMINAL_ANDROID_POS);
//		terminalAndroidPOSMenu.setPageUrl("/androidPOS");
//
//		terminal.add(terminalMPOSMenu);
//		terminal.add(terminalAndroidPOSMenu);

		terminalMenu.setMenus(terminal);

		if (menuRepository.findByMenuName(StringLiteral.MENU_POS_TERMINAL) == null) {
			terminalMenu = menuRepository.save(terminalMenu);
		}
	}

	/***
	 * 
	 * @param menuRepository
	 */

	private static void addOtherServicesMenu(MenuRepository menuRepository) {

		Menu otherMenu = new Menu();
		otherMenu.setShowMenuAll(1);
		otherMenu.setIsActive(1);
		otherMenu.setMenuIcon("icon-indent-decrease2");
		otherMenu.setMenuName(StringLiteral.MENU_OTHER_SERVICE);
		otherMenu.setPageUrl("#");

		List<Menu> other = new ArrayList<Menu>();

		Menu otherResellerProgramMenu = new Menu();
		otherResellerProgramMenu.setShowMenuAll(1);
		otherResellerProgramMenu.setIsActive(1);
		otherResellerProgramMenu.setMenuIcon("fas fa-car-crash");
		otherResellerProgramMenu.setMenuName(StringLiteral.MENU_OTHER_RESELLER_PROGRAM);
		otherResellerProgramMenu.setPageUrl("/service/reseller");

		Menu otherPaymentGatewayMenu = new Menu();
		otherPaymentGatewayMenu.setShowMenuAll(1);
		otherPaymentGatewayMenu.setIsActive(1);
		otherPaymentGatewayMenu.setMenuIcon("fas fa-car-crash");
		otherPaymentGatewayMenu.setMenuName(StringLiteral.MENU_OTHER_PAYMENT_GATEWAY);
		otherPaymentGatewayMenu.setPageUrl("/service/paymentGateway");

		Menu otherPaymentSoundboxMenu = new Menu();
		otherPaymentSoundboxMenu.setShowMenuAll(1);
		otherPaymentSoundboxMenu.setIsActive(1);
		otherPaymentSoundboxMenu.setMenuIcon("fas fa-car-crash");
		otherPaymentSoundboxMenu.setMenuName(StringLiteral.MENU_OTHER_Payment_Soundbox);
		otherPaymentSoundboxMenu.setPageUrl("/service/paymentSoundbox");

		Menu otherSarkariYojnaMenu = new Menu();
		otherSarkariYojnaMenu.setShowMenuAll(1);
		otherSarkariYojnaMenu.setIsActive(1);
		otherSarkariYojnaMenu.setMenuIcon("fas fa-car-crash");
		otherSarkariYojnaMenu.setMenuName(StringLiteral.MENU_OTHER_Sarkari_Yojna);
		otherSarkariYojnaMenu.setPageUrl("/service/sarkariYojna");

		Menu otherEducationalMenu = new Menu();
		otherEducationalMenu.setShowMenuAll(1);
		otherEducationalMenu.setIsActive(1);
		otherEducationalMenu.setMenuIcon("fas fa-car-crash");
		otherEducationalMenu.setMenuName(StringLiteral.MENU_OTHER_Educational);
		otherEducationalMenu.setPageUrl("/service/educational");

		Menu otherLegalServicesMenu = new Menu();
		otherLegalServicesMenu.setShowMenuAll(1);
		otherLegalServicesMenu.setIsActive(1);
		otherLegalServicesMenu.setMenuIcon("fas fa-car-crash");
		otherLegalServicesMenu.setMenuName(StringLiteral.MENU_OTHER_Legal_Services);
		otherLegalServicesMenu.setPageUrl("/service/legalServices");

		Menu otherDSCMenu = new Menu();
		otherDSCMenu.setShowMenuAll(1);
		otherDSCMenu.setIsActive(1);
		otherDSCMenu.setMenuIcon("fas fa-car-crash");
		otherDSCMenu.setMenuName(StringLiteral.MENU_OTHER_DSC);
		otherDSCMenu.setPageUrl("/service/dsc");

		Menu otherAgriRentalMenu = new Menu();
		otherAgriRentalMenu.setShowMenuAll(1);
		otherAgriRentalMenu.setIsActive(1);
		otherAgriRentalMenu.setMenuIcon("fas fa-car-crash");
		otherAgriRentalMenu.setMenuName(StringLiteral.MENU_OTHER_Agri_Rental);
		otherAgriRentalMenu.setPageUrl("/service/agriRental");

		Menu otherElectronicHardwareMenu = new Menu();
		otherElectronicHardwareMenu.setShowMenuAll(1);
		otherElectronicHardwareMenu.setIsActive(1);
		otherElectronicHardwareMenu.setMenuIcon("fas fa-car-crash");
		otherElectronicHardwareMenu.setMenuName(StringLiteral.MENU_OTHER_Electronic_Hardware);
		otherElectronicHardwareMenu.setPageUrl("/service/electronicHardware");

		other.add(otherResellerProgramMenu);
		other.add(otherPaymentGatewayMenu);
		other.add(otherPaymentSoundboxMenu);
		other.add(otherSarkariYojnaMenu);
		other.add(otherEducationalMenu);
		other.add(otherLegalServicesMenu);
		other.add(otherDSCMenu);
		other.add(otherAgriRentalMenu);
		other.add(otherElectronicHardwareMenu);

		otherMenu.setMenus(other);

		if (menuRepository.findByMenuName(StringLiteral.MENU_OTHER_SERVICE) == null) {
			otherMenu = menuRepository.save(otherMenu);
		}
	}

	/***
	 * 
	 * @param menuRepository
	 */
	private static void addB2CMenu(MenuRepository menuRepository) {

		Menu b2cMenu = new Menu();
		b2cMenu.setShowMenuAll(1);
		b2cMenu.setIsActive(1);
		b2cMenu.setMenuIcon("fas fa-building");
		b2cMenu.setMenuName(StringLiteral.MENU_BUSINESS_TO_CONSUMER);
		b2cMenu.setPageUrl("#");

		List<Menu> b2c = new ArrayList<Menu>();

		Menu b2cTicketingMenu = getB2CTicketingMenu(menuRepository);
		Menu b2cBookingMenu = getB2CBookingMenu(menuRepository);
		Menu b2cTelcomeRechargeMenu = getB2CTelcomeRechargeMenu(menuRepository);

		Menu b2cQRCodeMenu = new Menu();
		b2cQRCodeMenu.setShowMenuAll(1);
		b2cQRCodeMenu.setIsActive(1);
		b2cQRCodeMenu.setMenuName(StringLiteral.MENU_B2C_QR_CODE_STICKER);
		b2cQRCodeMenu.setPageUrl("/service/qrCode");

		b2c.add(b2cQRCodeMenu);
		b2c.add(b2cTicketingMenu);
		b2c.add(b2cBookingMenu);
		b2c.add(b2cTelcomeRechargeMenu);

		b2cMenu.setMenus(b2c);

		if (menuRepository.findByMenuName(StringLiteral.MENU_BUSINESS_TO_CONSUMER) == null) {
			b2cMenu = menuRepository.save(b2cMenu);
		}
	}

	/***
	 * 
	 * @param menuRepository
	 * @return
	 */
	private static Menu getB2CTelcomeRechargeMenu(MenuRepository menuRepository) {
		Menu b2cTelecomRechargeMenu = new Menu();
		b2cTelecomRechargeMenu.setShowMenuAll(1);
		b2cTelecomRechargeMenu.setIsActive(1);
		b2cTelecomRechargeMenu.setMenuName(StringLiteral.MENU_B2C_TELECOM_RECHARGES);
		b2cTelecomRechargeMenu.setPageUrl("#");

		List<Menu> b2cTelcomeRecharge = new ArrayList<Menu>();

		Menu b2cTelecomRechargePrePaidMenu = new Menu();
		b2cTelecomRechargePrePaidMenu.setShowMenuAll(1);
		b2cTelecomRechargePrePaidMenu.setIsActive(1);
		b2cTelecomRechargePrePaidMenu.setMenuIcon("fas fa-car-crash");
		b2cTelecomRechargePrePaidMenu.setMenuName(StringLiteral.MENU_B2C_TELECOM_RECHARGES_PRE_PAID);
		b2cTelecomRechargePrePaidMenu.setPageUrl("/service/prePaidRecharge");

		Menu b2cTelecomRechargeDTHMenu = new Menu();
		b2cTelecomRechargeDTHMenu.setShowMenuAll(1);
		b2cTelecomRechargeDTHMenu.setIsActive(1);
		b2cTelecomRechargeDTHMenu.setMenuIcon("fas fa-car-crash");
		b2cTelecomRechargeDTHMenu.setMenuName(StringLiteral.MENU_B2C_TELECOM_RECHARGES_DTH);
		b2cTelecomRechargeDTHMenu.setPageUrl("/service/DTHRecharge");

		b2cTelcomeRecharge.add(b2cTelecomRechargePrePaidMenu);
		b2cTelcomeRecharge.add(b2cTelecomRechargeDTHMenu);

		b2cTelecomRechargeMenu.setMenus(b2cTelcomeRecharge);

		return b2cTelecomRechargeMenu;
	}

	/***
	 * 
	 * @param menuRepository
	 * @return
	 */
	private static Menu getB2CBookingMenu(MenuRepository menuRepository) {
		Menu b2cBookingMenu = new Menu();
		b2cBookingMenu.setShowMenuAll(1);
		b2cBookingMenu.setIsActive(1);
		b2cBookingMenu.setMenuName(StringLiteral.MENU_B2C_BOOKING);
		b2cBookingMenu.setPageUrl("#");

		List<Menu> b2cBooking = new ArrayList<Menu>();

		Menu b2cBookingHotelMenu = new Menu();
		b2cBookingHotelMenu.setShowMenuAll(1);
		b2cBookingHotelMenu.setIsActive(1);
		b2cBookingHotelMenu.setMenuIcon("fas fa-car-crash");
		b2cBookingHotelMenu.setMenuName(StringLiteral.MENU_B2C_BOOKING_HOTEL);
		b2cBookingHotelMenu.setPageUrl("/service/hotel");

		Menu b2cBookingResortMenu = new Menu();
		b2cBookingResortMenu.setShowMenuAll(1);
		b2cBookingResortMenu.setIsActive(1);
		b2cBookingResortMenu.setMenuIcon("fas fa-car-crash");
		b2cBookingResortMenu.setMenuName(StringLiteral.MENU_B2C_BOOKING_RESORT);
		b2cBookingResortMenu.setPageUrl("/service/resort");

		b2cBooking.add(b2cBookingHotelMenu);
		b2cBooking.add(b2cBookingResortMenu);

		b2cBookingMenu.setMenus(b2cBooking);

		return b2cBookingMenu;
	}

	/***
	 * 
	 * @param menuRepository
	 * @return
	 */
	private static Menu getB2CTicketingMenu(MenuRepository menuRepository) {
		Menu b2cTicketingMenu = new Menu();
		b2cTicketingMenu.setShowMenuAll(1);
		b2cTicketingMenu.setIsActive(1);
		b2cTicketingMenu.setMenuName(StringLiteral.MENU_B2C_TICKETING);
		b2cTicketingMenu.setPageUrl("#");

		List<Menu> b2cTicketing = new ArrayList<Menu>();

		Menu b2cTicketingIRCTCMenu = new Menu();
		b2cTicketingIRCTCMenu.setShowMenuAll(1);
		b2cTicketingIRCTCMenu.setIsActive(1);
		b2cTicketingIRCTCMenu.setMenuIcon("fas fa-car-crash");
		b2cTicketingIRCTCMenu.setMenuName(StringLiteral.MENU_B2C_TICKETING_IRCTC);
		b2cTicketingIRCTCMenu.setPageUrl("/service/irctc");

		Menu b2cTicketingBUSMenu = new Menu();
		b2cTicketingBUSMenu.setShowMenuAll(1);
		b2cTicketingBUSMenu.setIsActive(1);
		b2cTicketingBUSMenu.setMenuIcon("fas fa-car-crash");
		b2cTicketingBUSMenu.setMenuName(StringLiteral.MENU_B2C_TICKETING_BUS);
		b2cTicketingBUSMenu.setPageUrl("/service/bus");

		Menu b2cTicketingFLIGHTMenu = new Menu();
		b2cTicketingFLIGHTMenu.setShowMenuAll(1);
		b2cTicketingFLIGHTMenu.setIsActive(1);
		b2cTicketingFLIGHTMenu.setMenuIcon("fas fa-car-crash");
		b2cTicketingFLIGHTMenu.setMenuName(StringLiteral.MENU_B2C_TICKETING_FLIGHT);
		b2cTicketingFLIGHTMenu.setPageUrl("/service/flight");

		b2cTicketing.add(b2cTicketingIRCTCMenu);
		b2cTicketing.add(b2cTicketingBUSMenu);
		b2cTicketing.add(b2cTicketingFLIGHTMenu);

		b2cTicketingMenu.setMenus(b2cTicketing);

		return b2cTicketingMenu;
	}

	/***
	 * 
	 * @param menuRepository
	 */
	private static void addG2CMenu(MenuRepository menuRepository) {

		Menu g2cMenu = new Menu();
		g2cMenu.setShowMenuAll(1);
		g2cMenu.setIsActive(1);
		g2cMenu.setMenuIcon("fas fa-university");
		g2cMenu.setMenuName(StringLiteral.MENU_GOVERNMENT_TO_CONSUMER);
		g2cMenu.setPageUrl("#");

		List<Menu> g2c = new ArrayList<Menu>();

		Menu g2cInsurancesMenu = getG2CInsurancesMenu(menuRepository);
		Menu g2cEDistrictMenu = getG2CEDistrictMenu(menuRepository);

		Menu g2cFasTagMenu = new Menu();
		g2cFasTagMenu.setShowMenuAll(1);
		g2cFasTagMenu.setIsActive(1);
		g2cFasTagMenu.setMenuIcon("fas fa-car-crash");
		g2cFasTagMenu.setMenuName(StringLiteral.MENU_G2C_FASTAG);
//		g2cFasTagMenu.setPageUrl("/service/fasTag");

		Menu g2cPanCentreMenu = new Menu();
		g2cPanCentreMenu.setShowMenuAll(1);
		g2cPanCentreMenu.setIsActive(1);
		g2cPanCentreMenu.setMenuIcon("fas fa-car-crash");
		g2cPanCentreMenu.setMenuName(StringLiteral.MENU_G2C_PAN_CENTRE);
		g2cPanCentreMenu.setPageUrl("/service/panCentre");

		Menu g2cAadhaarCentreMenu = new Menu();
		g2cAadhaarCentreMenu.setShowMenuAll(1);
		g2cAadhaarCentreMenu.setIsActive(1);
		g2cAadhaarCentreMenu.setMenuIcon("fas fa-car-crash");
		g2cAadhaarCentreMenu.setMenuName(StringLiteral.MENU_G2C_AADHAAR_CENTRE);
		g2cAadhaarCentreMenu.setPageUrl("/service/aadhaarCentre");

		g2c.add(g2cInsurancesMenu);
		g2c.add(g2cEDistrictMenu);
		g2c.add(g2cFasTagMenu);
		g2c.add(g2cPanCentreMenu);
		g2c.add(g2cAadhaarCentreMenu);

		g2cMenu.setMenus(g2c);

		if (menuRepository.findByMenuName(StringLiteral.MENU_GOVERNMENT_TO_CONSUMER) == null) {
			g2cMenu = menuRepository.save(g2cMenu);
		}
	}

	/***
	 * 
	 * @param menuRepository
	 * @return
	 */
	private static Menu getG2CInsurancesMenu(MenuRepository menuRepository) {
		Menu g2cInsurancesMenu = new Menu();
		g2cInsurancesMenu.setShowMenuAll(1);
		g2cInsurancesMenu.setIsActive(1);
		g2cInsurancesMenu.setMenuIcon("fas fa-car-crash");
		g2cInsurancesMenu.setMenuName(StringLiteral.MENU_G2C_INSURANCES);
		g2cInsurancesMenu.setPageUrl("#");

		List<Menu> g2cInsurances = new ArrayList<Menu>();

		Menu g2cInsGeneralInsurancesMenu = new Menu();
		g2cInsGeneralInsurancesMenu.setShowMenuAll(1);
		g2cInsGeneralInsurancesMenu.setIsActive(1);
		g2cInsGeneralInsurancesMenu.setMenuIcon("fas fa-car-crash");
		g2cInsGeneralInsurancesMenu.setMenuName(StringLiteral.MENU_G2C_INS_GENERAL_INSURANCES);
		g2cInsGeneralInsurancesMenu.setPageUrl("/service/generalInsurances");

		Menu g2cInsLifeInsurancesMenu = new Menu();
		g2cInsLifeInsurancesMenu.setShowMenuAll(1);
		g2cInsLifeInsurancesMenu.setIsActive(1);
		g2cInsLifeInsurancesMenu.setMenuIcon("fas fa-car-crash");
		g2cInsLifeInsurancesMenu.setMenuName(StringLiteral.MENU_G2C_INS_LIFE_INSURANCES);
		g2cInsLifeInsurancesMenu.setPageUrl("/service/lifeInsurances");

		Menu g2cInsHealthInsurancesMenu = new Menu();
		g2cInsHealthInsurancesMenu.setShowMenuAll(1);
		g2cInsHealthInsurancesMenu.setIsActive(1);
		g2cInsHealthInsurancesMenu.setMenuIcon("fas fa-car-crash");
		g2cInsHealthInsurancesMenu.setMenuName(StringLiteral.MENU_G2C_INS_HEALTH_INSURANCES);
		g2cInsHealthInsurancesMenu.setPageUrl("/service/healthInsurances");

		g2cInsurances.add(g2cInsGeneralInsurancesMenu);
		g2cInsurances.add(g2cInsLifeInsurancesMenu);
		g2cInsurances.add(g2cInsHealthInsurancesMenu);

		g2cInsurancesMenu.setMenus(g2cInsurances);

		return g2cInsurancesMenu;
	}

	/***
	 * getG2CEDistrictMenu Return the E-District Menu for binding in UI, we can use
	 * to this to store menu in database
	 * 
	 * @param menuRepository
	 * @return
	 */
	private static Menu getG2CEDistrictMenu(MenuRepository menuRepository) {
		Menu g2cEDistrictMenu = new Menu();
		g2cEDistrictMenu.setShowMenuAll(1);
		g2cEDistrictMenu.setIsActive(1);
		g2cEDistrictMenu.setMenuIcon("fas fa-car-crash");
		g2cEDistrictMenu.setMenuName(StringLiteral.MENU_G2C_E_DISTRICT_SERVICE);
		g2cEDistrictMenu.setPageUrl("#");

		List<Menu> g2cInsurances = new ArrayList<Menu>();

		Menu g2cEDSBirthCertMenu = new Menu();
		g2cEDSBirthCertMenu.setShowMenuAll(1);
		g2cEDSBirthCertMenu.setIsActive(1);
		g2cEDSBirthCertMenu.setMenuIcon("fas fa-car-crash");
		g2cEDSBirthCertMenu.setMenuName(StringLiteral.MENU_G2C_EDS_BIRTH_CERT);
		g2cEDSBirthCertMenu.setPageUrl("/service/birthCertificate");

		Menu g2cEDSDeathCertMenu = new Menu();
		g2cEDSDeathCertMenu.setShowMenuAll(1);
		g2cEDSDeathCertMenu.setIsActive(1);
		g2cEDSDeathCertMenu.setMenuIcon("fas fa-car-crash");
		g2cEDSDeathCertMenu.setMenuName(StringLiteral.MENU_G2C_EDS_DEATH_CERT);
		g2cEDSDeathCertMenu.setPageUrl("/service/deathCertificate");

		Menu g2cEDSDomicileCertMenu = new Menu();
		g2cEDSDomicileCertMenu.setShowMenuAll(1);
		g2cEDSDomicileCertMenu.setIsActive(1);
		g2cEDSDomicileCertMenu.setMenuIcon("fas fa-car-crash");
		g2cEDSDomicileCertMenu.setMenuName(StringLiteral.MENU_G2C_EDS_DOMICILE_CERT);
		g2cEDSDomicileCertMenu.setPageUrl("/service/domicileCertificate");

		Menu g2cEDSVoterCardMenu = new Menu();
		g2cEDSVoterCardMenu.setShowMenuAll(1);
		g2cEDSVoterCardMenu.setIsActive(1);
		g2cEDSVoterCardMenu.setMenuIcon("fas fa-car-crash");
		g2cEDSVoterCardMenu.setMenuName(StringLiteral.MENU_G2C_EDS_VOTER_CARD);
		g2cEDSVoterCardMenu.setPageUrl("/service/voterCard");

		Menu g2cEDSRationCardMenu = new Menu();
		g2cEDSRationCardMenu.setShowMenuAll(1);
		g2cEDSRationCardMenu.setIsActive(1);
		g2cEDSRationCardMenu.setMenuIcon("fas fa-car-crash");
		g2cEDSRationCardMenu.setMenuName(StringLiteral.MENU_G2C_EDS_RATION_CARD);
		g2cEDSRationCardMenu.setPageUrl("/service/rationCard");

		Menu g2cEDSkisanCardMenu = new Menu();
		g2cEDSkisanCardMenu.setShowMenuAll(1);
		g2cEDSkisanCardMenu.setIsActive(1);
		g2cEDSkisanCardMenu.setMenuIcon("fas fa-car-crash");
		g2cEDSkisanCardMenu.setMenuName(StringLiteral.MENU_G2C_EDS_KISAN_CARD);
		g2cEDSkisanCardMenu.setPageUrl("/service/kisanCard");

		g2cInsurances.add(g2cEDSBirthCertMenu);
		g2cInsurances.add(g2cEDSDeathCertMenu);
		g2cInsurances.add(g2cEDSDomicileCertMenu);
		g2cInsurances.add(g2cEDSVoterCardMenu);
		g2cInsurances.add(g2cEDSRationCardMenu);
		g2cInsurances.add(g2cEDSkisanCardMenu);

		g2cEDistrictMenu.setMenus(g2cInsurances);

		return g2cEDistrictMenu;
	}
}
