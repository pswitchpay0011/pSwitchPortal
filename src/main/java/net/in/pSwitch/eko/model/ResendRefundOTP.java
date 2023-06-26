package net.in.pSwitch.eko.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResendRefundOTP {

    @JsonProperty("tid")
    private String tid;
    @JsonProperty("otp")
    private String otp;
}
