package net.in.pSwitch.model.request;

import lombok.Data;

@Data
public class TokenRequest {
    private static final long serialVersionUID = 5926468583005150707L;

    private String UserName;
    private String Password;

}
