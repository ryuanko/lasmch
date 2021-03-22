package com.lasmch.user.controller;

import com.lasmch.youtube.dao.YoutubeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    YoutubeDao youtubeDao;

    @GetMapping
    @ResponseBody
    public ModelAndView main() throws Exception {

        return new ModelAndView("index").addObject("youtube_list", youtubeDao.main());
    }
}
