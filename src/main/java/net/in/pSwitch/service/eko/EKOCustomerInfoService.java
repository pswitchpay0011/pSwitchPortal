package net.in.pSwitch.service.eko;

import net.in.pSwitch.eko.CustomerDetails;
import net.in.pSwitch.eko.EKODataBuilder;
import net.in.pSwitch.eko.request.CreateCustomer;
import net.in.pSwitch.eko.request.CustomerAddress;
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
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class EKOCustomerInfoService {

    Logger logger = LoggerFactory.getLogger(EKOCustomerInfoService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EKODataBuilder ekoDataBuilder;

    @Value("${eko.endpoint}")
    private String ENDPOINT_URL;

    public EKOResponse<CustomerDetails> getRemitterInfo(String mobileNumber, MultiValueMap<String, String> queryParams) {

        String url = ENDPOINT_URL + "customers/mobile_number:" + mobileNumber;

        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParams(queryParams)
                    .build()
                    .toUri();

            RequestEntity<Void> requestEntity = new RequestEntity<>(ekoDataBuilder.getHeader(), HttpMethod.GET, uri);
            ResponseEntity<EKOResponse<CustomerDetails>> response = restTemplate.exchange(requestEntity,
                    new ParameterizedTypeReference<EKOResponse<CustomerDetails>>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                EKOResponse<CustomerDetails> ekoResponse = response.getBody();
                CustomerDetails customerDetails = ekoResponse.getData();
                // Process the response
                logger.info("CustomerDetails Response: " + customerDetails);
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

    public EKOResponse<CustomerDetails> createRemitter(String remitterMobileNumber, MultiValueMap<String, String> requestBody) {
        EKOResponse<CustomerDetails> ekoResponse = new EKOResponse<>();
        HttpHeaders headers = ekoDataBuilder.getHeader();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        try {
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            String url = ENDPOINT_URL + "customers/mobile_number:" + remitterMobileNumber;
            ResponseEntity<EKOResponse<CustomerDetails>> response = restTemplate.exchange(url,
                    HttpMethod.PUT,
                    requestEntity,
                    new ParameterizedTypeReference<EKOResponse<CustomerDetails>>() {
                    }
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                ekoResponse = response.getBody();
                CustomerDetails customerDetails = ekoResponse.getData();
                // Process the response
                logger.info("CustomerDetails Response: " + customerDetails);
                return ekoResponse;
            } else {
                // Handle the error response
                logger.error("Error Response: " + response.getBody());
                if (response.getBody() != null) {
                    ekoResponse = response.getBody();
                }
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            logger.error("Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Error: ", e);
        }
        if(StringUtils.isEmpty(ekoResponse.getMessage())) {
            ekoResponse.setMessage("Error while creating Remitter");
        }
        ekoResponse.setError(true);
        return ekoResponse;
    }

    public EKOResponse<CustomerDetails> verifyRemitterOTP(MultiValueMap<String, String> requestBody, String otp) {
        HttpHeaders headers = ekoDataBuilder.getHeader();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        try {
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            String url = ENDPOINT_URL + "customers/verification/otp:" + otp;

            ResponseEntity<EKOResponse<CustomerDetails>> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    new ParameterizedTypeReference<EKOResponse<CustomerDetails>>() {
                    }
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                EKOResponse<CustomerDetails> ekoResponse = response.getBody();
                CustomerDetails customerDetails = ekoResponse.getData();
                // Process the response
                logger.info("CustomerDetails Response: " + customerDetails);
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
            logger.error("Error: ", e);
        }
        return null;
    }

    public EKOResponse<CustomerDetails> resendRemitterOTP(MultiValueMap<String, String> queryParams, String mobileNumber) {
        EKOResponse<CustomerDetails> ekoResponse = new EKOResponse<>();
        try {
            String url = ENDPOINT_URL + "customers/mobile_number:" + mobileNumber + "/otp";

            // Set the request headers
            HttpHeaders headers = ekoDataBuilder.getHeader();

            // Set the request body
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("initiator_id", "9962981729");
            requestBody.add("user_code", "20810200");

            // Create the HttpEntity with headers and body
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Send the request
            ResponseEntity<EKOResponse<CustomerDetails>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<EKOResponse<CustomerDetails>>() {
                    });

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ekoResponse = responseEntity.getBody();
                CustomerDetails customerDetails = ekoResponse.getData();
                // Process the response
                logger.info("CustomerDetails Response: " + customerDetails);
                return ekoResponse;
            } else {
                // Handle the error response
                logger.error("Error Response: " + responseEntity.getBody());
                if (responseEntity.getBody() != null) {
                    ekoResponse = responseEntity.getBody();
                }
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            logger.error("Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Error: ", e);
        }
        if(StringUtils.isEmpty(ekoResponse.getMessage())) {
            ekoResponse.setMessage("Error while resending verification OTP");
        }
        ekoResponse.setError(true);
        return ekoResponse;
    }
}