package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AadhaarOTPResponse {
    @JsonProperty("ref_id")
    private String refId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("type")
    private String type;
    @JsonProperty("code")
    private String code;

}
