package net.in.pSwitch.eko.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Refund {

    @JsonProperty("refund_tid")
    private String refundTid;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("tds")
    private String tds;
    @JsonProperty("balance")
    private String balance;
    @JsonProperty("fee")
    private String fee;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("commission_reverse")
    private String commissionReverse;
    @JsonProperty("tid")
    private String tid;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("refunded_amount")
    private String refundedAmount;
}
