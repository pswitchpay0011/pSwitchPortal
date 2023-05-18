package net.in.pSwitch.service;

import net.in.pSwitch.model.Response;
import net.in.pSwitch.model.api.AadhaarOTPResponse;
import net.in.pSwitch.model.api.AadhaarVerification;
import net.in.pSwitch.model.api.BankVerificationResponse;
import net.in.pSwitch.model.api.GSTINResponse;
import net.in.pSwitch.model.api.PanCardVerification;
import net.in.pSwitch.model.api.TokenResponse;
import net.in.pSwitch.repository.ShopTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CashFreeService {
    private Logger LOGGER = LoggerFactory.getLogger(CashFreeService.class);
    final static String VERIFICATION_URL = "https://api.cashfree.com/verification/";
    @Autowired
    RestTemplate restTemplate;

    //    x-client-id
    private final String xClientID = "CF351152CF6E7MQOMVJS368UCFL0";
    //    x-client-secret
    private final String xClientSecret = "54e753e45a2f9340e0c88fc4bd0fc6781f7dc2d3";
    @Autowired
    private ShopTypeRepository shopTypeRepository;


    private HttpHeaders getHttpHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.add("x-client-id", xClientID);
        header.add("x-client-secret", xClientSecret);

        return header;
    }

    public Response generateAadhaarOTP(String aadhaarCard) {
        String url = VERIFICATION_URL + "offline-aadhaar/otp";

        Map<String, String> map = new HashMap<>();
        map.put("aadhaar_number", aadhaarCard);
        AadhaarOTPResponse otpResponse = null;
        Response response = new Response();
        // build the request
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, getHttpHeader());
        try {
            ResponseEntity<AadhaarOTPResponse> responseEntity = restTemplate.postForEntity(url, entity, AadhaarOTPResponse.class);
            otpResponse = responseEntity.getBody();


            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if(otpResponse.getStatus().equalsIgnoreCase("SUCCESS")){
                    response.setError(false);
                }else{
                    response.setError(true);
                }
            } else {
                response.setError(true);
            }
        } catch (RestClientException e) {
            LOGGER.error("Error: {}", e);
            otpResponse = new AadhaarOTPResponse();
            otpResponse.setMessage("Error while fetching OTP. please try again later");
        } catch (Exception e) {
            LOGGER.error("Error: {}", e);
            otpResponse = new AadhaarOTPResponse();
            otpResponse.setMessage("Error while fetching OTP. please try again later");
        }

        response.setResult(otpResponse);
        response.setMessage(otpResponse.getMessage());
        return response;
    }

    public Response verifyAadhaarOTP(String refId, String otp) {
        String url = VERIFICATION_URL + "offline-aadhaar/verify";

        Map<String, String> map = new HashMap<>();
        map.put("otp", otp);
        map.put("ref_id", refId);
        AadhaarVerification verification = null;
        Response response = new Response();
        // build the request
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, getHttpHeader());
        try {
            ResponseEntity<AadhaarVerification> responseEntity = restTemplate.postForEntity(url, entity, AadhaarVerification.class);
            verification = responseEntity.getBody();

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if (verification.getStatus().equalsIgnoreCase("VALID")) {
                    verification.setMessage("Aadhaar verified successfully");
                }
            } else {
                response.setError(true);
            }
        } catch (RestClientException e) {
            LOGGER.error("Error: {}", e);
            verification = new AadhaarVerification();
            verification.setMessage("Error while verifying OTP. please try again later");
            response.setError(true);
        } catch (Exception e) {
            LOGGER.error("Error: {}", e);
            verification = new AadhaarVerification();
            response.setError(true);
            verification.setMessage("Error while verifying OTP. please try again later");
        }

        response.setResult(verification);
        response.setMessage(verification.getMessage());
        return response;
    }
    
    public Response getGSTDetails(String gstin) {
        String url = VERIFICATION_URL + "gstin";

        Map<String, String> map = new HashMap<>();
        map.put("GSTIN", gstin);
        GSTINResponse gstinResponse = null;
        Response response = new Response();
        // build the request
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, getHttpHeader());
        try {
            ResponseEntity<GSTINResponse> responseEntity = restTemplate.postForEntity(url, entity, GSTINResponse.class);
            gstinResponse = responseEntity.getBody();

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if (gstinResponse.getValid()) {
                    gstinResponse.setMessage("GST details get successfully");
                }
            } else {
                response.setError(true);
            }
        } catch (RestClientException e) {
            LOGGER.error("Error: {}", e);
            gstinResponse = new GSTINResponse();
            gstinResponse.setMessage("Error while fetching GST details. please try again later");
            response.setError(true);
        } catch (Exception e) {
            LOGGER.error("Error: {}", e);
            gstinResponse = new GSTINResponse();
            response.setError(true);
            gstinResponse.setMessage("Error while fetching GST details. please try again later");
        }

        response.setResult(gstinResponse);
        response.setMessage(gstinResponse.getMessage());
        return response;
    }

    public Response<PanCardVerification> verifyPanCard(String panCard) {
        String url = VERIFICATION_URL + "pan";

        Map<String, String> map = new HashMap<>();
        map.put("pan", panCard);
        PanCardVerification verification = null;
        Response response = new Response();
        // build the request
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, getHttpHeader());
        try {
            ResponseEntity<PanCardVerification> responseEntity = restTemplate.postForEntity(url, entity, PanCardVerification.class);
            verification = responseEntity.getBody();

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if (verification.getValid()!=null && verification.getValid()) {
                    verification.setMessage("PAN verified successfully");
                }
            } else {
                response.setError(true);
            }
        } catch (RestClientException e) {
            LOGGER.error("Error: {}", e);
            verification = new PanCardVerification();
            verification.setMessage("Error while verifying PAN. please try again later");
            response.setError(true);
        } catch (Exception e) {
            LOGGER.error("Error: {}", e);
            verification = new PanCardVerification();
            response.setError(true);
            verification.setMessage("Error while verifying PAN. please try again later");
        }

        response.setResult(verification);
        response.setMessage(verification.getMessage());
        return response;
    }

    public Response<TokenResponse> getToken() {
        String url = "https://payout-api.cashfree.com/payout/v1/authorize";

        Map<String, String> map = new HashMap<>();
        TokenResponse verification = null;
        Response response = new Response();

//        HttpHeaders header = new HttpHeaders();
//        header.setContentType(MediaType.APPLICATION_JSON);
//        header.add("X-Client-Id", xClientID);
//        header.add("X-Client-Secre", xClientSecret);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, getHttpHeader());
        try {
            ResponseEntity<TokenResponse> responseEntity = restTemplate.postForEntity(url, entity, TokenResponse.class);
            verification = responseEntity.getBody();

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
            } else {
                response.setError(true);
            }
        } catch (RestClientException e) {
            LOGGER.error("Error: {}", e);
            response.setError(true);
        } catch (Exception e) {
            LOGGER.error("Error: {}", e);
            response.setError(true);
        }

        response.setResult(verification);
        return response;
    }

    public Response<BankVerificationResponse> verifyBankDetails(String accountNumber, String ifscCode) {

        Response<TokenResponse> token = getToken();
        Response response = new Response();
        if (!token.isError()) {

            String url = "https://payout-api.cashfree.com/payout/v1.2/validation/bankDetails?bankAccount=" + accountNumber + "&ifsc=" + ifscCode;

            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            header.add(HttpHeaders.AUTHORIZATION, "Bearer "+token.getResult().getData().getToken());

            BankVerificationResponse verification = null;

            HttpEntity<String> entity = new HttpEntity<String>(header);
            try {
                ResponseEntity<BankVerificationResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, BankVerificationResponse.class);
                verification = responseEntity.getBody();

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    if (verification.getStatus() != null && verification.getStatus().equalsIgnoreCase("SUCCESS")) {
                        verification.setMessage("Bank verified successfully");
                    }else{
                        response.setError(true);
                    }
                } else {
                    response.setError(true);
                }
            } catch (RestClientException e) {
                LOGGER.error("Error: {}", e);
                verification = new BankVerificationResponse();
                verification.setMessage("Error while verifying Bank details. please try again later");
                response.setError(true);
            } catch (Exception e) {
                LOGGER.error("Error: {}", e);
                verification = new BankVerificationResponse();
                response.setError(true);
                verification.setMessage("Error while verifying Bank details. please try again later");
            }

            response.setResult(verification);
            response.setMessage(verification.getMessage());
        }else {
            response.setError(true);
            response.setMessage("Error while verifying Bank details. please try again later");
        }
        return response;
    }
}
