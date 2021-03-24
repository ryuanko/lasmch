package com.lasmch.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

  @Autowired(required = false)
  UserDetailServiceImpl userDetailService;


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    log.debug("Login => username:{}, password:{}, authentication: {}", username, password, authentication);

    UserDetails userDetails = userDetailService.loadUserByUsername(username,password);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
