package net.in.pSwitch.eko;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.util.Date;

@Component
public class EKODataBuilder {

    private Logger LOGGER = LoggerFactory.getLogger(EKODataBuilder.class);
    public static final String HMAC_SHA256 = "HmacSHA256";

    @Value("${eko.developer.key}")
    private String DEVELOPER_KEY;
    @Value("${eko.initiator.id}")
    private String initiatorId;
//    @Value("${eko.user.code}")
//    private String userCode;

    public EKOSecretKey getSecretKey() {
        EKOSecretKey secretKey = new EKOSecretKey();
        try {

            String secret_key = "", secret_key_timestamp = "";
            // Initializing key in some variable. You will receive this key from Eko via email
            String key = "d2fe1d99-6298-4af2-8cc5-d97dcf46df30";
            String encodedKey = Base64.encodeBase64String(key.getBytes());
            // Encode it using base64

            // Get secret_key_timestamp: current timestamp in milliseconds since UNIX epoch as STRING
            // Check out https://currentmillis.com to understand the timestamp format
            Date date = new Date();
            secret_key_timestamp = Long.toString(date.getTime());

            // Computes the signature by hashing the salt with the encoded key
            Mac sha256_HMAC;

            sha256_HMAC = Mac.getInstance(HMAC_SHA256);

            SecretKeySpec signature = new SecretKeySpec(encodedKey.getBytes(), HMAC_SHA256);
            try {
                sha256_HMAC.init(signature);
            } catch (InvalidKeyException e) {
                LOGGER.error("Error: ", e);
            }

            // Encode it using base64 to get secret-key
            secret_key = Base64.encodeBase64String(sha256_HMAC.doFinal(secret_key_timestamp.getBytes())).trim();

            secretKey.setSecretKey(secret_key);
            secretKey.setSecretKeyTimestamp(secret_key_timestamp);

            System.out.println("secret_key: "+secret_key);
            System.out.println("secret_key_timestamp: "+secret_key_timestamp);

        } catch (Exception e) {
            LOGGER.error("Error: ", e);
        }
     return secretKey;
    }

    public HttpHeaders getHeader(){
        EKOSecretKey secretKey  = getSecretKey();
        HttpHeaders headers = new HttpHeaders();
        headers.set("developer_key", DEVELOPER_KEY);
        headers.set("secret-key", secretKey.getSecretKey());
        headers.set("secret-key-timestamp", secretKey.getSecretKeyTimestamp());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return headers;
    }

    public String getInitiatorId() {
        return initiatorId;
    }

//    public String getUserCode() {
//        return userCode;
//    }
}
