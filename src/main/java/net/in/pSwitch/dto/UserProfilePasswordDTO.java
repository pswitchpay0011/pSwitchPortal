package net.in.pSwitch.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserProfilePasswordDTO {

	@NotNull(message = "New Password cannot be blank")
	private String newPassword;

	@NotNull(message = "Old Password cannot be blank")
	private String oldPassword;

	@NotNull(message = "confirmPassword cannot be blank")
	private String confirmPassword;

}
