package net.in.pSwitch.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class UserProfileDocumentDTO {

	private String userId;

	@NotNull(message = "Pancard Number cannot be blank")
	private String pancardNumber;

	@NotNull(message = "Aadhaar Number cannot be blank")
	private String aadhaarNumber;

	@NotNull(message = "Pancard Image cannot be blank")
	private MultipartFile imgPancard;

	@NotNull(message = "Aadhaar Image  cannot be blank")
	private MultipartFile imgAadhaarCard;

	public String getPancardNumber() {
		return pancardNumber;
	}

	public void setPancardNumber(String pancardNumber) {
		this.pancardNumber = pancardNumber;
	}

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public MultipartFile getImgPancard() {
		return imgPancard;
	}

	public void setImgPancard(MultipartFile imgPancard) {
		this.imgPancard = imgPancard;
	}

	public MultipartFile getImgAadhaarCard() {
		return imgAadhaarCard;
	}

	public void setImgAadhaarCard(MultipartFile imgAadhaarCard) {
		this.imgAadhaarCard = imgAadhaarCard;
	}

}
