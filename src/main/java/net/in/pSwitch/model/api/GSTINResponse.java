package net.in.pSwitch.model.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GSTINResponse {

	@JsonProperty("reference_id")
	private Integer referenceId;
	@JsonProperty("GSTIN")
	private String gstin;
	@JsonProperty("legal_name_of_business")
	private String legalNameOfBusiness;
	@JsonProperty("center_jurisdiction")
	private String centerJurisdiction;
	@JsonProperty("state_jurisdiction")
	private String stateJurisdiction;
	@JsonProperty("date_of_registration")
	private String dateOfRegistration;
	@JsonProperty("constitution_of_business")
	private String constitutionOfBusiness;
	@JsonProperty("taxpayer_type")
	private String taxpayerType;
	@JsonProperty("gst_in_status")
	private String gstInStatus;
	@JsonProperty("last_update_date")
	private String lastUpdateDate;
	@JsonProperty("nature_of_business_activities")
	private List<String> natureOfBusinessActivities;
	@JsonProperty("principal_place_address")
	private String principalPlaceAddress;
	@JsonProperty("valid")
	private Boolean valid;
	@JsonProperty("message")
	private String message;

}
