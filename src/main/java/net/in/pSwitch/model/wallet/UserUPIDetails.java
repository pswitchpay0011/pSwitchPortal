package net.in.pSwitch.model.wallet;

import lombok.Getter;
import lombok.Setter;
import net.in.pSwitch.model.UserInfo;

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

@Getter
@Setter
@Entity
@Table(name = "UserUPIDetails")
public class UserUPIDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "account_holder_name", nullable = false)
	private String accountHolderName;

	@Column(name = "upi_id")
	private String upiId;

	@OneToOne // (mappedBy = "bankDetails")
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private UserInfo userInfo;
	
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
