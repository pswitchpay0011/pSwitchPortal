package net.in.pSwitch.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "UserBankDetails")
public class UserBankDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "account_holder_name", nullable = false)
	private String accountHolderName;

	@Column(name = "account_number")
	private String AccountNumber;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "account_type")
	private String accountType;

	@OneToOne//(mappedBy = "bankDetails")
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private UserInfo userInfo;

}
