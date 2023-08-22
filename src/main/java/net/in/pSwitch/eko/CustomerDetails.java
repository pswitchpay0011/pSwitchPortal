package net.in.pSwitch.eko;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "customer_details")
public class CustomerDetails {
    @Id
    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("customer_id_type")
    private String customerIdType;
    @JsonProperty("bc_available_limit")
    private Integer bcAvailableLimit;
    @JsonProperty("used_limit")
    private Integer usedLimit;
    @JsonProperty("total_limit")
    private Integer totalLimit;
    @JsonProperty("available_limit")
    private Integer availableLimit;
    @JsonProperty("user_code")
    private String userCode;
    @JsonProperty("state_desc")
    private String stateDesc;
    @JsonProperty("name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_details_id")
    @JsonProperty("limit")
    private List<Limit> limit = new ArrayList<>();

    @JsonProperty("currency")
    private String currency;
    @JsonProperty("state")
    private String state;
    @JsonProperty("wallet_available_limit")
    private Integer walletAvailableLimit;


//   New Customer Additional params
    @JsonProperty("otp")
    private String otp;
    @JsonProperty("pipe")
    private Integer pipe;

    @JsonProperty("otp_ref_id")
    private String otpRefId;

    @CreationTimestamp
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on", nullable = false)
    private LocalDateTime lastUpdatedOn;
}
