package net.in.pSwitch.eko.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class Pipes {

    @JsonProperty("pipe")
    private Integer pipe;
    @JsonProperty("status")
    private Integer status;
}
