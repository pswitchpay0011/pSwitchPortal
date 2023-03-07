package net.in.pSwitch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CompanyBankDetails")
public class CompanyBankDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "account_holder_name", nullable = false)
	private String accountHolderName;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "IFSC_CODE")
	private String IFSC_CODE;

	@Column(name = "BANK_NAME")
	private String BANK_NAME;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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
