package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "pan_card_details")
public class PanCardVerification {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @JsonProperty("pan")
    @Column(name = "pan")
    private String pan;

    @JsonProperty("type")
    private String type;

    @JsonProperty("reference_id")
    @Column(name = "reference_id")
    private Integer referenceId;

    @JsonProperty("name_provided")
    @Column(name = "name_provided")
    private String nameProvided;

    @JsonProperty("registered_name")
    @Column(name = "registered_name")
    private String registeredName;

    @JsonProperty("valid")
    private Boolean valid;

    @JsonProperty("message")
    private String message;

    @JsonProperty("name_match_score")
    @Column(name = "name_match_score")
    private String nameMatchScore;

    @JsonProperty("name_match_result")
    @Column(name = "name_match_result")
    private String nameMatchResult;

    @JsonProperty("aadhaar_seeding_status")
    @Column(name = "aadhaar_seeding_status")
    private String aadhaarSeedingStatus;

    @JsonProperty("last_updated_at")
    @Column(name = "last_updated_at")
    private String lastUpdatedAt;

    @JsonProperty("name_pan_card")
    @Column(name = "name_pan_card")
    private String namePanCard;

    @JsonProperty("pan_status")
    @Column(name = "pan_status")
    private String panStatus;

    @JsonProperty("aadhaar_seeding_status_desc")
    @Column(name = "aadhaar_seeding_status_desc")
    private String aadhaarSeedingStatusDesc;

    @JsonProperty("father_name")
    @Column(name = "father_name")
    private String fatherName;

}
