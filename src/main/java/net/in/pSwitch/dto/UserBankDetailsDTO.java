package net.in.pSwitch.dto;

import javax.validation.constraints.NotNull;

public class UserBankDetailsDTO {

	private String userId;

	@NotNull(message = "Account Holder Name cannot be blank")
	private String accountHolderName;

	@NotNull(message = "Account Number cannot be blank")
	private String AccountNumber;

	@NotNull(message = "IFSC cannot be blank")
	private String IFSC_CODE;

	@NotNull(message = "Bank Name cannot be blank")
	private String BANK_NAME;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getAccountNumber() {
		return AccountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}

	public String getIFSC_CODE() {
		return IFSC_CODE;
	}

	public void setIFSC_CODE(String iFSC_CODE) {
		IFSC_CODE = iFSC_CODE;
	}

	public String getBANK_NAME() {
		return BANK_NAME;
	}

	public void setBANK_NAME(String bANK_NAME) {
		BANK_NAME = bANK_NAME;
	}

}
