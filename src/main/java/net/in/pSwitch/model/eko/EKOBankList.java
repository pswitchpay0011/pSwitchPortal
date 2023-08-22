package net.in.pSwitch.model.eko;

import lombok.Getter;
import lombok.Setter;
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

@Getter
@Setter
@Entity
@Table(name = "eko_bank_list")
public class EKOBankList  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "bank_id", nullable = false)
    private Integer bankId;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "short_code")
    private String shortCode;

    @Column(name = "IMPS_Status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status IMPSStatus = Status.DISABLED;;

    @Column(name = "NEFT_Status", nullable = false)
    private String NEFTStatus;

    @Column(name = "IsVerificationAvailable", nullable = false)
    private String isVerificationAvailable;

}
