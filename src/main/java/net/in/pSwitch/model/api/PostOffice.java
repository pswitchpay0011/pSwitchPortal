package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PostOffice {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Description")
    private Object description;
    @JsonProperty("BranchType")
    private String branchType;
    @JsonProperty("DeliveryStatus")
    private String deliveryStatus;
    @JsonProperty("Circle")
    private String Circle;
    @JsonProperty("District")
    private String district;
    @JsonProperty("Division")
    private String division;
    @JsonProperty("Region")
    private String region;
    @JsonProperty("Block")
    private String block;
    @JsonProperty("State")
    private String state;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Pincode")
    private String pincode;
    private Map<String, Object> additionalProperties = new HashMap<>();

}
