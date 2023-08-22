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
@Table(name = "TblDMTTDSDetails")
public class DMTTDSDetails  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DMTTransferAmountId", nullable = false)
    private int dmtTransferAmountId;

    @Column(name = "SuperAdminTDS", precision = 18, scale = 2)
    private Double superAdminTDS;

    @Column(name = "MasterDistributorUserId")
    private Integer masterDistributorUserId;

    @Column(name = "MasterDistributorTDS", precision = 18, scale = 2)
    private Double masterDistributorTDS;

    @Column(name = "DistributorUserId")
    private Integer distributorUserId;

    @Column(name = "DistributorTDS", precision = 18, scale = 2)
    private Double distributorTDS;

    @Column(name = "RetailerUserId")
    private Integer retailerUserId;

    @Column(name = "RetailerTDS", precision = 18, scale = 2)
    private Double retailerTDS;



}