package net.in.pSwitch.eko.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InitiateTransaction {
    @JsonProperty("tx_status")
    private String txStatus;
    @JsonProperty("debit_user_id")
    private String debitUserId;
    @JsonProperty("tds")
    private String tds;
    @JsonProperty("txstatus_desc")
    private String txstatusDesc;
    @JsonProperty("fee")
    private String fee;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("collectable_amount")
    private String collectableAmount;
    @JsonProperty("txn_wallet")
    private String txnWallet;
    @JsonProperty("utility_acc_no")
    private String utilityAccNo;
    @JsonProperty("sender_name")
    private String senderName;
    @JsonProperty("ekyc_enabled")
    private String ekycEnabled;
    @JsonProperty("remaining_limit_before_pan_required")
    private Integer remainingLimitBeforePanRequired;
    @JsonProperty("tid")
    private String tid;
    @JsonProperty("bank")
    private String bank;
    @JsonProperty("utrnumber")
    private String utrnumber;
    @JsonProperty("balance")
    private String balance;
    @JsonProperty("user_code")
    private String userCode;
    @JsonProperty("totalfee")
    private String totalfee;
    @JsonProperty("next_allowed_limit")
    private String nextAllowedLimit;
    @JsonProperty("is_otp_required")
    private String isOtpRequired;
    @JsonProperty("aadhar")
    private String aadhar;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("commission")
    private String commission;
    @JsonProperty("pipe")
    private Integer pipe;
    @JsonProperty("state")
    private String state;
    @JsonProperty("bank_ref_num")
    private String bankRefNum;
    @JsonProperty("recipient_id")
    private Integer recipientId;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("pan_required")
    private Integer panRequired;
    @JsonProperty("gst_benefit")
    private String gstBenefit;
    @JsonProperty("payment_mode_desc")
    private String paymentModeDesc;
    @JsonProperty("channel_desc")
    private String channelDesc;
    @JsonProperty("client_ref_id")
    private String clientRefId;
    @JsonProperty("service_tax")
    private String serviceTax;
    @JsonProperty("recipient_name")
    private String recipientName;
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("account")
    private String account;
    @JsonProperty("kyc_state")
    private String kycState;
}
