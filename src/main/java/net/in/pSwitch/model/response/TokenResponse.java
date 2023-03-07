package net.in.pSwitch.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {
    private static final long serialVersionUID = 5926468583005150707L;

    private String token;

//    "2021-04-29T11:04:26Z"
    private String expiration;
    private String message;

    private boolean error=false;

}
