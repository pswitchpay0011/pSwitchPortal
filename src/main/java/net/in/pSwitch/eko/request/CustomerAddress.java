package net.in.pSwitch.eko.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerAddress {
    @JsonProperty("line")
    private String line;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("pincode")
    private String pincode;
    @JsonProperty("district")
    private String district;
    @JsonProperty("area")
    private String area;
}
