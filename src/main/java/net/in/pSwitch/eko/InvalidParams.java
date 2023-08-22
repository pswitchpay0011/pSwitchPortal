package net.in.pSwitch.eko;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class InvalidParams {

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("tid")
    private String tid;

    @JsonProperty("otp")
    private String otp;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
