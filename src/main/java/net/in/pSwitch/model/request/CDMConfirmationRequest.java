package net.in.pSwitch.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CDMConfirmationRequest {

    @JsonProperty("Appl_User_ID")
    private String applUserID;
    @JsonProperty("UTR")
    private String utr;
    @JsonProperty("CDMCardNo")
    private String cDMCardNo;
    @JsonProperty("Agent_Id")
    private String agentId;
    @JsonProperty("Txn_amnt")
    private String txnAmnt;
    @JsonProperty("Req_dt_time")
    private String reqDtTime;
    @JsonProperty("txn_nmbr")
    private String txnNmbr;
    @JsonProperty("Corp_code")
    private String corpCode;
    @JsonProperty("pmode")
    private String pmode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
