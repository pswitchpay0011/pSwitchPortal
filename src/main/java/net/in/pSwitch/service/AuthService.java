package net.in.pSwitch.service;

import net.in.pSwitch.authentication.JWTService;
import net.in.pSwitch.controller.ApplicationController;
import net.in.pSwitch.model.UserInfo;
import net.in.pSwitch.model.request.AuthRequest;
import net.in.pSwitch.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {
    Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private JWTService jwtService;

    @Value("${pSwitch.token.timeOutInMinutes}")
    private Integer tokenTimeout;

    public Map authenticate(AuthRequest authenticationRequest) throws Exception {

        Map<String, Object> user = new HashMap<>();
        Authentication authentication = null;
        try {
            Objects.requireNonNull(authenticationRequest.getUsername());
            Objects.requireNonNull(authenticationRequest.getPassword());
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (NullPointerException e) {
            throw new RuntimeException("Invalid user Credentials. Username password can't be blank");
        }

        if(authentication.isAuthenticated()) {
            UserInfo userInfo = userInfoRepository.findUserForLogin(authenticationRequest.getUsername());
            user.put("id", userInfo.getUserId());
            user.put("fullName", userInfo.getFullName());
            user.put("pSwitchId", userInfo.getUserPSwitchId());
            user.put("email", userInfo.getUsername());
            user.put("userRole", userInfo.getRoles());
            final String token = jwtService.token(user, Optional.of(LocalDateTime.now().plusMinutes(tokenTimeout)));
            user.remove("userRole");
            user.put("roleCode", userInfo.getRoles().getRoleCode());
            user.put("role", userInfo.getRoles().getRoleName());
            user.put("isKycCompleted", userInfo.getKycCompleted());
            user.put("mobileNumber", userInfo.getMobileNumber());
            user.put("token", token);
        }
        return user;
    }
}
