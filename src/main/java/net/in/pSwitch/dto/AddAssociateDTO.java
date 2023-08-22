package net.in.pSwitch.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

@Data
public class AddAssociateDTO {
    @NotNull(message = "firstName cannot be blank")
    private String firstName;
    @NotNull(message = "lastName cannot be blank")
    private String lastName;
    private String middleName;
    @NotNull(message = "role cannot be blank")
    private String role;
    private String password;
    private String username;

    @NotNull(message = "mobileNumber cannot be blank")
    private String mobileNumber;
    @NotNull(message = "address cannot be blank")
    private String address;
    @NotNull(message = "city cannot be blank")
    private String city;
    @NotNull(message = "state cannot be blank")
    private String state;
    @NotNull(message = "zipcode cannot be blank")
    private String zipcode;

    private String country;

    private String masterDistributor;
    private String distributor;
    @NotNull(message = "Email cannot be blank")
    private String email;

    private String latLng;
    @NotNull(message = "Date of birth cannot be blank")
    private String dob;

    public Long getStateCode(){
        if(!StringUtils.isEmpty(state)) {
            return Long.parseLong(state);
        }
        return 0l;
    }
    public Long getCityCode(){
        if(!StringUtils.isEmpty(city)) {
            return Long.parseLong(city);
        }
        return 0l;
    }
}
