package net.in.pSwitch.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "aadhaar_verification")
public class AadhaarVerification {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;

    @JsonProperty("ref_id")
    @Column(name = "ref_id")
    private String refId;

    @JsonProperty("care_of")
    @Column(name = "care_of")
    private String careOf;

    @JsonProperty("address")
    private String address;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("email")
    private String email;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("name")
    private String name;

    @JsonProperty("photo_link")
    @Lob
    @Column(name = "photo_link")
    private String photoLink;

    @JsonProperty("mobile_hash")
    @Column(name = "mobile_hash")
    private String mobileHash;

    @OneToOne
    @JoinColumn(name = "split_address_id", referencedColumnName = "split_address_id")
    @JsonProperty("split_address")
    private SplitAddress splitAddress;

    @JsonProperty("year_of_birth")
    @Column(name = "year_of_birth")
    private String yearOfBirth;

    @JsonProperty("type")
    private String type;

    @JsonProperty("code")
    private String code;
}
