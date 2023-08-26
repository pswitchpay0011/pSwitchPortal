package net.in.pSwitch.model;

import lombok.Data;
import net.in.pSwitch.model.user.UserInfo;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tblFundTransfer")
public class FundTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "transferredFromUserId")
    private Integer transferredFromUserId;

    @Column(name = "transferredToUserId")
    private Integer transferredToUserId;

    @Column(name = "beforeAmount")
    private Double beforeAmount;

    @Column(name = "afterAmount")
    private Double afterAmount;

    @Column(name = "senderBeforeAmount")
    private Double senderBeforeAmount;

    @Column(name = "senderAfterAmount")
    private Double senderAfterAmount;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "isActive")
    private Integer isActive;

    @Column(name = "createdBy")
    private Integer createdBy;

    @Column(name = "modifiedBy")
    private Integer modifiedBy;

    @UpdateTimestamp
    @Column(name = "modifiedOn")
    private LocalDateTime modifiedOn;

    @CreationTimestamp
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

}
