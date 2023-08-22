package net.in.pSwitch.eko.model;

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
import java.util.List;

@Data
@Entity(name = "recipient_data")
public class RecipientData {

    @Id
    @JsonProperty("mobile_number")
    private String mobileNumber;

    @JsonProperty("pan_required")
    private Integer panRequired;
    @JsonProperty("user_code")
    private String userCode;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_details_id")
    @JsonProperty("recipient_list")
    private List<Recipient> recipientList;

    @JsonProperty("remaining_limit_before_pan_required")
    private Integer remainingLimitBeforePanRequired;

    @CreationTimestamp
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on", nullable = false)
    private LocalDateTime lastUpdatedOn;
}
