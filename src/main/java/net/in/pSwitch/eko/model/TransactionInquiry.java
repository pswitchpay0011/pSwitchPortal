package net.in.pSwitch.eko.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionInquiry {
    @JsonProperty("tx_status")
    private String txStatus;
    @JsonProperty("tds")
    private String tds;
    @JsonProperty("txstatus_desc")
    private String txstatusDesc;
    @JsonProperty("fee")
    private String fee;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("txn_wallet")
    private String txnWallet;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("tid")
    private String tid;
    @JsonProperty("allow_retry")
    private String allowRetry;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("commission")
    private String commission;
    @JsonProperty("pipe")
    private Integer pipe;
    @JsonProperty("bank_ref_num")
    private String bankRefNum;
    @JsonProperty("recipient_id")
    private Integer recipientId;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("gst_benefit")
    private String gstBenefit;
    @JsonProperty("tx_desc")
    private String txDesc;
    @JsonProperty("client_ref_id")
    private String clientRefId;
    @JsonProperty("service_tax")
    private String serviceTax;
    @JsonProperty("customer_id")
    private String customerId;
}
