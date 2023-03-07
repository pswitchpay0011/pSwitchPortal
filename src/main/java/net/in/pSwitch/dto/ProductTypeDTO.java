package net.in.pSwitch.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import net.in.pSwitch.utility.StringLiteral;

public class ProductTypeDTO {

	private String id;
	private String menuId;
	private String productTypeMenuId;

	@NotNull(message = "Product Type Name cannot be blank")
	private String name;

	private List<String> userAccess;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getUserAccess() {
		return userAccess;
	}

	public void setUserAccess(List<String> userAccess) {
		this.userAccess = userAccess;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
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

	public String getProductTypeMenuId() {
		return productTypeMenuId;
	}

	public void setProductTypeMenuId(String productTypeMenuId) {
		this.productTypeMenuId = productTypeMenuId;
	}

}
