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

import lombok.Data;
import net.in.pSwitch.model.constant.TransactionType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "transactions")
public class Transactions implements Serializable {
	private static final long serialVersionUID = -5861717593524362630L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "amount", nullable = false)
	private Double amount;

	@Column(name = "transaction_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	@Column(name = "user_id", nullable = false)
	private Integer userid;

	@UpdateTimestamp
	@Column(name = "modifiedOn", nullable = false)
	private LocalDateTime modifiedOn;

	@CreationTimestamp
	@Column(name = "createdOn", nullable = false)
	private LocalDateTime createdOn;

}
