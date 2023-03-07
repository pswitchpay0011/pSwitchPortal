package net.in.pSwitch.authentication;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//@Component
//@Slf4j
public class CustomJwtAuthenticationFilter{
//        extends OncePerRequestFilter {
//
    public static final String COOKIE_TOKEN = "PSwitchToken";
    public static final String COOKIE_REFRESH_TOKEN = "PSwitchRefreshToken";
//
//    @Autowired
//    private JWTService jwtService;
//
//    private List ignoreList = Arrays.asList("/css/**", "/js/**", "/images/**");
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//
//
//        if(request.getRequestURI().equals("/") || request.getRequestURI().equals("/index")
//                ||request.getRequestURI().startsWith("/login")
//                ||request.getRequestURI().startsWith("/css/")
//                || request.getRequestURI().startsWith("/js/")
//                || request.getRequestURI().startsWith("/images/")){
//            return;
//        }
//        log.info(request.getRequestURI());
//
//        try {
//            // JWT Token is in the form "Bearer token". Remove Bearer word and
//            // get  only the Token
//            String jwtToken = extractJwtFromRequest(request);
//            if (jwtToken == null) {
//                jwtToken = Arrays.stream(request.getCookies())
//                        .filter(cookie -> cookie.getName().equals(COOKIE_TOKEN)).findFirst().map(Cookie::getValue)
//                        .orElse(null);
//            }
////            user.put("id", userInfo.getUserId());
////            user.put("fullName", userInfo.getFullName());
////            user.put("fullName", userInfo.getUserPSwitchId());
////            user.put("email", userInfo.getUsername());
////            user.put("userRole", userInfo.getRoles());
//
//            if(!StringUtils.isEmpty(jwtToken)) {
//                Map<String, Object> userData = jwtService.verify(jwtToken);
//                if (!CollectionUtils.isEmpty(userData)) {
//                    Map role = (Map) userData.get("userRole");
//                    UserDetails userDetails = new User((String) userData.get("email"), "",
//                            Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role.get("roleCode"))));
//
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities());
//                    // After setting the Authentication in the context, we specify
//                    // that the current user is authenticated. So it passes the
//                    // Spring Security Configurations successfully.
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                } else {
//                    log.error("Cannot set the Security Context");
//                }
//            }
//        } catch (ExpiredJwtException ex) {
//            log.error("Token expired");
//
////            ModelAndView mav = new ModelAndView("loginPage/signIn");
////            mav.addObject("return_url", request.getRequestURI());
//
////            throw new ModelAndViewDefiningException(mav);
//            response.sendRedirect("/login");
//        } catch (JwtException ex) {
//            log.error("Invalid token");
////            ModelAndView mav = new ModelAndView("loginPage/signIn");
////            throw new ModelAndViewDefiningException(mav);
//            response.sendRedirect("/login");
//        }
////            String isRefreshToken = request.getHeader("isRefreshToken");
////            if(StringUtils.isEmpty(isRefreshToken))
////            {
////                isRefreshToken = Arrays.stream(request.getCookies())
////                        .filter(cookie -> cookie.getName().equals(COOKIE_REFRESH_TOKEN)).findFirst().map(Cookie::getValue)
////                        .orElse("dummy");
////            }
////            String requestURL = request.getRequestURL().toString();
////            // allow for Refresh Token creation if following conditions are true.
////            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
////                allowForRefreshToken(ex, request);
////            } else
////                request.setAttribute("exception", ex);
////        }
//        catch (BadCredentialsException ex) {
//            request.setAttribute("exception", ex);
//        }
//        chain.doFilter(request, response);
//    }
//
//    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
//
//        // create a UsernamePasswordAuthenticationToken with null values.
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                null, null, null);
//        // After setting the Authentication in the context, we specify
//        // that the current user is authenticated. So it passes the
//        // Spring Security Configurations successfully.
//        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//        // Set the claims so that in controller we will be using it to create
//        // new JWT
//        request.setAttribute("claims", ex.getClaims());
//
//    }
//
//    private String extractJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7, bearerToken.length());
//        }
//        return null;
//    }
//
}