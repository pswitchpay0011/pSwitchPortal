package net.in.pSwitch.eko.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity(name = "beneficiary" )
public class Recipient {

    @Id
    @JsonProperty("recipient_id")
    private Integer recipientId;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pipes")
    @JsonProperty("pipes")
    private Pipes pipes;

    @JsonProperty("is_rblbc_recipient")
    private Integer isRblbcRecipient;
}
