package net.in.pSwitch.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TransactionValidation")
public class TransactionValidation {

    @Id
    @JsonProperty("utr")
    private String utr;
    @JsonProperty("bene_acc_no")
    private String beneAccNo;
    @JsonProperty("req_type")
    private String reqType;
    @JsonProperty("req_dt_time")
    private String reqDtTime;
    @JsonProperty("txn_amnt")
    private String txnAmount;
    @JsonProperty("corp_code")
    private String corpCode;
    @JsonProperty("pmode")
    private String pMode;
    @JsonProperty("sndr_acnt")
    private String sndrAcnt;
    @JsonProperty("sndr_nm")
    private String sndrNm;
    @JsonProperty("sndr_acnt1")
    private String sndrAcnt1;
    @JsonProperty("sndr_nm1")
    private String sndrNm1;
    @JsonProperty("sndr_ifsc")
    private String sndrIfsc;
}
