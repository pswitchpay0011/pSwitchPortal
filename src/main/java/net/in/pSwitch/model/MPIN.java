package net.in.pSwitch.model;

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
@Table(name = "mpin")
public class MPIN {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "pin")
	private String pin;

	@Column(name = "resetOTP")
	private String resetOTP;

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

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getResetOTP() {
		return resetOTP;
	}

	public void setResetOTP(String resetOTP) {
		this.resetOTP = resetOTP;
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
