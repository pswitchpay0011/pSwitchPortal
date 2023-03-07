package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class AadhaarVerification {
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("ref_id")
    private String refId;
    @JsonProperty("care_of")
    private String careOf;
    @JsonProperty("address")
    private String address;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("email")
    private String email;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("name")
    private String name;
    @JsonProperty("photo_link")
    private String photoLink;
    @JsonProperty("mobile_hash")
    private String mobileHash;
    @JsonProperty("split_address")
    private SplitAddress splitAddress;
    @JsonProperty("year_of_birth")
    private String yearOfBirth;

    @JsonProperty("type")
    private String type;
    @JsonProperty("code")
    private String code;
}
