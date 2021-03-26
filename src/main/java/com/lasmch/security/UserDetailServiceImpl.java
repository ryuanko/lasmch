package com.lasmch.security;

import com.lasmch.admmgmt.usermgmt.dao.UserMgmtDao;
import com.lasmch.admmgmt.usermgmt.domain.UserMgmt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailServiceImpl {


  @Autowired
  UserMgmtDao usermgmtdao;

  public UserDetails loadUserByUsername(String username,String password) throws UsernameNotFoundException {

    Map<String, Object> params = new HashMap<>();
    params.put("id", username);
    params.put("pwd", password);
    UserMgmt user =  usermgmtdao.logintUserInfo(params).orElseThrow(() -> new UsernameNotFoundException(String.format("가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.", username)));

    return UserPrincipal.create(user);
  }
}
