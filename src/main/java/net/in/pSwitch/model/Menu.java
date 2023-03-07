package net.in.pSwitch.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "menu")
public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id")
	private long menuId;

	@Column(name = "menu_name")
	private String menuName;

	@Column(name = "menu_type")
	private String menuType = "page";

	@Column(name = "page_url")
	private String pageUrl;

	@Column(name = "menu_icon")
	private String menuIcon;

	@Column(name = "parent_menu_id")
	private Long parentMenuId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_menu_id")
	private List<Menu> menus;

	@Column(name = "admin_menu")
	private long adminMenu = 0;

	@Column(name = "retailer_menu")
	private long retailerMenu = 0;

	@Column(name = "distributor_menu")
	private long distributorMenu = 0;

	@Column(name = "super_distributor_menu")
	private long superDistributorMenu = 0;

	@Column(name = "business_associate_menu")
	private long businessAssociateMenu = 0;

	@Column(name = "is_active")
	private long isActive = 0;

	@Column(name = "is_searchable")
	private long isSearchable = 0;

	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@Column(name = "menuOrder")
	private long menuOrder=0;

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

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

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public long getAdminMenu() {
		return adminMenu;
	}

	public void setAdminMenu(long adminMenu) {
		this.adminMenu = adminMenu;
	}

	public long getRetailerMenu() {
		return retailerMenu;
	}

	public void setRetailerMenu(long retailerMenu) {
		this.retailerMenu = retailerMenu;
	}

	public long getDistributorMenu() {
		return distributorMenu;
	}

	public void setDistributorMenu(long distributorMenu) {
		this.distributorMenu = distributorMenu;
	}

	public long getSuperDistributorMenu() {
		return superDistributorMenu;
	}

	public void setSuperDistributorMenu(long superDistributorMenu) {
		this.superDistributorMenu = superDistributorMenu;
	}

	public long getIsActive() {
		return isActive;
	}

	public void setIsActive(long isActive) {
		this.isActive = isActive;
	}

	public long getBusinessAssociateMenu() {
		return businessAssociateMenu;
	}

	public void setBusinessAssociateMenu(long businessAssociateMenu) {
		this.businessAssociateMenu = businessAssociateMenu;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public Long getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public long getIsSearchable() {
		return isSearchable;
	}

	public void setIsSearchable(long isSearchable) {
		this.isSearchable = isSearchable;
	}

	public void setShowMenuAll(long show) {
		this.adminMenu = show;
		this.retailerMenu = show;
		this.distributorMenu = show;
		this.superDistributorMenu = show;
		this.businessAssociateMenu = show;
	}

	public long getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(long menuOrder) {
		this.menuOrder = menuOrder;
	}
}
