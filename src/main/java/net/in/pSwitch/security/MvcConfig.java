package net.in.pSwitch.security;

import java.util.List;
import java.util.Locale;

import net.in.pSwitch.authentication.CustomJwtAuthenticationFilter;
import net.in.pSwitch.authentication.LoginCheck;
import net.in.pSwitch.authentication.LoginUserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableWebSecurity
public class MvcConfig implements WebMvcConfigurer {

	@Autowired
	private LoginUserResolver loginUserResolver;

	@Autowired
    LoginCheck loginCheck;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheck).addPathPatterns("", "/**").excludePathPatterns("/login", "/error", "/userLogout","/loginUser","/register", "/api/authenticate/**", "/api/authenticate/**", "/api/token/**", "/api/healthcheck/**", "/files/**", "/account/**", "/css/**","/global_assets/**","/assets/**", "/js/**", "/images/**", "/actuator/**");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginUserResolver);
	}

}
