package com.lasmch.user.controller;

import com.lasmch.user.dao.UserDao;
import com.lasmch.user.service.UserService;
import com.lasmch.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
@Transactional
public class UserController {

    @GetMapping
    @ResponseBody
    public ModelAndView main(@RequestParam Map<String, Object> params) throws Exception {
        return new ModelAndView("home/home");
    }
}
