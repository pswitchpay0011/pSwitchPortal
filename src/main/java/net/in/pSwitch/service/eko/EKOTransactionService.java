package net.in.pSwitch.service.eko;

import net.in.pSwitch.eko.EKODataBuilder;
import net.in.pSwitch.eko.model.InitiateTransaction;
import net.in.pSwitch.eko.model.Refund;
import net.in.pSwitch.eko.model.ResendRefundOTP;
import net.in.pSwitch.eko.model.TransactionInquiry;
import net.in.pSwitch.eko.response.EKOResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class EKOTransactionService {
    Logger logger = LoggerFactory.getLogger(EKOTransactionService.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EKODataBuilder ekoDataBuilder;

    @Value("${eko.endpoint}")
    private String ENDPOINT_URL;

    public EKOResponse<InitiateTransaction> initiateTransaction(MultiValueMap<String, String> requestBody) {
        String url = ENDPOINT_URL + "transactions";

        try {
            HttpHeaders headers = ekoDataBuilder.getHeader();
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Send the request
            ResponseEntity<EKOResponse<InitiateTransaction>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<EKOResponse<InitiateTransaction>>() {
                    });

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                EKOResponse<InitiateTransaction> ekoResponse = responseEntity.getBody();
                InitiateTransaction transaction = ekoResponse.getData();
                // Process the response
                logger.info("CustomerDetails Response: " + transaction);
                return ekoResponse;
            } else {
                // Handle the error response
                logger.error("Error Response: " + responseEntity.getBody());
            }

        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            logger.error("Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            logger.error("Error: ", e);
        }
        return null;
    }

    public EKOResponse<TransactionInquiry> transactionInquiry(String transactionId, String userCode) {
        String url = ENDPOINT_URL + "transactions/" + transactionId + "?initiator_id="
                + ekoDataBuilder.getInitiatorId() + "&user_code=" + userCode;

        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(url)
                    .build()
                    .toUri();

            RequestEntity<Void> requestEntity = new RequestEntity<>(ekoDataBuilder.getHeader(), HttpMethod.GET, uri);
            ResponseEntity<EKOResponse<TransactionInquiry>> response = restTemplate.exchange(requestEntity,
                    new ParameterizedTypeReference<EKOResponse<TransactionInquiry>>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                EKOResponse<TransactionInquiry> ekoResponse = response.getBody();
                TransactionInquiry transactionInquiry = ekoResponse.getData();
                // Process the response
                logger.info("CustomerDetails Response: " + transactionInquiry);
                return ekoResponse;
            } else {
                // Handle the error response
                logger.error("Error Response: " + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            logger.error("Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Error while fetching CustomerDetails", e);
        }

        return null;
    }

    public EKOResponse<Refund> refundTransaction(MultiValueMap<String, String> requestBody, String transactionId) {
        String url = ENDPOINT_URL + "transactions/" + transactionId + "/refund";
//
//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("initiator_id", "9962981729");
//        requestBody.add("otp", "3682953466");
//        requestBody.add("state", "1");
//        requestBody.add("user_code", "20810200");
//
//        HttpHeaders headers = ekoDataBuilder.getHeader();
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        try {
            HttpHeaders headers = ekoDataBuilder.getHeader();
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Send the request
            ResponseEntity<EKOResponse<Refund>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<EKOResponse<Refund>>() {
                    });

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                EKOResponse<Refund> ekoResponse = responseEntity.getBody();
                Refund refund = ekoResponse.getData();
                // Process the response
                logger.info("CustomerDetails Response: " + refund);
                return ekoResponse;
            } else {
                // Handle the error response
                logger.error("Error Response: " + responseEntity.getBody());
            }

        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            logger.error("Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            logger.error("Error: ", e);
        }
        return null;
    }

    public EKOResponse<ResendRefundOTP> refundTransactionOTP(String transactionId, String userCode) {
        String url = ENDPOINT_URL + "transactions/" + transactionId + "/refund/otp";

        try {
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("initiator_id", ekoDataBuilder.getInitiatorId());
            requestBody.add("user_code", userCode);

            HttpHeaders headers = ekoDataBuilder.getHeader();

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);


            // Send the request
            ResponseEntity<EKOResponse<ResendRefundOTP>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<EKOResponse<ResendRefundOTP>>() {
                    });
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                EKOResponse<ResendRefundOTP> ekoResponse = responseEntity.getBody();
                ResendRefundOTP refundOTP = ekoResponse.getData();
                // Process the response
                logger.info("RefundOTP Response: " + refundOTP);
                return ekoResponse;
            } else {
                // Handle the error response
                logger.error("Error Response: " + responseEntity.getBody());
            }

        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            logger.error("Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            logger.error("Error: ", e);
        }
        return null;
    }
}
