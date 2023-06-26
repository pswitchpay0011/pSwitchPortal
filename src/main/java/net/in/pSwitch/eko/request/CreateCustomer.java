package net.in.pSwitch.eko.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateCustomer {
    @JsonProperty("initiator_id")
    private String initiatorId;
    private String name;
    @JsonProperty("user_code")
    private String userCode;
    private String dob;
    @JsonProperty("residence_address")
    private String residenceAddress;
    private String pipe;
}
