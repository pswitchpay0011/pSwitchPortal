package net.in.pSwitch.model.wallet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.in.pSwitch.model.Status;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wallet_withdrawal")
public class WalletWithdrawal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "transaction_code")
	private String transaction_code;

	@Column(name = "user_id")
	private Integer user_id;

	@Column(name = "amount")
	private double amount;

	@Column(name = "charged")
	private double charged;

	@Column(name = "to_receive")
	private String to_receive;

	@Column(name = "gateway_id")
	private Integer gatewayId;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

	@Column(name = "createdBy", nullable = false)
	private Integer createdBy;
	@Column(name = "updatedBy", nullable = false)
	private Integer updatedBy;

	@CreationTimestamp
	@Column(name = "createdOn", nullable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	@Column(name = "updatedOn", nullable = false)
	private LocalDateTime updatedOn;

}
