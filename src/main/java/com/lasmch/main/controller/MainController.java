package com.lasmch.main.controller;

import com.lasmch.board.dao.BoardDao;
import com.lasmch.common.service.CommonService;
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

    @Autowired
    BoardDao boardDao;

    @Autowired
    CommonService commonService;

    @GetMapping
    @ResponseBody
    public ModelAndView main() throws Exception {
        ModelAndView mv = new ModelAndView("index");


        Map<String, Object> params = new HashMap<>();
        params.put("type_c", "CHN");
        params.put("fetchSize", "5");
        params = commonService.setOffset(params);

        mv.addObject("chn_list", boardDao._select(params));
        mv.addObject("youtube_list", youtubeDao.mainPageView());
        return mv;
    }
}
