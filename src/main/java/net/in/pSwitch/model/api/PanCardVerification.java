package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PanCardVerification {
    @JsonProperty("pan")
    private String pan;
    @JsonProperty("type")
    private String type;
    @JsonProperty("reference_id")
    private Integer referenceId;
    @JsonProperty("name_provided")
    private String nameProvided;
    @JsonProperty("registered_name")
    private String registeredName;
    @JsonProperty("valid")
    private Boolean valid;
    @JsonProperty("message")
    private String message;
    @JsonProperty("name_match_score")
    private String nameMatchScore;
    @JsonProperty("name_match_result")
    private String nameMatchResult;
    @JsonProperty("aadhaar_seeding_status")
    private String aadhaarSeedingStatus;
    @JsonProperty("last_updated_at")
    private String lastUpdatedAt;
    @JsonProperty("name_pan_card")
    private String namePanCard;
    @JsonProperty("pan_status")
    private String panStatus;
    @JsonProperty("aadhaar_seeding_status_desc")
    private String aadhaarSeedingStatusDesc;
    @JsonProperty("father_name")
    private String fatherName;
}
