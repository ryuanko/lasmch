package com.lasmch.config;

import com.lasmch.security.LoginAuthenticationProvider;
import com.lasmch.security.LoginCallbackHandler;
import com.lasmch.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.DefaultHttpFirewall;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginAuthenticationProvider loginProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .headers().frameOptions().sameOrigin()
                .cacheControl().disable()
                .and()// 로그인 설정
                    .formLogin()
                    .defaultSuccessUrl("/", true)
                    .successHandler(new SuccessHandler())
                    .permitAll()
                .and() // 로그아웃 설정
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                .and() // 페이지 권한 설정
                .authorizeRequests()
                .antMatchers("/assets/**", "/common/**" ,"/components/**", "/").permitAll()
                .antMatchers("/board", "/board/view").permitAll()

                .antMatchers("/church", "/church/view", "/church/info").permitAll()
                .antMatchers("/weekly", "/weekly/view").permitAll()
                .antMatchers("/worship-time", "/worship-time/view").permitAll()
                .antMatchers("/youtube", "/youtube/view").permitAll()
                .antMatchers("/user-mgmt", "/user-mgmt/write", "/user-mgmt/info", "/user-mgmt/id-chk/*", "/user-mgmt/insert" ).permitAll()

                .antMatchers("/**/adm-*").hasRole("ADMIN")
                .antMatchers("/adm-mgmt, /adm-mgmt/**").hasRole("ADMIN")
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/assets/**",  "/common/**");

        web.httpFirewall(new DefaultHttpFirewall());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginProvider);
    }


    @Autowired
    LoginCallbackHandler loginCallbackHandler;


    class SuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            loginCallbackHandler.update((UserPrincipal) authentication.getPrincipal());
            response.sendRedirect("/");
        }
    }


}
