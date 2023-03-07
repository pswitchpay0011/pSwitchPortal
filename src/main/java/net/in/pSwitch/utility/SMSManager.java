package net.in.pSwitch.utility;

import net.in.pSwitch.controller.UserAccountController;
import net.in.pSwitch.service.UtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class SMSManager {
    private Logger LOGGER = LoggerFactory.getLogger(SMSManager.class);
    final static String url = "http://msg.mtalkz.com/V2/http-api-post.php";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilService utilService;

    public String sendOTP(String mobileNumber){
        Map<String, Object> request = new HashMap<>();
        request.put("apikey", "BxaypHaZNo6XVUYA");
        String otp = utilService.generateOTP();
        request.put("message", "Please Use the OTP "+otp+" for verification PSWITCH");
        request.put("number", mobileNumber);
        request.put("senderid", "PSWTCH");

        String result = restTemplate.postForObject(url, request, String.class);
        org.springframework.boot.json.JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> map = springParser.parseMap(result);
        LOGGER.info("OTP result: " + result);
        if (map.containsKey("status") && String.valueOf(map.get("status")).equals("OK")) {
            return otp;
        }
        throw new RuntimeException("Unable to send the OTP");
    }
}
