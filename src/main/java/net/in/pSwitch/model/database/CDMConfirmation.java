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
@Table(name = "CDMConfirmation")
public class CDMConfirmation {

    @Column(name = "user_id")
    private String appLUserID;

//    Unique request ID from Bank (UTR)
    @Id
    @Column(name = "utr")
    private String utr;

//    16 Digit CDM Card No.
    @Column(name = "cdm_card_no")
    private String cDMCardNo;

//    Emboss Code on card
    @Column(name = "agent_id")
    private String agentId;

//    Transaction Amount
    @Column(name = "txn_amount")
    private String txnAmount;

//    Transaction Date & Time
    @Column(name = "req_dt_time")
    private String reqDtTime;

//    Pass 4 digit Sequence No.
    @Column(name = "txn_number")
    private String txnNumber;

//    Shared by Bank
    @Column(name = "corp_code")
    private String corpCode;

//    CDM
    @Column(name = "p_mode")
    private String pMode;
}
