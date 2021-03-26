package com.lasmch.biblestudy.controller;

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
@RequestMapping("/bible-study")
@Slf4j
@Transactional
public class BibleStudyController {

    @GetMapping("/view")
    @ResponseBody
    public ModelAndView view() throws Exception {
        return new ModelAndView("biblestudy/bible_view");
    }
}
