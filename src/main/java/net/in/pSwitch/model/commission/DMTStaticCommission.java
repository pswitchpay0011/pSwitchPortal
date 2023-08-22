package net.in.pSwitch.model.commission;

import lombok.Data;
import net.in.pSwitch.model.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tblDMTStaticCommission")
public class DMTStaticCommission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "service_provider_id")
    private Integer serviceProviderId;

    @Column(name = "transaction_from", nullable = false)
    private Double transactionFrom;

    @Column(name = "transaction_to", nullable = false)
    private Double transactionTo;

    @Column(name = "commission", nullable = false)
    private Double commission;

    @Column(name = "provider_charges", nullable = false)
    private Double ProviderCharges;

    @Column(name = "retailer_cashBack", nullable = false)
    private Double RetailerCashBack;

    @Column(name = "distributor_commission", nullable = false)
    private Double DistributorCommission;

    @Column(name = "master_distributor_commission", nullable = false)
    private Double masterDistributorCommission;

    @Column(name = "company_commission", nullable = false)
    private Double companyCommission;

    @Column(name = "is_active")
    @Enumerated(EnumType.STRING)
    private Status isActive;

}
