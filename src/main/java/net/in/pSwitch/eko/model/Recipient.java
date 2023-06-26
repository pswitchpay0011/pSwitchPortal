package net.in.pSwitch.eko.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Recipient {
    @JsonProperty("channel_absolute")
    private Integer channelAbsolute;

    @JsonProperty("available_channel")
    private Integer availableChannel;

    @JsonProperty("account_type")
    private String accountType;

    @JsonProperty("ifsc_status")
    private Integer ifscStatus;

    @JsonProperty("is_self_account")
    private String isSelfAccount;

    @JsonProperty("channel")
    private Integer channel;

    @JsonProperty("is_imps_scheduled")
    private Integer isImpsScheduled;

    @JsonProperty("recipient_id_type")
    private String recipientIdType;

    @JsonProperty("imps_inactive_reason")
    private String impsInactiveReason;

    @JsonProperty("allowed_channel")
    private Integer allowedChannel;

    @JsonProperty("is_verified")
    private Integer isVerified;

    @JsonProperty("bank")
    private String bank;

    @JsonProperty("is_otp_required")
    private String isOtpRequired;

    @JsonProperty("recipient_mobile")
    private String recipientMobile;

    @JsonProperty("recipient_name")
    private String recipientName;

    @JsonProperty("ifsc")
    private String ifsc;

    @JsonProperty("account")
    private String account;

    @JsonProperty("pipes")
    private Pipes pipes;

    @JsonProperty("recipient_id")
    private Integer recipientId;

    @JsonProperty("is_rblbc_recipient")
    private Integer isRblbcRecipient;
}
