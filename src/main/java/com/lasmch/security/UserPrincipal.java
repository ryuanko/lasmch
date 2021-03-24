package com.lasmch.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lasmch.user.domain.Role;
import com.lasmch.user.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Data
public class UserPrincipal implements UserDetails {

  @JsonIgnore
  private final String password = null;
  @JsonIgnore
  private final boolean accountNonExpired = true;
  @JsonIgnore
  private final boolean accountNonLocked = true;
  @JsonIgnore
  private final boolean credentialsNonExpired = true;
  @JsonIgnore
  private final boolean enabled = true;
  private String username;
  private String nickname;
  private User userDetail;

  private Collection<? extends GrantedAuthority> authorities;


  public static UserPrincipal create(String username, String nickname, String auth) {
    UserPrincipal user = new UserPrincipal();
    user.setUsername(username);
    user.setNickname(nickname);
    if (auth.equals("SM")) {
      user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(Role.ADMIN.getValue())));
    } else {
      user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(Role.MEMBER.getValue())));
    }

    return user;
  }

  public static UserPrincipal create(User userDetail) {
    UserPrincipal user = create(userDetail.getId(), userDetail.getKorName(), userDetail.getAuth());
    user.userDetail = userDetail;
    return user;
  }
}
