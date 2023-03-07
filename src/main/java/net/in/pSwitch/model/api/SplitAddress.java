package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SplitAddress {
    @JsonProperty("country")
    private String country;
    @JsonProperty("dist")
    private String dist;
    @JsonProperty("house")
    private String house;
    @JsonProperty("landmark")
    private String landmark;
    @JsonProperty("pincode")
    private String pincode;
    @JsonProperty("postOffice")
    private String postOffice;
    @JsonProperty("state")
    private String state;
    @JsonProperty("street")
    private String street;
    @JsonProperty("subdist")
    private String subdist;
    @JsonProperty("vtc")
    private String vtc;
}
