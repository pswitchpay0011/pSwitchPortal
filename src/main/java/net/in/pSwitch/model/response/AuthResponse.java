package net.in.pSwitch.model.response;

import lombok.Data;
import net.in.pSwitch.model.user.Role;

@Data
public class AuthResponse {
    private static final long serialVersionUID = 5926468583005150707L;

    private String username;

    private Role role;


    private String token;
}
