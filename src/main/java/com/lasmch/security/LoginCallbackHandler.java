package com.lasmch.security;

import com.lasmch.adm.usermgmt.dao.UserMgmtDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginCallbackHandler {


  @Autowired
  UserMgmtDao usermgmtdao;

  public void update(UserPrincipal userPrincipal) {

  }
}
