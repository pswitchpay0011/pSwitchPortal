package net.in.pSwitch.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "ConfirmTransactionRequest")
public class ConfirmTransactionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JsonProperty("UTR")
    private String utr;
    @JsonProperty("Bene_acc_no")
    private String beneAccNo;
    @JsonProperty("Req_type")
    private String reqType;
    @JsonProperty("Req_dt_time")
    private String reqDtTime;
    @JsonProperty("Txn_amnt")
    private String txnAmnt;
    @JsonProperty("Corp_code")
    private String corpCode;
    @JsonProperty("Pmode")
    private String pmode;
    @JsonProperty("Sndr_acnt")
    private String sndrAcnt;
    @JsonProperty("Sndr_nm")
    private String sndrNm;
    @JsonProperty("Sndr_acnt1")
    private String sndrAcnt1;
    @JsonProperty("Sndr_nm1")
    private String sndrNm1;
    @JsonProperty("Sndr_ifsc")
    private String sndrIfsc;
    @JsonProperty("Tran_id")
    private String tranId;

    @JsonProperty("Stts_flg")
    private String sttsFlg;
    @JsonProperty("Err_cd")
    private String errCd;
    @JsonProperty("message")
    private String message;
}
