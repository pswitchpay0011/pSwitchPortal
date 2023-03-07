package net.in.pSwitch.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import net.in.pSwitch.utility.StringLiteral;

public class BannerDTO {

	private String id;

	@NotNull(message = "Banner Name cannot be blank")
	private String bannerName;

	@NotNull(message = "Banner Description  cannot be blank")
	private String bannerDescription;

	@NotNull(message = "banner Image File cannot be blank")
	private MultipartFile bannerImage;

	private List<String> userAccess;

	public Long getAdminMenu() {
		if (userAccess != null && userAccess.contains(StringLiteral.ROLE_CODE_ADMIN)) {
			return 1l;
		}
		return 0l;
	}

	public Long getRetailerMenu() {
		if (userAccess != null && userAccess.contains(StringLiteral.ROLE_CODE_RETAILER)) {
			return 1l;
		}
		return 0l;
	}

	public Long getDistributorMenu() {
		if (userAccess != null && userAccess.contains(StringLiteral.ROLE_CODE_DISTRIBUTOR)) {
			return 1l;
		}
		return 0l;
	}

	public Long getSuperDistributorMenu() {
		if (userAccess != null && userAccess.contains(StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR)) {
			return 1l;
		}
		return 0l;
	}

	public Long getBusinessAssociateMenu() {
		if (userAccess != null && userAccess.contains(StringLiteral.ROLE_CODE_BUSINESS_ASSOCIATE)) {
			return 1l;
		}
		return 0l;
	}

	public List getUserAccess() {
		return userAccess;
	}

	public void setUserAccess(List<String> userAccess) {
		this.userAccess = userAccess;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBannerName() {
		return bannerName;
	}

	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	public String getBannerDescription() {
		return bannerDescription;
	}

	public void setBannerDescription(String bannerDescription) {
		this.bannerDescription = bannerDescription;
	}

	public MultipartFile getBannerImage() {
		return bannerImage;
	}

	public void setBannerImage(MultipartFile bannerImage) {
		this.bannerImage = bannerImage;
	}

}
