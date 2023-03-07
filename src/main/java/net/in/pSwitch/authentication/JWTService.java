package net.in.pSwitch.authentication;

import net.in.pSwitch.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {

    @Value("${pSwitch.secret.key}")
    private String secret;

    @Value("${pSwitch.refreshToken.timeOutInMinutes}")
    private Integer refreshTokenTimeOut;

    @Autowired
    private UtilService utilService;

    public String token(Map<String, Object> body, Optional<LocalDateTime> expired) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);

        Key key = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());

        JwtBuilder builder = Jwts.builder().setClaims(body)
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusDays(1)))
                .signWith(SignatureAlgorithm.HS512, key);
        expired.ifPresent(exp -> {
            builder.setExpiration(Timestamp.valueOf(exp));
        });

        return utilService.encodedData(builder.compact());
    }

    public String doGenerateRefreshToken(Map<String, Object> claims, Optional<LocalDateTime> expired) {
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (refreshTokenTimeOut * 60000)))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Map<String, Object> verify(String token) {
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(token)
                .getBody();

        return new HashMap<>(claims);
    }

    public String token(Map<String, Object> body) {
        return token(body, Optional.of(LocalDateTime.now().plusDays(1)));
    }
}
