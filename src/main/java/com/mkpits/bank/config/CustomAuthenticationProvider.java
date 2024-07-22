package com.mkpits.bank.config;

import com.mkpits.bank.model.*;
import com.mkpits.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private EmployeeCredentialRepository employeeCredentialRepository;

    @Autowired
    private AdminCredentialRepository adminCredentialRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<UserCredential> userCredentialOptional = userCredentialRepository.findByUserName(username);
        if (userCredentialOptional.isPresent() && userCredentialOptional.get().getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        }

        Optional<EmployeeCredential> employeeCredentialOptional = employeeCredentialRepository.findByUserName(username);
        if (employeeCredentialOptional.isPresent() && employeeCredentialOptional.get().getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE")));
        }

        Optional<AdminCredential> adminCredentialOptional = adminCredentialRepository.findByUserName(username);
        if (adminCredentialOptional.isPresent() && adminCredentialOptional.get().getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        throw new BadCredentialsException("Invalid credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}