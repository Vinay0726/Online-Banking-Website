package com.mkpits.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/user/", "/css/**", "/js/**","/images/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/index")
                                .loginProcessingUrl("/perform_login")
                                .successHandler((request, response, authentication) -> {
                                    String role = authentication.getAuthorities().iterator().next().getAuthority();
                                    if ("ROLE_USER".equals(role)) {
                                        response.sendRedirect("/user/dashboard");
                                    } else if ("ROLE_EMPLOYEE".equals(role)) {
                                        response.sendRedirect("/employee/dashboard");
                                    } else if ("ROLE_ADMIN".equals(role)) {
                                        response.sendRedirect("/admin/dashboard");
                                    }
                                })
                                .permitAll()
                )
                .logout(LogoutConfigurer::permitAll
                )
                .authenticationProvider(customAuthenticationProvider);

        return http.build();
    }
}