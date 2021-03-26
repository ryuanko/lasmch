package com.lasmch.worshipTime.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/worship-time")
@Slf4j
@Transactional
public class WorshipTimeController {

    @GetMapping
    @ResponseBody
    public ModelAndView main() throws Exception {
        return new ModelAndView("worshipTime/worship_time_list");
    }

    @GetMapping("/view")
    @ResponseBody
    public ModelAndView view() throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("test", "adsasdasd");

        return new ModelAndView("worshipTime/worship_time_view");
    }
}
