package net.in.pSwitch.eko.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResendRefundOTP {

    @JsonProperty("user_code")
    private String userCode;
    @JsonProperty("otp")
    private String otp;
}
