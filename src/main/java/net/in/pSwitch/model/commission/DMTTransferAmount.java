package net.in.pSwitch.model.commission;

import lombok.Data;
import net.in.pSwitch.model.Status;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tblDMTTransferAmount")
public class DMTTransferAmount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "subscription_id")
    private String SubscriptionId;

    @Column(name = "DMTAPIDetailsId")
    private Integer dmtAPIDetailsId;

    @Column(name = "PaymentModeId")
    private Integer paymentModeId;

    @Column(name = "UserId", nullable = false)
    private Integer userId;

    @Column(name = "RemitterUserId", nullable = false)
    private Integer remitterUserId;

    @Column(name = "DMTBeneficiaryId", nullable = false)
    private Integer dmtBeneficiaryId;

    @Column(name = "TransferredAmount", nullable = false)
    private Double transferredAmount;

    @Column(name = "TotalCommission", nullable = false)
    private Double totalCommission;

    @Column(name = "transactionId")
    private String transactionId;

    @Column(name = "GstTds")
    private Double gstTds;

    @Column(name = "Gst")
    private Double gst;

    @Column(name = "tds")
    private Double tds;

    @Column(name = "Response", length = 500)
    private String response;

    @Column(name = "RequestId", length = 50)
    private String requestId;

    @Column(name = "Status", length = 500)
    private String status;

    @Column(name = "ClientRefId", length = 50)
    private String clientRefId;

    @Lob
    @Column(name = "APIRequest")
    private String apiRequest;

    @Lob
    @Column(name = "APIResponse")
    private String apiResponse;

    @Lob
    @Column(name = "IsRequestFromApp")
    private Integer isRequestFromApp;

    @Column(name = "createdBy", nullable = false)
    private Integer createdBy;

    @Column(name = "updatedBy", nullable = false)
    private Integer updatedBy;

    @CreationTimestamp
    @Column(name = "createdOn", nullable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updatedOn", nullable = false)
    private LocalDateTime updatedOn;

    @Column(name = "is_active")
    @Enumerated(EnumType.STRING)
    private Status isActive;

}
