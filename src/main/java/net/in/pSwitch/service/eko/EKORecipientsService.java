package net.in.pSwitch.service.eko;

import net.in.pSwitch.eko.EKODataBuilder;
import net.in.pSwitch.eko.model.AddRecipientResponse;
import net.in.pSwitch.eko.model.RecipientData;
import net.in.pSwitch.eko.model.RecipientDetails;
import net.in.pSwitch.eko.response.EKOResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class EKORecipientsService {
    Logger logger = LoggerFactory.getLogger(EKORecipientsService.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EKODataBuilder ekoDataBuilder;

    @Value("${eko.endpoint}")
    private String ENDPOINT_URL;

    public EKOResponse<RecipientData> getRecipientList(String mobileNumber, String userCode) {
        String url = ENDPOINT_URL + "customers/mobile_number:" + mobileNumber + "/recipients?initiator_id="
                + ekoDataBuilder.getInitiatorId() + "&user_code=" + userCode;

        HttpHeaders headers = ekoDataBuilder.getHeader();

        try {
            ResponseEntity<EKOResponse<RecipientData>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<EKOResponse<RecipientData>>() {
                    },
                    headers
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                EKOResponse<RecipientData> ekoResponse = responseEntity.getBody();
                RecipientData recipients = ekoResponse.getData();
                // Process the response
                logger.info("Response: " + ekoResponse);
                return ekoResponse;
            } else {
                // Handle the error response
                logger.error("Error Response: " + responseEntity.getBody().getMessage());
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            logger.error("Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            logger.error("Error: ", e);
        }
        return null;
    }

    public EKOResponse<RecipientDetails> getRecipientDetails(String mobileNumber, String recipientId, String userCode) {
        String url = ENDPOINT_URL + "customers/mobile_number:" + mobileNumber + "/recipients/recipient_id:"
                + recipientId + "?initiator_id=" + ekoDataBuilder.getInitiatorId()
                + "&user_code=" + userCode;

        HttpHeaders headers = ekoDataBuilder.getHeader();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        try {
            ResponseEntity<EKOResponse<RecipientDetails>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<EKOResponse<RecipientDetails>>() {
                    },
                    headers
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                EKOResponse<RecipientDetails> ekoResponse = responseEntity.getBody();
                RecipientDetails recipient = ekoResponse.getData();
                // Process the response
                logger.info("Response: " + ekoResponse);
                return ekoResponse;
            } else {
                // Handle the error response
                logger.error("Error Response: " + responseEntity.getBody().getMessage());
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            logger.error("Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Error", e);
        }
        return null;
    }

    public EKOResponse<AddRecipientResponse> addRecipient(String mobileNumber, String accIfsc, MultiValueMap<String, String> params) {
        String url = ENDPOINT_URL + "customers/mobile_number:" + mobileNumber + "/recipients/acc_ifsc:"+accIfsc;

        HttpHeaders headers = ekoDataBuilder.getHeader();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

        try {
            // Create the request entity with headers and parameters
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            // Make the PUT request
            ResponseEntity<EKOResponse<AddRecipientResponse>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    new ParameterizedTypeReference<EKOResponse<AddRecipientResponse>>() {
                    }
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                EKOResponse<AddRecipientResponse> ekoResponse = responseEntity.getBody();
                AddRecipientResponse recipients = ekoResponse.getData();
                // Process the response
                logger.info("Response: " + ekoResponse);
                return ekoResponse;
            } else {
                // Handle the error response
                logger.error("Error Response: " + responseEntity.getBody().getMessage());
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            logger.error("Error: ", e);
        } catch (Exception e) {
            logger.error("Error: ", e);
        }
        return null;
    }
}
