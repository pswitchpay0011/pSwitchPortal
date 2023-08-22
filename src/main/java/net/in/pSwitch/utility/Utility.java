package net.in.pSwitch.utility;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

//	private static SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

	public static String getFormatedDate(LocalDateTime date) {
		String strDate = formatter.format(date);
		return strDate;
	}

	public static String getAssociateRole(String parentRole) {
		switch (parentRole) {
		case StringLiteral.ROLE_CODE_DISTRIBUTOR:
			return StringLiteral.ROLE_CODE_RETAILER;

		case StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR:
			return StringLiteral.ROLE_CODE_DISTRIBUTOR;

		case StringLiteral.ROLE_CODE_BUSINESS_ASSOCIATE:
			return StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR;

		case StringLiteral.ROLE_CODE_MANAGER_BUSINESS_ASSOCIATE:
			return StringLiteral.ROLE_CODE_BUSINESS_ASSOCIATE;

		case StringLiteral.ROLE_CODE_MANAGER_FINANCE:
			return StringLiteral.ROLE_CODE_FINANCE;
		}

		return StringLiteral.ROLE_CODE_RETAILER;
	}

	public static String getParentRole(String childRole) {
		switch (childRole) {
		case StringLiteral.ROLE_CODE_DISTRIBUTOR:
			return StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR;

		case StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR:
			return StringLiteral.ROLE_CODE_BUSINESS_ASSOCIATE;

		case StringLiteral.ROLE_CODE_BUSINESS_ASSOCIATE:
			return StringLiteral.ROLE_CODE_MANAGER_BUSINESS_ASSOCIATE;

		case StringLiteral.ROLE_CODE_FINANCE:
			return StringLiteral.ROLE_CODE_MANAGER_FINANCE;

		case StringLiteral.ROLE_CODE_RETAILER:
			return StringLiteral.ROLE_CODE_DISTRIBUTOR;
		}

		return StringLiteral.ROLE_CODE_DISTRIBUTOR;
	}

	public static String getPSwitchUserId(String state, String role, long userId) {
		return "PS_" + state.substring(0, 3).toUpperCase() + "_" + role + "_" + userId;
	}

	// Function to validate the PAN Card number.
	public static boolean isValidPanCardNo(String panCardNo) {

		// Regex to check valid PAN Card number.
		String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";

		// Compile the ReGex
		Pattern p = Pattern.compile(regex);

		// If the PAN Card number
		// is empty return false
		if (panCardNo == null) {
			return false;
		}

		// Pattern class contains matcher() method
		// to find matching between given
		// PAN Card number using regular expression.
		Matcher m = p.matcher(panCardNo);

		// Return if the PAN Card number
		// matched the ReGex
		return m.matches();
	}

	// Function to validate Aadhar number.
	public static boolean isValidAadharNumber(String aadhaarNumber) {
		// Regex to check valid Aadhar number.
		String regex = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";

		// Compile the ReGex
		Pattern p = Pattern.compile(regex);

		// If the string is empty
		// return false
		if (aadhaarNumber == null) {
			return false;
		}

		// Pattern class contains matcher() method
		// to find matching between given string
		// and regular expression.
		Matcher m = p.matcher(aadhaarNumber);

		// Return if the string
		// matched the ReGex
		return m.matches();
	}

	public static boolean isValidMobileNumber(String mobileNumber) {
		// The given argument to compile() method
		// is regular expression. With the help of
		// regular expression we can validate mobile
		// number.
		// 1) Begins with 0 or 91
		// 2) Then contains 7 or 8 or 9.
		// 3) Then contains 9 digits
		Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

		// Pattern class contains matcher() method
		// to find matching between given number
		// and regular expression
		Matcher m = p.matcher(mobileNumber);
		return (m.find() && m.group().equals(mobileNumber));
	}

//	public static <T > void copyAllFields(T to, T from) {
//		Class<T> clazz = (Class<T>) from.getClass();
//		// OR:
//		// Class<T> clazz = (Class<T>) to.getClass();
//		List<Field> fields = getAllModelFields(clazz);
//
//		if (fields != null) {
//			for (Field field : fields) {
//				try {
//					field.setAccessible(true);
//					field.set(to,field.get(from));
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	public static List<Field> getAllModelFields(Class aClass) {
//		List<Field> fields = new ArrayList<>();
//		do {
//			Collections.addAll(fields, aClass.getDeclaredFields());
//			aClass = aClass.getSuperclass();
//		} while (aClass != null);
//		return fields;
//	}
}
