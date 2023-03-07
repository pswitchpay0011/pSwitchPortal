package net.in.pSwitch.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "Transaction")
public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5861717593524362630L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "amount", nullable = false)
	private Long amount = 0l;

	@Column(name = "transaction_type", nullable = false)
	private TransactionType transactionType;

	@ManyToOne
	@JoinColumn(name = "to_user")
	private UserInfo toUser;

	@ManyToOne
	@JoinColumn(name = "from_user")
	private UserInfo fromUser;

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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public UserInfo getToUser() {
		return toUser;
	}

	public void setToUser(UserInfo toUser) {
		this.toUser = toUser;
	}

	public UserInfo getFromUser() {
		return fromUser;
	}

	public void setFromUser(UserInfo fromUser) {
		this.fromUser = fromUser;
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
