package net.in.pSwitch.model.commission;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tblDMTAmountCashBack")
public class DMTAmountCashBack  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DMTTransferAmountId", nullable = false)
    private int dmtTransferAmountId;

    @Column(name = "SuperAdminCashBack", precision = 18, scale = 2)
    private Double superAdminCashBack;

    @Column(name = "MasterDistributorUserId")
    private Integer masterDistributorUserId;

    @Column(name = "MasterDistributorCashBack", precision = 18, scale = 2)
    private Double masterDistributorCashBack;

    @Column(name = "DistributorUserId")
    private Integer distributorUserId;

    @Column(name = "DistributorCashBack", precision = 18, scale = 2)
    private Double distributorCashBack;

    @Column(name = "RetailerUserId", nullable = false)
    private int retailerUserId;

    @Column(name = "RetailerCashBack", precision = 18, scale = 2)
    private Double retailerCashBack;


}