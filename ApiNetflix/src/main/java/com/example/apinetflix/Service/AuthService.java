package com.example.apinetflix.Service;

import com.example.apinetflix.Accessor.AuthAccessor;
import com.example.apinetflix.Accessor.UserAccessor;
import com.example.apinetflix.Accessor.Model.UserDTO;
import com.example.apinetflix.Exception.InvalidCredentialsException;
import com.example.apinetflix.Security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component //to create beans
public class AuthService { //intreacting with authAccessor & userAccessor
    @Autowired
    private UserAccessor userAccessor;  // TO GET EMAIL(FETCHING USER)
    @Autowired
    private AuthAccessor authAccessor; // TO STORE TOKEN
    // BOTH ARE NEEDED IN USERSERVICE

//it will return JWT token if email and password matches
    public  String login(final String email, final String password) {
        UserDTO userDTO = userAccessor.getUserByEmail(email); //FETCHING FROM USERDTO
       // System.out.println("userDto =" + userDTO.getPassword() +" my password =" + password);
        if (userDTO != null && userDTO.getPassword().equals(password)) { // if this is true generate token
            String token = Jwts.builder() //generating token
                    .setSubject(email) //identity of person for whom the token is issued
                    .setAudience(userDTO.getRole().name()) //tells the role
                    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_KEY.getBytes())
                    .compact();
            authAccessor.StoreToken(userDTO.getUserId(), token);//Storing Token
            return token;
        }

        throw new InvalidCredentialsException("Please check your password & email");
    }
    public void logout(final String token){
       authAccessor.deleteAuthByToken(token);
    }

}
