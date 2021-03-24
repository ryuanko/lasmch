package com.lasmch.config;

import com.lasmch.security.UserPrincipal;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Optional;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  final ViewRegistry viewRegistry;

  public WebConfiguration(ViewRegistry viewRegistry) {
    this.viewRegistry = viewRegistry;
  }


  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SessionInterceptor());
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    for (ViewRegistry.View v : viewRegistry) {
      registry.addViewController(v.path).setViewName(v.viewName);
    }
  }

  class SessionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
      if (modelAndView != null) {

        modelAndView.addObject("request", request);

        Optional.of(SecurityContextHolder.getContext())
          .flatMap(i -> Optional.ofNullable(i.getAuthentication()))
          .map(auth -> {

            if (auth.getPrincipal() instanceof UserDetails) {
              return (UserPrincipal) auth.getPrincipal();

            }
            return null;

          }).ifPresent(user -> {
          modelAndView.addObject("session",
            new HashMap<String, Object>() {{
              put("nickname", user.getNickname());
              put("username", user.getUsername());
              put("userDetail", user.getUserDetail());
            }});

        });


      }
    }
  }

}
