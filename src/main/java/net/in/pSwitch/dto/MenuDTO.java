package net.in.pSwitch.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import net.in.pSwitch.utility.StringLiteral;

public class MenuDTO {

	private String menuId;

	@NotNull(message = "Menu Name cannot be blank")
	private String menuName;

	@NotNull(message = "Menu Type cannot be blank")
	private String menuType;

	@NotNull(message = "Page URL cannot be blank")
	private String pageUrl;

	private Long parentMenuId;

	private Long isSearchable;

	private List<String> userAccess;

	private String menuIcon = "icon-home4";

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public Long getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
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

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public List getUserAccess() {
		return userAccess;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public void setUserAccess(List<String> userAccess) {
		this.userAccess = userAccess;
	}

	public Long getIsSearchable() {
		return isSearchable;
	}

	public void setIsSearchable(Long isSearchable) {
		this.isSearchable = isSearchable;
	}

}
