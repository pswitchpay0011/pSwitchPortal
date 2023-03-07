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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_description", length = 5000)
	@Size(max = 5000)
	private String productDescription;

	@Column(name = "is_active")
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

	@Column(name = "amount")
	private Long amount;

	@Column(name = "MRP")
	private Long MRP;

	@Column(name = "commission_file")
	private String commissionFile;

	@Column(name = "brochure_file")
	private String brochureFile;

	@Column(name = "product_image_file")
	private String productImageFile;

	@CreationTimestamp
	@Column(name = "created_ts", nullable = false)
	private LocalDateTime createdTs;

	@UpdateTimestamp
	@Column(name = "last_updated_ts", nullable = false)
	private LocalDateTime lastUpdatedTs;

	@ManyToOne
	@JoinColumn(name = "product_type_id")
	private ProductType productType;

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getProductImageFile() {
		return productImageFile;
	}

	public void setProductImageFile(String productImageFile) {
		this.productImageFile = productImageFile;
	}

	public String getCommissionFile() {
		return commissionFile;
	}

	public void setCommissionFile(String commissionFile) {
		this.commissionFile = commissionFile;
	}

	public String getBrochureFile() {
		return brochureFile;
	}

	public void setBrochureFile(String brochureFile) {
		this.brochureFile = brochureFile;
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

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getMRP() {
		if (MRP == null)
			MRP = 0l;
		return MRP;
	}

	public void setMRP(Long mRP) {
		MRP = mRP;
	}

}
