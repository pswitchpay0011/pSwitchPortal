package net.in.pSwitch.dto;

import javax.validation.constraints.NotNull;

public class UserProfilePasswordDTO {

	private String userId;

	@NotNull(message = "Password Number cannot be blank")
	private String newPassword;

	@NotNull(message = "confirmPassword cannot be blank")
	private String confirmPassword;

	@NotNull(message = "OTP cannot be blank")
	private Long pwd_otp;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Long getPwd_otp() {
		return pwd_otp;
	}

	public void setPwd_otp(Long pwd_otp) {
		this.pwd_otp = pwd_otp;
	}

}
