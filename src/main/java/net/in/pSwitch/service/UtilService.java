package net.in.pSwitch.service;

import java.util.List;

import net.in.pSwitch.model.Product;
import net.in.pSwitch.model.UserInfo;

public interface UtilService {

	public void shareProduct(Product product, List<String> email); 
	
	public void sendVerificationMail(UserInfo userInfo);

	public void sendOTPMail(UserInfo userInfo);
	public void sendKYCCompleteMail(UserInfo userInfo);
	public void sendLoginDetailMail(UserInfo userInfo);

	public String generateOTP();

	void sendVerificationMobileAndEmail(UserInfo userInfo);
	
	public void sendResetMail(UserInfo userInfo, String token);

	public void sendAccountCreatedMail(UserInfo userInfo);

	public String validatePasswordResetToken(String token);

	public String encodedData(String data);
	public String decodedData(String data);

}
