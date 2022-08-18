package com.example.apinetflix.Service;

import com.example.apinetflix.Accessor.UserAccessor;
import com.example.apinetflix.Accessor.Model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UserAccessor userAccessor;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO= userAccessor.getUserByEmail(username);
                if(userDTO!=null){
                    return new User(userDTO.getEmail(),userDTO.getPassword(),
                            Arrays.asList(new SimpleGrantedAuthority(userDTO.getRole().name())));
                }
                throw new UsernameNotFoundException("User with this email" +username+ "not found!");
    }
}
