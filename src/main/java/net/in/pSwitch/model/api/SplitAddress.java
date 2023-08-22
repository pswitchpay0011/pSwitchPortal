package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "aadhaar_split_Address")
public class SplitAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "split_address_id", nullable = false)
    private Integer id;

    @JsonProperty("country")
    private String country;
    @JsonProperty("dist")
    private String dist;
    @JsonProperty("house")
    private String house;
    @JsonProperty("landmark")
    private String landmark;
    @JsonProperty("pincode")
    private String pincode;
    @JsonProperty("postOffice")
    private String postOffice;
    @JsonProperty("state")
    private String state;
    @JsonProperty("street")
    private String street;
    @JsonProperty("subdist")
    private String subdist;
    @JsonProperty("vtc")
    private String vtc;
}
