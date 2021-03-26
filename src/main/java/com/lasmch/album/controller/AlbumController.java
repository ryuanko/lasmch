package com.lasmch.album.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/album")
@Slf4j
@Transactional
public class AlbumController {

    @GetMapping("/view")
    @ResponseBody
    public ModelAndView view() throws Exception {
        return new ModelAndView("album/album_view");
    }
}
