package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PostalPincode {
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("PostOffice")
    private List<PostOffice> postOffice = null;
    private Map<String, Object> additionalProperties = new HashMap<>();
}
