package net.in.pSwitch.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "banner")
public class Banner implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "banner_name")
	private String bannerName;

	@Column(name = "banner_description", length = 500)
	private String bannerDescription;

	@Column(name = "is_active")
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

	@Column(name = "banner_image")
	private String bannerImage;

	@CreationTimestamp
	@Column(name = "created_ts", nullable = false)
	private LocalDateTime createdTs;

	@UpdateTimestamp
	@Column(name = "last_updated_ts", nullable = false)
	private LocalDateTime lastUpdatedTs;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getBannerImage() {
		return bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}

	public LocalDateTime getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(LocalDateTime createdTs) {
		this.createdTs = createdTs;
	}

	public LocalDateTime getLastUpdatedTs() {
		return lastUpdatedTs;
	}

	public void setLastUpdatedTs(LocalDateTime lastUpdatedTs) {
		this.lastUpdatedTs = lastUpdatedTs;
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

	public long getBusinessAssociateMenu() {
		return businessAssociateMenu;
	}

	public void setBusinessAssociateMenu(long businessAssociateMenu) {
		this.businessAssociateMenu = businessAssociateMenu;
	}

}
