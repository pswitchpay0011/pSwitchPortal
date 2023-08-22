package net.in.pSwitch.service.eko;

import net.in.pSwitch.eko.CustomerDetails;
import net.in.pSwitch.eko.EKODataBuilder;
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
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class EKOOnBoardService {

    Logger logger = LoggerFactory.getLogger(EKOOnBoardService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EKODataBuilder ekoDataBuilder;

    @Value("${eko.endpoint.v1}")
    private String ENDPOINT_URL_V1;

    public EKOResponse<CustomerDetails> onboardUser(MultiValueMap<String, String> requestBody) {
        EKOResponse<CustomerDetails> ekoResponse = new EKOResponse<>();
        String url = ENDPOINT_URL_V1 + "user/onboard";
        HttpHeaders headers = ekoDataBuilder.getHeader();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        try {
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
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
}