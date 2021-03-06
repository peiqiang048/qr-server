package com.cnc.qr.config;

import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.security.jwt.JWTConfigurer;
import com.cnc.qr.security.jwt.TokenProvider;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;

    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder,
        UserDetailsService userDetailsService,
        TokenProvider tokenProvider, CorsFilter corsFilter, SecurityProblemSupport problemSupport) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
    }

    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf()
            .disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
            .and()
            .headers()
            .frameOptions()
            .disable()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/csmb/weChatAliPayBackUrl").permitAll()
            .antMatchers("/api/csmb/sbPaymentCallBack").permitAll()
            .antMatchers("/api/csmb/getPrepaymentPrintData").permitAll()
            .antMatchers("/api/csmb/getToken").permitAll()
            .antMatchers("/api/csmb/**").hasAuthority(AuthoritiesConstants.MOBILE)
            .antMatchers("/api/csdv/weChatAliPayBackUrl").permitAll()
            .antMatchers("/api/csdv/sbPaymentCallBack").permitAll()
            .antMatchers("/api/csdv/**").hasAuthority(AuthoritiesConstants.MOBILE)
            .antMatchers("/api/stpd/login").permitAll()
            .antMatchers("/api/stpd/storeIdVerification").permitAll()
            .antMatchers("/api/stpd/getUserAndLanguageList").permitAll()
            .antMatchers("/api/stpd/getPrintOrderList").permitAll()
            .antMatchers("/api/stpd/changePrintStatus").permitAll()
            .antMatchers("/api/stpd/**").hasAuthority(AuthoritiesConstants.ORDER)
            .antMatchers("/api/stmb/login").permitAll()
            .antMatchers("/api/stmb/getUserList").permitAll()
            .antMatchers("/api/stmb/**").hasAuthority(AuthoritiesConstants.ORDER)
            .antMatchers("/api/pc/login").permitAll()
            .antMatchers("/api/pc/**").hasAuthority(AuthoritiesConstants.ITEM_MAINTENANCE)
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .and()
            .httpBasic()
            .and()
            .apply(securityConfigurerAdapter());
        // @formatter:on
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
