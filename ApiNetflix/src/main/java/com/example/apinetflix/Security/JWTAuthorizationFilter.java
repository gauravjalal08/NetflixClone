package com.example.apinetflix.Security;

import com.example.apinetflix.Accessor.AuthAccessor;
import com.example.apinetflix.Accessor.Model.AuthDTO;
import com.example.apinetflix.Accessor.UserAccessor;
import com.example.apinetflix.Accessor.Model.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import javax.servlet.FilterChain;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    private AuthAccessor authAccessor;


    private UserAccessor userAccessor;
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager){

        super(authenticationManager);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        ServletContext servletContext = request.getSession().getServletContext();
       WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
       if (authAccessor == null) {
           authAccessor = (AuthAccessor) applicationContext.getBean("authAccessor");
     }
        if (userAccessor == null) {
           userAccessor = (UserAccessor) applicationContext.getBean("userAccessor");
       }
        try{
            UsernamePasswordAuthenticationToken authentication= getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request,response);
        }
        catch (MalformedJwtException | BadCredentialsException exception){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        } catch (ServletException e) {   ///exce
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e); //exce
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        final String tokenPrefix = "Bearer";
        if (authorizationHeader != null) {
            if (authorizationHeader.startsWith(tokenPrefix)) {
                String token = authorizationHeader.replace(tokenPrefix, "");
                Claims claims = Jwts.parser()
                        .setSigningKey(SecurityConstants.SECRET_KEY.getBytes())
                        .parseClaimsJws(token)
                        .getBody();
                Date expirationTime = claims.getExpiration();
                if(expirationTime.after(new Date(System.currentTimeMillis()))){
                    AuthDTO authDTO = authAccessor.getAuthByToken(token);
                    if(authDTO!=null){
                        UserDTO userDTO = userAccessor.getUserByEmail(claims.getSubject());
                        if(userDTO!=null){
                            return new UsernamePasswordAuthenticationToken(userDTO, userDTO.getPassword(),
                                    Arrays.asList(new SimpleGrantedAuthority(userDTO.getRole().name())));
                    }
                }
            }

        }

    }
        return new UsernamePasswordAuthenticationToken(null,null,Arrays.asList(new SimpleGrantedAuthority(Roles.Anonymous)));
}
}
