package net.in.pSwitch.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class KycDetails {

    private String userId;
    private String refId;
    private String stepNo;
    private String businessName;
    private String address;
    private String zipcode;
    private String state;
    private String city;
    private String typeOfShops;
    private String typeOfMcc;
    private String aadhaarCardNumber;
    private String panCard;
    private String termAndConditions;
    private String eSignAgreement;
    private String bankName;
    private String ifscCode;
    private String accountType;
    private String accountNumber;
    private String accountHolderName;

    public Long getTypeOfShopCode(){
        if(!StringUtils.isEmpty(typeOfShops)) {
            return Long.parseLong(typeOfShops);
        }
        return 0l;
    }
    public Long getTypeOfMccCode(){
        if(!StringUtils.isEmpty(typeOfMcc)) {
            return Long.parseLong(typeOfMcc);
        }
        return 0l;
    }

    public Long getBankCode(){
        if(!StringUtils.isEmpty(bankName)) {
            return Long.parseLong(bankName);
        }
        return 0l;
    }

    public Long getStateCode(){
        if(!StringUtils.isEmpty(state)) {
            return Long.parseLong(state);
        }
        return 0l;
    }

    public Long getCityCode(){
        if(!StringUtils.isEmpty(city)) {
            return Long.parseLong(city);
        }
        return 0l;
    }
}
