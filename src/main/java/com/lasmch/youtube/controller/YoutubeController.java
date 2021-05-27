package com.lasmch.youtube.controller;

import com.lasmch.aws.AwsClient;
import com.lasmch.security.UserPrincipal;
import com.lasmch.youtube.dao.YoutubeDao;
import com.lasmch.youtube.service.YoutubeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
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

    @Autowired
    AwsClient awsClient;

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


    @GetMapping("/view")
    @ResponseBody
    public ModelAndView view(@RequestParam Map<String, Object> params) throws Exception {
        return view_post(params);
    }
    @PostMapping("/view")
    @ResponseBody
    public ModelAndView view_post(@RequestParam Map<String, Object> params) throws Exception {

        youtubeDao.read_cnt(params);

        ModelAndView mv = new ModelAndView("youtube/youtube_view");
        mv.addObject("params", youtubeDao._view(params));
        return mv;
    }

    @GetMapping("/adm-write")
    @ResponseBody
    public ModelAndView write(@RequestParam Map<String, Object> params) throws Exception {
        return write_post(params);
    }

    @PostMapping("/adm-write")
    @ResponseBody
    public ModelAndView write_post(@RequestParam Map<String, Object> params) throws Exception {

        ModelAndView mv = new ModelAndView("youtube/youtube_write");
        if (params.get("seq_id") != null && !params.get("seq_id").equals("")) {
            mv.addObject("params", youtubeDao._view(params));
            mv.addObject("return_params", params);
        }

        return mv;
    }


    @PutMapping
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody Map<String, Object> params, @AuthenticationPrincipal UserPrincipal principal) throws Exception {

        params.put("update_name", principal.getNickname());

        if (params.get("seq_id") != null && !params.get("seq_id").equals("")) {
            ResponseEntity.ok(youtubeDao._update(params));
        } else {
            ResponseEntity.ok(youtubeDao._insert(params));
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{seq_id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable String seq_id) throws Exception {
        return ResponseEntity.ok(youtubeDao._delete(seq_id));
    }
}
