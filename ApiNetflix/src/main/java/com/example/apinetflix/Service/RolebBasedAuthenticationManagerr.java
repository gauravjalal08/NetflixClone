package com.example.apinetflix.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface RolebBasedAuthenticationManagerr {
    Authentication authentication(Authentication authentication) throws AuthenticationException;
}
