package net.in.pSwitch.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String role;
    private String password;
    private String username;

    private String mobileNumber;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    private String masterDistributor;
    private String distributor;
    private String email;

    private String latLng;

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
