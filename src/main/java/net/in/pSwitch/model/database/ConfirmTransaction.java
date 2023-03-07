package net.in.pSwitch.model.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ConfirmTransaction")
public class ConfirmTransaction {

    @Id
    @Column(name = "utr")
    private String utr;
    @Column(name = "bene_acc_no")
    private String beneAccNo;
    @Column(name = "req_type")
    private String reqType;
    @Column(name = "req_dt_time")
    private String reqDtTime;
    @Column(name = "txn_amnt")
    private String txnAmnt;
    @Column(name = "corp_code")
    private String corpCode;
    @Column(name = "pmode")
    private String pmode;
    @Column(name = "sndr_acnt")
    private String sndrAcnt;
    @Column(name = "sndr_nm")
    private String sndrNm;
    @Column(name = "sndr_acnt1")
    private String sndrAcnt1;
    @Column(name = "sndr_nm1")
    private String sndrNm1;
    @Column(name = "sndr_ifsc")
    private String sndrIfsc;
    @Column(name = "rran_id")
    private String tranId;
}
