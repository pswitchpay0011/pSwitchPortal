package net.in.pSwitch.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "TransactionValidation_Request")
public class TransactionValidationRequest {

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

    @Column(name = "Stts_flg")
    private String sttsFlg;
    @Column(name = "Err_cd")
    private String errCd;
    @Column(name = "message")
    private String message;

    @CreationTimestamp
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on", nullable = false)
    private LocalDateTime UpdatedOn;
}
