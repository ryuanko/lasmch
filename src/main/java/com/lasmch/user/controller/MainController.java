package com.lasmch.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@Slf4j
@Transactional
public class MainController {

    @GetMapping
    @ResponseBody
    public ModelAndView main() throws Exception {

        Map <String, Object> params = new HashMap<>();
        params.put("test", "adsasdasd");

        return new ModelAndView("index").addObject("params", params);
    }
}
