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
@Table(name = "tblDmtAgentsAmount")
public class DMTAgentsAmount implements Serializable {

        private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "DMTTransferAmountId", nullable = false)
    private Integer dmtTransferAmountId;

    @Column(name = "RetailerUserId", nullable = false)
    private Integer retailerUserId;

    @Column(name = "RetailerBeforeAmount", nullable = false, precision = 18, scale = 2)
    private Double retailerBeforeAmount;

    @Column(name = "RetailerAfterAmount", nullable = false, precision = 18, scale = 2)
    private Double retailerAfterAmount;

    @Column(name = "DistributorUserId")
    private Integer distributorUserId;

    @Column(name = "DistributorBeforeAmount", precision = 18, scale = 2)
    private Double distributorBeforeAmount;

    @Column(name = "DistributorAfterAmount", precision = 18, scale = 2)
    private Double distributorAfterAmount;

    @Column(name = "MasterDistributorUserId")
    private Integer masterDistributorUserId;

    @Column(name = "MasterDistributorBeforeAmount", precision = 18, scale = 2)
    private Double masterDistributorBeforeAmount;

    @Column(name = "MasterDistributorAfterAmount", precision = 18, scale = 2)
    private Double masterDistributorAfterAmount;

    @Column(name = "CompanyBeforeAmount", precision = 18, scale = 2)
    private Double companyBeforeAmount;

    @Column(name = "CompanyAfterAmount", precision = 18, scale = 2)
    private Double companyAfterAmount;


}