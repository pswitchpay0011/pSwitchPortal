package net.in.pSwitch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "service")
public class Service implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "service_name")
	private String serviceName;

	@Column(name = "service_charge")
	private double serviceCharge;

	@Column(name = "is_active")
	@Enumerated(EnumType.STRING)
	private Status status;

	@CreationTimestamp
	@Column(name = "created_ts", nullable = false)
	private LocalDateTime createdTs;

	@UpdateTimestamp
	@Column(name = "last_updated_ts", nullable = false)
	private LocalDateTime lastUpdatedTs;

	@Column(name = "admin_menu")
	private long adminMenu = 0;

	@Column(name = "retailer_menu")
	private long retailerMenu = 0;

	@Column(name = "distributor_menu")
	private long distributorMenu = 0;

	@Column(name = "super_distributor_menu")
	private long superDistributorMenu = 0;

	@Column(name = "business_associate_menu")
	private long businessAssociateMenu = 0;

}
