package net.in.pSwitch.authentication;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginUserInfo {
    private Integer id;
    private String name;
}
