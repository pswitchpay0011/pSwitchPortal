package net.in.pSwitch.authentication;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import net.in.pSwitch.dto.UserRegistrationDTO;
import net.in.pSwitch.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@Component
@Slf4j
public class LoginCheck implements HandlerInterceptor {
    public static final String COOKIE_NAME = "PSwitchToken";

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UtilService utilService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // if(request.getCookies()==null)
        // return true;
        boolean isWeb = true;
        try {

            String token = null;
            if (request.getCookies() == null) {
                token = extractJwtFromRequest(request);
            } else {
                token = Arrays.stream(request.getCookies())
                        .filter(cookie -> cookie.getName().equals(LoginCheck.COOKIE_NAME)).findFirst()
                        .map(Cookie::getValue)
                        .orElse("dummy");
            }
            if (request.getRequestURI().startsWith("/api/")) {
                isWeb = false;

                if(request.getParameter("isWeb")!=null && request.getParameter("isWeb").equals("1")){
                    isWeb=true;
                }

            }
            // log.info("token : {}", token);
            log.info("URL : {}", request.getRequestURI()+" isWeb: "+isWeb);
            // Set the user information verified in the request so that login information
            // can be easily retrieved and used like session.id in the View
            if (StringUtils.isEmpty(token)) {
                token = "dummy";
            } else {
                token = utilService.decodedData(token);
            }

            Map<String, Object> userData = jwtService.verify(token);
            LoginUserInfo user = LoginUserInfo.builder().id((Integer) userData.get("id"))
                    .name((String) userData.get("fullName")).build();
            if (!CollectionUtils.isEmpty(userData)) {
                Map role = (Map) userData.get("userRole");
                UserDetails userDetails = new User((String) userData.get("email"), "",
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role.get("roleCode"))));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // // After setting the Authentication in the context, we specify
                // // that the current user is authenticated. So it passes the
                // // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                // Accessible by login.id in view
                request.setAttribute("login", user);
            }
        } catch (ExpiredJwtException ex) {
            log.error("Token expired");

            if (isWeb) {
                ModelAndView mav = new ModelAndView("redirect:/login");
                String errorMessage = null;
//                mav.addObject("user", new UserRegistrationDTO());
//                mav.addObject("loginTab", "show active");
//                mav.addObject("resetPasswordTab", "");
//                mav.addObject("registerTab", "");
//                mav.addObject("errorMsg", errorMessage);
//                mav.addObject("return_url", request.getRequestURI());

                throw new ModelAndViewDefiningException(mav);
            } else {
                response.reset();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                return false;
            }
        } catch (JwtException ex) {
            log.error("Invalid token");
            if (isWeb) {
                ModelAndView mav = new ModelAndView("redirect:/login");
//                String errorMessage = null;
//
//                mav.addObject("user", new UserRegistrationDTO());
//                mav.addObject("loginTab", "show active");
//                mav.addObject("resetPasswordTab", "");
//                mav.addObject("registerTab", "");
//                mav.addObject("errorMsg", errorMessage);
//                mav.addObject("return_url", request.getRequestURI());

                throw new ModelAndViewDefiningException(mav);
            } else {
                response.reset();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return false;
            }
        } catch (Exception ex) {
            log.error("Error: {}", ex.getLocalizedMessage());
            if (isWeb) {
                ModelAndView mav = new ModelAndView("redirect:/login");
//                String errorMessage = null;
//                mav.addObject("user", new UserRegistrationDTO());
//                mav.addObject("loginTab", "show active");
//                mav.addObject("resetPasswordTab", "");
//                mav.addObject("registerTab", "");
//                mav.addObject("errorMsg", errorMessage);
//                mav.addObject("return_url", request.getRequestURI());

                throw new ModelAndViewDefiningException(mav);
            } else {
                response.reset();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
                return false;
            }
        }

        return true;
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
