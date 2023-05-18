package net.in.pSwitch.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "gstin")
public class GSTINData {

	@Id
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "reference_id")
	private Integer referenceId;
	@Column(name = "gstin")
	private String gstin;
	@Column(name = "legal_name_of_business")
	private String legalNameOfBusiness;
	@Column(name = "center_jurisdiction")
	private String centerJurisdiction;
	@Column(name = "state_jurisdiction")
	private String stateJurisdiction;
	@Column(name = "date_of_registration")
	private String dateOfRegistration;
	@Column(name = "constitution_of_business")
	private String constitutionOfBusiness;
	@Column(name = "taxpayer_type")
	private String taxpayerType;
	@Column(name = "gst_in_status")
	private String gstInStatus;
	@Column(name = "last_update_date")
	private String lastUpdateDate;
	@Column(name = "nature_of_business_activities")
	private String natureOfBusinessActivities;
	@Column(name = "principal_place_address")
	private String principalPlaceAddress;
	@Column(name = "valid")
	private Boolean valid;
	@Column(name = "message")
	private String message;



}
