package net.in.pSwitch.eko.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.in.pSwitch.eko.InvalidParams;

@Data
public class EKOResponse<T> {

    @JsonProperty("response_status_id")
    private Integer responseStatusId;
    @JsonProperty("data")
    private T data;
    @JsonProperty("response_type_id")
    private Integer responseTypeId;
    @JsonProperty("message")
    private String message;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("invalid_params")
    private InvalidParams invalidParams;
    private boolean error=false;
}
