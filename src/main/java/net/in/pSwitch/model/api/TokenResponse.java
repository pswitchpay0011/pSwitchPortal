package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class TokenResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("subCode")
    private String subCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private TokenData data;
}
