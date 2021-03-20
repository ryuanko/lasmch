package com.lasmch.youtube.controller;

import com.lasmch.util.BeanUtil;
import com.lasmch.youtube.dao.YoutubeDao;
import com.lasmch.youtube.service.YoutubeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/youtube")
@Slf4j
@Transactional
public class YoutubeController {

    @Autowired
    YoutubeService youtubeService;

    @Autowired
    YoutubeDao youtubeDao;

    @GetMapping
    @ResponseBody
    public ModelAndView index(@RequestParam Map<String, Object> params) throws Exception {
        return index_post(params);
    }

    @PostMapping
    @ResponseBody
    public ModelAndView index_post(@RequestParam Map<String, Object> params) throws Exception {
        ModelAndView mv = new ModelAndView("youtube/youtube_list");
        return youtubeService.select(params).asModel("youtube/youtube_list");
    }

    @GetMapping("/write")
    @ResponseBody
    public ModelAndView index_insert(@RequestParam Map<String, Object> params) throws Exception {
        ModelAndView mv = new ModelAndView("youtube/youtube_write");
        mv.addObject("params", params);
        return mv;
    }

    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<?> insert(@RequestParam Map<String, Object> params) throws Exception {
        return ResponseEntity.ok(youtubeDao._insert(params));
    }

    @PostMapping("/update")
    @ResponseBody
    public ModelAndView index_update(@RequestParam Map<String, Object> params) throws Exception {
        ModelAndView mv = new ModelAndView("youtube/youtube_write");
        mv.addObject("params", params);
        return mv;
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<?> update(@RequestParam Map<String, Object> params) throws Exception {
        return ResponseEntity.ok(youtubeDao._update(params));
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<?> delete(@RequestParam Map<String, Object> params) throws Exception {
        return ResponseEntity.ok(youtubeDao._delete(params));
    }
}
