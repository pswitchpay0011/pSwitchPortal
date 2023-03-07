package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class TokenData {
    @JsonProperty("token")
    private String token;
    @JsonProperty("expiry")
    private Integer expiry;
}
