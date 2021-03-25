package com.lasmch.churchInfo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/church-info")
@Slf4j
@Transactional
public class ChurchInfoController {

    @GetMapping
    @ResponseBody
    public ModelAndView main() throws Exception {
        return new ModelAndView("churchInfo/church_info_view");
    }
}
