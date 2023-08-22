package net.in.pSwitch.eko;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "transaction_limit")
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "customer_details_id")
//    private CustomerDetails customerDetails;

    @JsonProperty("is_registered")
    private Integer isRegistered;
    @JsonProperty("name")
    private String name;
    @JsonProperty("pipe")
    private String pipe;
    @JsonProperty("used")
    private String used;
    @JsonProperty("priority")
    private Integer priority;
    @JsonProperty("remaining")
    private String remaining;
    @JsonProperty("status")
    private String status;
}
