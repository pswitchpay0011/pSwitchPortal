package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class BankData {
    @JsonProperty("nameAtBank")
    private String nameAtBank;
    @JsonProperty("refId")
    private String refId;
    @JsonProperty("bankName")
    private String bankName;
    @JsonProperty("utr")
    private String utr;
    @JsonProperty("city")
    private String city;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("micr")
    private Integer micr;
}
