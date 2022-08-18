package com.example.apinetflix.Service;

import com.example.apinetflix.Accessor.UserAccessor;
import com.example.apinetflix.Accessor.Model.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor

public  class RolebBasedAuthenticationManager  implements AuthenticationManager, RolebBasedAuthenticationManagerr {
    private final UserAccessor userAccessor;


    @Override
    public Authentication authentication(Authentication authentication) throws AuthenticationException{
        String email=(String) authentication.getPrincipal();
        List<GrantedAuthority> allowedRole = new ArrayList<>(authentication.getAuthorities());
        UserDTO userDTO= userAccessor.getUserByEmail(email);
        if(userDTO!=null){
            for(int i=0;i<allowedRole.size();i++){
                if(allowedRole.get(i).equals(userDTO.getRole().toString())){
                    return new UsernamePasswordAuthenticationToken(new User(email, userDTO.getPassword(), allowedRole),"",allowedRole);
                }
            }
        }
        throw new BadCredentialsException("Role not allowed");
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }
}

