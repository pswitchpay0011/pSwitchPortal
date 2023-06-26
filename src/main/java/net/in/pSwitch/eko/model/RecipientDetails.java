package net.in.pSwitch.eko.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecipientDetails {
    @JsonProperty("user_code")
    private String userCode;
    @JsonProperty("name")
    private String name;
    @JsonProperty("recipient_id_type")
    private String recipientIdType;
    @JsonProperty("ifsc")
    private String ifsc;
    @JsonProperty("is_verified")
    private String isVerified;
    @JsonProperty("account")
    private String account;
    @JsonProperty("pipes")
    private Pipes pipes;
    @JsonProperty("recipient_id")
    private Integer recipientId;
}
