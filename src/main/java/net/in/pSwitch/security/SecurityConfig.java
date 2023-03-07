package net.in.pSwitch.security;

import net.in.pSwitch.authentication.CustomJwtAuthenticationFilter;
import net.in.pSwitch.authentication.JwtAuthenticationEntryPoint;
import net.in.pSwitch.authentication.LoginCheck;
import net.in.pSwitch.authentication.LoginUserResolver;
import net.in.pSwitch.error.CustomLoginFailureHandler;
import net.in.pSwitch.error.CustomLoginSuccessHandler;
import net.in.pSwitch.service.UserDetailsServiceImpl;
import net.in.pSwitch.service.UtilServiceImpl;
import net.in.pSwitch.utility.StringLiteral;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomLoginFailureHandler loginFailureHandler;

    @Autowired
    private CustomLoginSuccessHandler loginSuccessHandler;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    @Autowired
    private LoginUserResolver loginUserResolver;

//    @Autowired
//    private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private MessageSource messages;

    @Autowired
    private UtilServiceImpl utilService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
//		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setMessageSource(messages);
        daoAuthenticationProvider.setPasswordEncoder(new PasswordEncoder() {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return utilService.encodedData(String.valueOf(rawPassword)).equals(encodedPassword);
//                return encoder.matches(rawPassword, encodedPassword);
            }

            @Override
            public String encode(CharSequence rawPassword) {
                return encoder.encode(rawPassword);
            }
        });
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder().encode("pass"))
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN");
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login", "/error","/userLogout","/loginUser","/register", "/api/authenticate/**","/api/token/**", "/api/healthcheck/**", "/account/**", "/files/**", "/css/**","/global_assets/**","/assets/**", "/js/**", "/images/**", "/actuator/**");
    }
    @Autowired
    LoginCheck loginCheck;

//    @Bean
//    public AuthenticationEntryPoint unauthorizedEntryPoint() {
//        return (request, response, authException) -> response.sendRedirect("/login");
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/admin/**").hasRole(StringLiteral.ROLE_CODE_ADMIN)
//                .antMatchers("/service/**").authenticated()
//                .antMatchers("/retailer/**").hasRole(StringLiteral.ROLE_CODE_RETAILER)
//                .antMatchers("/officeAdmin/**").hasRole(StringLiteral.ROLE_CODE_OFFICE_ADMIN)
//                .antMatchers("/distributor/**").hasRole(StringLiteral.ROLE_CODE_DISTRIBUTOR)
//                .antMatchers("/superDistributor/**").hasRole(StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR)
//                .antMatchers("/businessAssociate/**").hasRole(StringLiteral.ROLE_CODE_BUSINESS_ASSOCIATE)
//                .antMatchers("/mangerBusinessAssociate/**").hasRole(StringLiteral.ROLE_CODE_MANAGER_BUSINESS_ASSOCIATE)
//                .antMatchers("/finance/**").hasRole(StringLiteral.ROLE_CODE_FINANCE)
//                .antMatchers("/managerFinance/**").hasRole(StringLiteral.ROLE_CODE_MANAGER_FINANCE)
//                .antMatchers("/login", "/loginUser","/register", "/", "/index", "/css/**", "/js/**", "/images/**",
//                        "/registration/**", "/verify/**", "/resetPassword/**", "/states/**", "/cities/**",
//                        "/account/**", "/changePassword/**", "/updatePassword/**", "/files/**", "/api/**")
                .antMatchers("/*/**")
                .permitAll().anyRequest().authenticated()
//                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                        .
//                and().addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


//        http.authorizeRequests().antMatchers("/category/?**", "/questions/?**").hasRole("ADMIN")
//                .antMatchers("/css/**", "/js/**", "/images/**", "/registration/**", "/verify/**", "/resetPassword/**", "/states/**", "/cities/**",
//                        "/account/**", "/changePassword/**", "/updatePassword/**", "/files/**", "/api/**")
//                .permitAll().antMatchers("/user/**").authenticated()
//                .antMatchers("/admin/**").hasRole(StringLiteral.ROLE_CODE_ADMIN)
//                .antMatchers("/service/**").authenticated()
//                .antMatchers("/retailer/**").hasRole(StringLiteral.ROLE_CODE_RETAILER)
//                .antMatchers("/officeAdmin/**").hasRole(StringLiteral.ROLE_CODE_OFFICE_ADMIN)
//                .antMatchers("/distributor/**").hasRole(StringLiteral.ROLE_CODE_DISTRIBUTOR)
//                .antMatchers("/superDistributor/**").hasRole(StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR)
//                .antMatchers("/businessAssociate/**").hasRole(StringLiteral.ROLE_CODE_BUSINESS_ASSOCIATE)
//                .antMatchers("/mangerBusinessAssociate/**").hasRole(StringLiteral.ROLE_CODE_MANAGER_BUSINESS_ASSOCIATE)
//                .antMatchers("/finance/**").hasRole(StringLiteral.ROLE_CODE_FINANCE)
//                .antMatchers("/managerFinance/**").hasRole(StringLiteral.ROLE_CODE_MANAGER_FINANCE)
//                .antMatchers("/api/**").permitAll()
//                .antMatchers("/loginUser/**").permitAll()
//                .antMatchers("/register/**")
//                .permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login")
//                .failureUrl("/login?error").defaultSuccessUrl("/").usernameParameter("username")
//                .failureHandler(loginFailureHandler).successHandler(loginSuccessHandler).permitAll().and().rememberMe()
//                .key("rem-me-key-pswitch").rememberMeParameter("remember") // it is name of checkbox at login page
//                .rememberMeCookieName("rememberlogin") // it is name of the cookie
//                .tokenValiditySeconds(1800).and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login").invalidateHttpSession(true).permitAll().and().exceptionHandling();
//                .accessDeniedPage("/accessDenied.html");


        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.authorizeRequests()
//                .antMatchers("/category/?**", "/questions/?**").hasRole("ADMIN")
//                .antMatchers("/api/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout().permitAll();
//                .and();
        // .oauth2Login()
//                .loginPage("/login");
    }
}
