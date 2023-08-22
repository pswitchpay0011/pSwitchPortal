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
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TblDMTTransferAmountRefund")
public class DMTTransferAmountRefund  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "SubscriptionId", length = 100)
    private String subscriptionId;

    @Column(name = "DMTAPIDetailsId")
    private Integer dmtApiDetailsId;

    @Column(name = "PaymentModeId")
    private Integer paymentModeId;

    @Column(name = "RetailerUserId", nullable = false)
    private int retailerUserId;

    @Column(name = "RemmitterUserId", nullable = false)
    private int remitterUserId;

    @Column(name = "DMTBeneficiaryId", nullable = false)
    private int dmtBeneficiaryId;

    @Column(name = "TransferredAmount", nullable = false, precision = 18, scale = 2)
    private Double transferredAmount;

    @Column(name = "TotalCommission", nullable = false, precision = 18, scale = 2)
    private Double totalCommission;

    @Column(name = "TransactionId", length = 50)
    private String transactionId;

    @Column(name = "GstTds", precision = 18, scale = 2)
    private Double gstTds;

    @Column(name = "Gst", precision = 18, scale = 2)
    private Double gst;

    @Column(name = "Tds", precision = 18, scale = 2)
    private Double tds;

    @Column(name = "Response", length = 500)
    private String response;

    @Column(name = "RequestId", length = 50)
    private String requestId;

    @Column(name = "Status", length = 500)
    private String status;

    @Column(name = "ClientRefId", length = 50)
    private String clientRefId;

    @Column(name = "APIRequest", length = 2147483647) // Maximum length for varchar(MAX)
    private String apiRequest;

    @Column(name = "APIResponse", length = 2147483647) // Maximum length for varchar(MAX)
    private String apiResponse;

    @Column(name = "BeforeBalance", precision = 18, scale = 2)
    private Double beforeBalance;

    @Column(name = "AfterBalance", precision = 18, scale = 2)
    private Double afterBalance;

    @Column(name = "DistributorBeforeAmount", precision = 18, scale = 2)
    private Double distributorBeforeAmount;

    @Column(name = "DistributorAfterAmount", precision = 18, scale = 2)
    private Double distributorAfterAmount;

    @Column(name = "MasterDistributorBeforeAmount", precision = 18, scale = 2)
    private Double masterDistributorBeforeAmount;

    @Column(name = "MasterDistributorAfterAmount", precision = 18, scale = 2)
    private Double masterDistributorAfterAmount;

    @Column(name = "IsActive", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status isActive;

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


}