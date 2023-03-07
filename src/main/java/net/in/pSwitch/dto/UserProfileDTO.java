package net.in.pSwitch.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class UserProfileDTO {

	private String userId;

	@NotNull(message = "First Name cannot be blank")
	@Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
	private String firstName;
	private String lastName;
	private String password;
	@NotNull
	@Size(min = 2, max = 50)
	private String username;

	@NotNull(message = "Contact Number cannot be blank")
	private String mobileNumber;

	@NotNull(message = "Address cannot be blank")
	private String address;

	@NotNull(message = "City cannot be blank")
	private String city;

	@NotNull(message = "State cannot be blank")
	private String state;

	@NotNull(message = "Zipcode cannot be blank")
	private String zipcode;

	@NotNull(message = "Country cannot be blank")
	private String country;

	private String gstNo;
	private String companyName;

	private MultipartFile imgProfile;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public MultipartFile getImgProfile() {
		return imgProfile;
	}

	public void setImgProfile(MultipartFile imgProfile) {
		this.imgProfile = imgProfile;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
