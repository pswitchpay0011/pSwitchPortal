package net.in.pSwitch.authentication;

import net.in.pSwitch.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginUserResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private UtilService utilService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Map<String, Object> resolved = new HashMap<>();
        HttpServletRequest req = (HttpServletRequest) webRequest.getNativeRequest();
        // If there is a token in the cookie, take it out, verify it, and return the login information
        Arrays.stream(req.getCookies()).filter(cookie -> cookie.getName().equals(CustomJwtAuthenticationFilter.COOKIE_TOKEN))
                .map(Cookie::getValue).findFirst().ifPresent(token -> {
                    token = utilService.decodedData(token);
                    Map<String, Object> info = jwtService.verify(token);

                    // @LoginUser String id, @LoginUser String name
                    if (parameter.getParameterType().isAssignableFrom(String.class)) {
                        resolved.put("resolved", info.get(parameter.getParameterName()));
                    }
                    // @LoginUser User user
                    else if (parameter.getParameterType().isAssignableFrom(LoginUserInfo.class)) {
                        LoginUserInfo user = LoginUserInfo.builder().id((Integer) info.get("id")).name((String) info.get("fullName")).build();
                        resolved.put("resolved", user);
                    }
                });

        return resolved.get("resolved");
    }
}
