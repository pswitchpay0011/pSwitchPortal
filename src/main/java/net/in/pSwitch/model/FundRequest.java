package net.in.pSwitch.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.in.pSwitch.model.constant.TransactionType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "fundRequest")
public class FundRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "dmt_and_bill_payment")
	private Long dmtAndBillPayment;

	@Column(name = "panCard")
	private Long pancard;

	@Column(name = "pancardNo")
	private String pancardNo;

	@Column(name = "ePolicy")
	private Long ePolicy;

	@Column(name = "gst")
	private Long gst;

	@Column(name = "nepalMoney")
	private Long nepalMoneyTransfer;

	@Column(name = "totalAmount")
	private Long totalAmount;

	@Column(name = "transactionType")
	private TransactionType transactionType;

	@Column(name = "companyBankDetails")
	private CompanyBankDetails companyBankDetails;

	@Column(name = "paymentDate")
	private String paymentDate;

	@Column(name = "reciptNo")
	private String reciptNo;

	@Column(name = "recipt_image")
	private String reciptImage;

	@Column(name = "requestDate")
	private Long requestDate;

	@ManyToOne
	@JoinColumn(name = "userId")
	private UserInfo user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_user_id")
	protected UserInfo parent;

	@Column(name = "status")
	private Status status;

	@CreationTimestamp
	@Column(name = "created_ts", nullable = false)
	private LocalDateTime createdTs;

	@UpdateTimestamp
	@Column(name = "last_updated_ts", nullable = false)
	private LocalDateTime lastUpdatedTs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getDmtAndBillPayment() {
		return dmtAndBillPayment;
	}

	public void setDmtAndBillPayment(Long dmtAndBillPayment) {
		this.dmtAndBillPayment = dmtAndBillPayment;
	}

	public Long getPancard() {
		return pancard;
	}

	public void setPancard(Long pancard) {
		this.pancard = pancard;
	}

	public String getPancardNo() {
		return pancardNo;
	}

	public void setPancardNo(String pancardNo) {
		this.pancardNo = pancardNo;
	}

	public Long getePolicy() {
		return ePolicy;
	}

	public void setePolicy(Long ePolicy) {
		this.ePolicy = ePolicy;
	}

	public Long getGst() {
		return gst;
	}

	public void setGst(Long gst) {
		this.gst = gst;
	}

	public Long getNepalMoneyTransfer() {
		return nepalMoneyTransfer;
	}

	public void setNepalMoneyTransfer(Long nepalMoneyTransfer) {
		this.nepalMoneyTransfer = nepalMoneyTransfer;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public CompanyBankDetails getCompanyBankDetails() {
		return companyBankDetails;
	}

	public void setCompanyBankDetails(CompanyBankDetails companyBankDetails) {
		this.companyBankDetails = companyBankDetails;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getReciptNo() {
		return reciptNo;
	}

	public void setReciptNo(String reciptNo) {
		this.reciptNo = reciptNo;
	}

	public String getReciptImage() {
		return reciptImage;
	}

	public void setReciptImage(String reciptImage) {
		this.reciptImage = reciptImage;
	}

	public Long getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Long requestDate) {
		this.requestDate = requestDate;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public UserInfo getParent() {
		return parent;
	}

	public void setParent(UserInfo parent) {
		this.parent = parent;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

}
