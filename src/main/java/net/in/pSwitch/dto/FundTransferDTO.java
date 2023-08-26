package net.in.pSwitch.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

@Data
public class FundTransferDTO {
    @Getter
    @NotNull(message = "User cannot be blank")
    private String toUser;
    @NotNull(message = "amount cannot be blank")
    private String amount;
    private String remark;
    @NotNull(message = "role cannot be blank")
    private String role;


    public Integer getUserId() {
           if (!StringUtils.isEmpty(toUser))
               return Integer.parseInt(toUser);
        return null;
    }
}
