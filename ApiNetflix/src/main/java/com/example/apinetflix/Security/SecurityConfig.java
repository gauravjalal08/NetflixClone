package com.example.apinetflix.Security;

import com.example.apinetflix.Accessor.UserAccessor;
import com.example.apinetflix.Service.RolebBasedAuthenticationManager;
import com.example.apinetflix.Service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 @Autowired
    private UserSecurityService userSecurityService;
 @Autowired
 private UserAccessor userAccessor;


protected void  configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userSecurityService);
    }

 protected void configure(HttpSecurity http) throws Exception{ //http= allows to define end point which is public and private
        //endpoint need security, endpoint don't need security
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthorizationFilter(new RolebBasedAuthenticationManager(userAccessor)))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.httpBasic().disable();



    }
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration= new CorsConfiguration();
        corsConfiguration.addAllowedHeader("Authorization");
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT","OPTION"));
        corsConfiguration.addAllowedOrigin("");
       source.registerCorsConfiguration("/**",corsConfiguration);
       return source;
        }
    }

