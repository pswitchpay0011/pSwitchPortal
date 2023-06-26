package net.in.pSwitch.eko;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Limit {
    @JsonProperty("is_registered")
    private Integer isRegistered;
    @JsonProperty("name")
    private String name;
    @JsonProperty("pipe")
    private String pipe;
    @JsonProperty("used")
    private String used;
    @JsonProperty("priority")
    private Integer priority;
    @JsonProperty("remaining")
    private String remaining;
    @JsonProperty("status")
    private String status;
}
