package net.in.pSwitch.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "BankList")
public class BankList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "bank_name", nullable = false)
	private String bankName;

	@Column(name = "bank_code")
	private String bankCode;

	@Column(name = "status")
	private String status;

}
