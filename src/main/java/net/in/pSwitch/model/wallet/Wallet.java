package net.in.pSwitch.model.wallet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.in.pSwitch.model.Status;
import net.in.pSwitch.model.constant.WalletType;
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
@Table(name = "wallet")
public class Wallet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "wallet_type")
	@Enumerated(EnumType.STRING)
	private WalletType walletType;

	@Column(name = "user_id")
	private Integer user_id;

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
