package net.in.pSwitch.eko.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RecipientData {
    @JsonProperty("pan_required")
    private Integer panRequired;
    @JsonProperty("user_code")
    private String userCode;
    @JsonProperty("recipient_list")
    private List<Recipient> recipientList;
    @JsonProperty("remaining_limit_before_pan_required")
    private Integer remainingLimitBeforePanRequired;
}
