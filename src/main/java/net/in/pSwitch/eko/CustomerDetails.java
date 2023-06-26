package net.in.pSwitch.eko;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDetails {
    @JsonProperty("customer_id_type")
    private String customerIdType;
    @JsonProperty("bc_available_limit")
    private Integer bcAvailableLimit;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("used_limit")
    private Integer usedLimit;
    @JsonProperty("total_limit")
    private Integer totalLimit;
    @JsonProperty("available_limit")
    private Integer availableLimit;
    @JsonProperty("user_code")
    private String userCode;
    @JsonProperty("state_desc")
    private String stateDesc;
    @JsonProperty("name")
    private String name;
    @JsonProperty("limit")
    private List<Limit> limit;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("state")
    private String state;
    @JsonProperty("wallet_available_limit")
    private Integer walletAvailableLimit;
    @JsonProperty("customer_id")
    private String customerId;

//   New Customer Additional params
    @JsonProperty("otp")
    private String otp;
    @JsonProperty("pipe")
    private Integer pipe;

    @JsonProperty("otp_ref_id")
    private String otpRefId;
}
