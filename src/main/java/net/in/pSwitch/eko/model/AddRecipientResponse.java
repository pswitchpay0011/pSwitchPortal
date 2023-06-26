package net.in.pSwitch.eko.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddRecipientResponse {
    @JsonProperty("user_code")
    private String userCode;
    @JsonProperty("initiator_id")
    private String initiatorId;
    @JsonProperty("recipient_mobile")
    private String recipientMobile;
    @JsonProperty("recipient_id_type")
    private String recipientIdType;
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("pipes")
    private Pipes pipes;
    @JsonProperty("recipient_id")
    private Integer recipientId;
}
