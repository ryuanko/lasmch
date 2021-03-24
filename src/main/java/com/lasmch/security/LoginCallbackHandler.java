package com.lasmch.security;

import com.lasmch.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginCallbackHandler {


  @Autowired
  UserDao userDao;

  public void update(UserPrincipal userPrincipal) {

  }
}
