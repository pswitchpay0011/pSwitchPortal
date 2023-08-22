package net.in.pSwitch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateRemitterDTO {

    private String fName;
    private String lName;
    private String address;
    private String zipcode;
    private String city="0";
    private String state="0";
    private String dbo;
    private String remitterMobileNumber;

    public String getFullName(){
        return fName+" "+lName;
    }
}
