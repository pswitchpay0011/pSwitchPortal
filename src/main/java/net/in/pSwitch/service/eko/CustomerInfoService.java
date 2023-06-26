package net.in.pSwitch.service.eko;

import net.in.pSwitch.eko.CustomerDetails;
import net.in.pSwitch.eko.request.CreateCustomer;
import net.in.pSwitch.eko.request.CustomerAddress;
import net.in.pSwitch.eko.response.EKOResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;

@Service
public class CustomerInfoService {

    @Value("${eko.endpoint}")
    private String ENDPOINT_URL;
    @Value("${eko.developer.key}")
    private String DEVELOPER_KEY;
    @Value("${eko.secret.key}")
    private String SECRET_KEY;
    @Value("${eko.secret.timestamp}")
    private String SECRET_KEY_TIMESTAMP;

    private HttpHeaders headers;

//    private static final String ENDPOINT_URL = "https://staging.eko.in:25004/ekoapi/v2/customers/mobile_number:8870778821";
//    private static final String DEVELOPER_KEY = "becbbce45f79c6f5109f848acd540567";
//    private static final String SECRET_KEY = "MC6dKW278tBef+AuqL/5rW2K3WgOegF0ZHLW/FriZQw=";
//    private static final String SECRET_KEY_TIMESTAMP = "1516705204593";

    @PostConstruct
    private void init() {
        headers = new HttpHeaders();
        headers.set("developer_key", DEVELOPER_KEY);
        headers.set("secret-key", SECRET_KEY);
        headers.set("secret-key-timestamp", SECRET_KEY_TIMESTAMP);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    public void getCustomerInfo(String initiator_id, String user_code) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("initiator_id", initiator_id);
        queryParams.add("user_code", user_code);

        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(ENDPOINT_URL)
                    .queryParams(queryParams)
                    .build()
                    .toUri();

            RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

            ResponseEntity<CustomerDetails> response = restTemplate.exchange(requestEntity, CustomerDetails.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                CustomerDetails customerDetails = response.getBody();
                // Process the response
                System.out.println("Response: " + customerDetails);
            } else {
                // Handle the error response
                System.out.println("Error Response: " + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            System.out.println("Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }

    public void updateCustomerInfo(CreateCustomer customer, CustomerAddress address) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("developer_key", DEVELOPER_KEY);
        headers.set("secret-key", SECRET_KEY);
        headers.set("secret-key-timestamp", SECRET_KEY_TIMESTAMP);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("initiator_id", customer.getInitiatorId());
        requestBody.add("name", customer.getName());
        requestBody.add("user_code", customer.getUserCode());
        requestBody.add("dob", customer.getDob());
        requestBody.add("residence_address", address.toString());
        requestBody.add("pipe", customer.getPipe());

        try {
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<EKOResponse> response = restTemplate.exchange(
                    ENDPOINT_URL,
                    HttpMethod.PUT,
                    requestEntity,
                    EKOResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                EKOResponse ekoResponse = response.getBody();
                CustomerDetails customerDetails = (CustomerDetails) ekoResponse.getData();
                // Process the response
                System.out.println("Response: " + ekoResponse);
            } else {
                // Handle the error response
                System.out.println("Error Response: " + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            System.out.println("Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }

    public void verifyCustomerOTP() {
        // Create a custom HttpClient
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("developer_key", DEVELOPER_KEY);
        headers.set("secret-key", SECRET_KEY);
        headers.set("secret-key-timestamp", SECRET_KEY_TIMESTAMP);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("initiator_id", "9962981729d");
        requestBody.add("id_type", "mobile_number");
        requestBody.add("id", "8870778821");
        requestBody.add("otp_ref_id", "d3e00033-ebd1-5492-a631-53f0dbf00d69");
        requestBody.add("user_code", "20810200");
        requestBody.add("pipe", "9");

        try {
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    ENDPOINT_URL,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                // Process the response
                System.out.println("Response: " + responseBody);
            } else {
                // Handle the error response
                System.out.println("Error Response: " + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors
            System.out.println("Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }
}