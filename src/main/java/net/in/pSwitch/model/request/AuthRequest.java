package net.in.pSwitch.model.request;

import lombok.Data;

@Data
public class AuthRequest {
    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;
}
