package net.in.pSwitch.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import net.in.pSwitch.model.constant.TransactionType;

public class FundRequestDTO {

	private String fundRequestId;

	// @NotNull(message = "DMT AND Bill Payment cannot be blank")
	private Long dmtAndBillPayment;

	// @NotNull(message = "Pancard Payment cannot be blank")
	private Long pancard;

	// @NotNull(message = "Pancard No. cannot be blank")
	private String pancardNo;

	// @NotNull(message = "E-Policy cannot be blank")
	private Long ePolicy;

	// @NotNull(message = "GST cannot be blank")
	private Long gst;

	// @NotNull(message = "Nepal Money Transfer cannot be blank")
	private Long nepalMoneyTransfer;

	@NotNull(message = "Total Amount cannot be blank")
	private Long totalAmount;

	// @NotNull(message = "Transaction Type cannot be blank")
	private TransactionType transactionType;

	// @NotNull(message = "Bank Details cannot be blank")
	private String bankDetailsId;

	// @NotNull(message = "Payment Date cannot be blank")
	private String paymentDate;

	// @NotNull(message = "Recipt No cannot be blank")
	private String reciptNo;

	// @NotNull(message = "Recipt Image cannot be blank")
	private MultipartFile reciptImage;
	private String reciptImageId;

	public String getFundRequestId() {
		return fundRequestId;
	}

	public void setFundRequestId(String fundRequestId) {
		this.fundRequestId = fundRequestId;
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

	public String getBankDetailsId() {
		return bankDetailsId;
	}

	public void setBankDetailsId(String bankDetailsId) {
		this.bankDetailsId = bankDetailsId;
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

	public MultipartFile getReciptImage() {
		return reciptImage;
	}

	public void setReciptImage(MultipartFile reciptImage) {
		this.reciptImage = reciptImage;
	}

	public String getReciptImageId() {
		return reciptImageId;
	}

	public void setReciptImageId(String reciptImageId) {
		this.reciptImageId = reciptImageId;
	}

}
