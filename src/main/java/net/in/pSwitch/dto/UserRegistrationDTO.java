package net.in.pSwitch.dto;

import lombok.Data;

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

    private String latLng;

    /*
     * public UserRegistrationDto() {
     *
     * }
     *
     * public UserRegistrationDto(String firstName, String lastName, String username,
     * Role role, String password) { super(); this.firstName = firstName;
     * this.lastName = lastName; this.role = role; this.username = username; this.password
     * = password; }
     */


}
