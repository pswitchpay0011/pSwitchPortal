package net.in.pSwitch.utility;

public class StringLiteral {


	public static final String ACCOUNT_CREATION_STATE_STEP_1 = "INITIATE_OTP_VERIFICATION";
	public static final String ACCOUNT_CREATION_STATE_STEP_2 = "OTP_VERIFIED";
	public static final String ACCOUNT_CREATION_STATE_STEP_3 = "KYC_INITIATED";
	public static final String ACCOUNT_CREATION_STATE_STEP_4 = "KYC_VERIFICATION_PENDING";
	public static final String ACCOUNT_CREATION_STATE_STEP_5 = "KYC_COMPLETED";

	// ROLE - ADMIN
	public static final String ROLE_CODE_ADMIN = "AD";
	public static final String ROLE_CODE_OFFICE_ADMIN = "OAD";
	public static final String ROLE_CODE_BUSINESS_ASSOCIATE = "BA";
	public static final String ROLE_CODE_MANAGER_BUSINESS_ASSOCIATE = "MBA";
	public static final String ROLE_CODE_RETAILER = "R";
	public static final String ROLE_CODE_DISTRIBUTOR = "D";
	public static final String ROLE_CODE_SUPER_DISTRIBUTOR = "S";
	public static final String ROLE_CODE_SALES_EMPLOYEE = "SE";
	public static final String ROLE_CODE_FINANCE = "FN";
	public static final String ROLE_CODE_MANAGER_FINANCE = "MFN";

	public static final String ROLE_CODE_API_ROLE = "AIP";

	public static final String ROLE_NAME_API_ROLE = "Pswitch API";
	public static final String ROLE_NAME_ADMIN = "Admin";
	public static final String ROLE_NAME_OFFICE_ADMIN = "Office Admin";
	public static final String ROLE_NAME_SALES_EMPLOYEE = "Sales Employee";
	public static final String ROLE_NAME_BUSINESS_ASSOCIATE = "Business Associate";
	public static final String ROLE_NAME_MANAGER_BUSINESS_ASSOCIATE = "Manger Business Associate";
	public static final String ROLE_NAME_RETAILER = "Retailer";
	public static final String ROLE_NAME_DISTRIBUTOR = "Distributor";
	public static final String ROLE_NAME_SUPER_DISTRIBUTOR = "Super Distributor";
	public static final String ROLE_NAME_FINANCE = "Finance";
	public static final String ROLE_NAME_MANAGER_FINANCE = "Manager Finance";

	public static final String KEY_ACTIVE_PAGE = "activePage";
 
//	Admin Menu
	public static final String MENU_DASHBOARD = "Dashboard";
	public static final String MENU_MANAGE_USER = "Manage User";
	public static final String MENU_MANAGE_ASSOCIATES = "Manage Associate";

	public static final String MENU_MANAGE_MENU = "Manage Menu";
	public static final String MENU_MANAGE_PRODUCT = "Manage Product";
	public static final String MENU_MANAGE_FUND_REQUEST = "Manage Fund Request";
	public static final String MENU_MANAGE_BANNER = "Manage Banner";
	public static final String MENU_MANAGE_PRODUCT_TYPE = "Manage Product Type";
	public static final String MENU_MANAGE_NEWS = "Manage News";

//	Single Menu
	public static final String MENU_DMT = "DMT";
	public static final String MENU_AEPS = "AePS";
	public static final String MENU_BBPS = "BBPS";
	public static final String MENU_AADHAARPAY = "Aadhaarpay";
	public static final String MENU_INSURANCES = "Insurances";
	public static final String MENU_TICKETING = "Ticketing";
	public static final String MENU_BOOKING = "Booking";

//	Group Menu
	public static final String MENU_GOVERNMENT_TO_CONSUMER = "Government to Consumer";
	public static final String MENU_BUSINESS_TO_CONSUMER = "Business to Consumer (B2C)";
	public static final String MENU_FINANCIAL_INCLUSION = "Financial Inclusion";
	public static final String MENU_ATM = "ATM";
	public static final String MENU_POS_TERMINAL = "POS Terminal";
	public static final String MENU_OTHER_SERVICE = "Other Service";
	public static final String MENU_PRODUCTS = "Products";

//	Sub-Menu of GOVERNMENT TO CONSUMER

	public static final String MENU_G2C_INSURANCES = "Insurances Services";
	public static final String MENU_G2C_E_DISTRICT_SERVICE = "E-District Services";
	public static final String MENU_G2C_FASTAG = "FasTag";
	public static final String MENU_G2C_PAN_CENTRE = "Pan Centre";
	public static final String MENU_G2C_AADHAAR_CENTRE = "Aadhaar Centre";

//	Sub-Menu of MENU_G2C_INSURANCES
	public static final String MENU_G2C_INS_GENERAL_INSURANCES = "General Insurances";
	public static final String MENU_G2C_INS_LIFE_INSURANCES = "Life Insurances";
	public static final String MENU_G2C_INS_HEALTH_INSURANCES = "Health Insurances";

//	Sub-Menu of MENU_G2C_E_DISTRICT_SERVICE
	public static final String MENU_G2C_EDS_BIRTH_CERT = "Birth Certificate";
	public static final String MENU_G2C_EDS_DEATH_CERT = "Death Certificate";
	public static final String MENU_G2C_EDS_DOMICILE_CERT = "Domicile Certificate";
	public static final String MENU_G2C_EDS_VOTER_CARD = "Voter Card";
	public static final String MENU_G2C_EDS_RATION_CARD = "Ration Card";
	public static final String MENU_G2C_EDS_KISAN_CARD = "Kisan Card etc";

	/***
	 * 
	 * Sub-Menu of Business to Consumer (B2C)
	 * 
	 */

	public static final String MENU_B2C_QR_CODE_STICKER = "QR Code Sticker";
	public static final String MENU_B2C_TICKETING = "Ticketing Services";
	public static final String MENU_B2C_BOOKING = "Booking Service";
	public static final String MENU_B2C_TELECOM_RECHARGES = "Telecom Recharges";

	/***
	 * 
	 * Sub-Menu of Business to Consumer (B2C) Ticketing
	 * 
	 */

	public static final String MENU_B2C_TICKETING_IRCTC = "IRCTC";
	public static final String MENU_B2C_TICKETING_BUS = "Bus";
	public static final String MENU_B2C_TICKETING_FLIGHT = "Flight";

	/***
	 * 
	 * Sub-Menu of Business to Consumer (B2C) Booking
	 * 
	 */

	public static final String MENU_B2C_BOOKING_HOTEL = "Hotel";
	public static final String MENU_B2C_BOOKING_RESORT = "Resort";

	/***
	 * 
	 * Sub-Menu of Business to Consumer (B2C) Telecom Recharges
	 * 
	 */

	public static final String MENU_B2C_TELECOM_RECHARGES_PRE_PAID = "PrePaid";
	public static final String MENU_B2C_TELECOM_RECHARGES_DTH = "DTH";

	/***
	 * 
	 * Sub-Menu of Financial Inclusion (FIN)
	 * 
	 */

	public static final String MENU_FIN_BANKING = "Banking Services";
	public static final String MENU_FIN_CSP = "Customer Service Points(CSP)";
	public static final String MENU_FIN_CASH_COLLECTION_RECOVERY = "Cash Collection & Recovery";
	public static final String MENU_FIN_PENSIONS = "Pension";

	/***
	 * 
	 * Sub-Menu of Financial Inclusion (FIN) Banking Services
	 * 
	 */

	public static final String MENU_FIN_BANK_LOAN = "Loan";
	public static final String MENU_FIN_BANK_ACCOUNT_OPENING = "Account Opening";

	/***
	 * 
	 * Sub-Menu of Financial Inclusion (FIN)-> Banking Services -> LOAN
	 * 
	 */

	public static final String MENU_FIN_BANK_LOAN_HDFC = "HDFC Loan";
	public static final String MENU_FIN_BANK_LOAN_ICICI = "ICICI Loan";
	public static final String MENU_FIN_BANK_LOAN_RBL = "RBL Loan";
	public static final String MENU_FIN_BANK_LOAN_CAPITAL = "Capital Float";

	/***
	 * 
	 * Sub-Menu of Financial Inclusion (FIN)-> Banking Services -> LOAN
	 * 
	 */

	public static final String MENU_FIN_BANK_ACCOUNT_OPENING_SAVING = "Saving A/c";
	public static final String MENU_FIN_BANK_ACCOUNT_OPENING_CURRENT = "Current A/c";

	/***
	 * 
	 * Sub-Menu of Financial Inclusion (FIN)-> Customer Service Points(CSP)
	 * 
	 */

	public static final String MENU_FIN_CSP_SBI = "SBI";

	/***
	 * 
	 * Sub-Menu of ATM
	 * 
	 */

	public static final String MENU_ATM_MICRO_ATM = "Micro ATM";
	public static final String MENU_ATM_PAYMENT_SOUNDBOX = "Payment Soundbox";
	public static final String MENU_ATM_CRM = "White Lable ATM/CRM";

	/***
	 * 
	 * Sub-Menu of Terminal
	 * 
	 */

	public static final String MENU_TERMINAL_MPOS = "MPOS Terminal";
	public static final String MENU_TERMINAL_ANDROID_POS = "Android POS";

	/***
	 * 
	 * Sub-Menu of Other Services
	 * 
	 */

	public static final String MENU_OTHER_RESELLER_PROGRAM = "Reseller Program";
	public static final String MENU_OTHER_PAYMENT_GATEWAY = "Payment Gateway";
	public static final String MENU_OTHER_Payment_Soundbox = "Payment Soundbox";
	public static final String MENU_OTHER_Sarkari_Yojna = "Sarkari Yojna";
	public static final String MENU_OTHER_Educational = "Educational";
	public static final String MENU_OTHER_Legal_Services = "Legal Services";
	public static final String MENU_OTHER_DSC = "DSC(Digital Signature)";
	public static final String MENU_OTHER_Agri_Rental = "Agri Rental";
	public static final String MENU_OTHER_Electronic_Hardware = "Electronic Hardware";

}
