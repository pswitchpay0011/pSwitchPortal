package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class BankVerificationResponse {

    @JsonProperty("status")
    private String status;
    @JsonProperty("subCode")
    private String subCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("accountStatus")
    private String accountStatus;
    @JsonProperty("accountStatusCode")
    private String accountStatusCode;
    @JsonProperty("data")
    private BankData data;
}
