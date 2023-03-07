package net.in.pSwitch.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "UserWallet")
public class UserWallet implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "opening_amount", nullable = false)
	private Long openingAmount = 0l;

	@Column(name = "closing_amount", nullable = false)
	private Long closingAmount = 0l;

	@Column(name = "current_amount", nullable = false)
	private Long currentAmount = 0l;

	@OneToOne
	@JoinColumn(name = "userId")
	private UserInfo user;

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

	public Long getOpeningAmount() {
		return openingAmount;
	}

	public void setOpeningAmount(Long openingAmount) {
		this.openingAmount = openingAmount;
	}

	public Long getClosingAmount() {
		return closingAmount;
	}

	public void setClosingAmount(Long closingAmount) {
		this.closingAmount = closingAmount;
	}

	public Long getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(Long currentAmount) {
		this.currentAmount = currentAmount;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
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
