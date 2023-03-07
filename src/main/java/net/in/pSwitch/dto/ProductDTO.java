package net.in.pSwitch.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import net.in.pSwitch.utility.StringLiteral;

public class ProductDTO {

	private String id;

	@NotNull(message = "Product Name cannot be blank")
	private String productName;

	@NotNull(message = "Product Description  cannot be blank")
	private String productDescription;

	@NotNull(message = "Commission cannot be blank")
	private Long amount;

	@NotNull(message = "MRP cannot be blank")
	private Long MRP;

	private String brochureFileId;
	private String productImageFileId;

//	@NotNull(message = "Commission File cannot be blank")
//	private MultipartFile commissionFile;

//	@NotNull(message = "brochure File cannot be blank")
	private MultipartFile brochureFile;

//	@NotNull(message = "Product Image File cannot be blank")
	private MultipartFile productImageFile;

	@NotNull(message = "Product Type cannot be blank")
	private Integer productType;

	private List<String> userAccess;

	public String getBrochureFileId() {
		return brochureFileId;
	}

	public void setBrochureFileId(String brochureFileId) {
		this.brochureFileId = brochureFileId;
	}

	public String getProductImageFileId() {
		return productImageFileId;
	}

	public void setProductImageFileId(String productImageFileId) {
		this.productImageFileId = productImageFileId;
	}

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	/*
	 * public MultipartFile getCommissionFile() { return commissionFile; }
	 * 
	 * public void setCommissionFile(MultipartFile commissionFile) {
	 * this.commissionFile = commissionFile; }
	 */

	public MultipartFile getBrochureFile() {
		return brochureFile;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public void setBrochureFile(MultipartFile brochureFile) {
		this.brochureFile = brochureFile;
	}

	public MultipartFile getProductImageFile() {
		return productImageFile;
	}

	public void setProductImageFile(MultipartFile productImageFile) {
		this.productImageFile = productImageFile;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Long getMRP() {
		return MRP;
	}

	public void setMRP(Long mRP) {
		MRP = mRP;
	}

}
