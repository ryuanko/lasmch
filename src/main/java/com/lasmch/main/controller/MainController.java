package com.lasmch.main.controller;

import com.lasmch.aws.AwsClient;
import com.lasmch.board.dao.BoardDao;
import com.lasmch.board.domain.Board;
import com.lasmch.board.domain.FileInfo;
import com.lasmch.common.service.CommonService;
import com.lasmch.youtube.dao.YoutubeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
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

    @Autowired
    AwsClient awsClient;

    @GetMapping
    @ResponseBody
    public ModelAndView main() throws Exception {
        ModelAndView mv = new ModelAndView("index");


        Map<String, Object> params = new HashMap<>();
        params.put("type_c", "CHN");
        params.put("fetchSize", "5");
        params = commonService.setOffset(params);


        Map<String, Object> params2 = new HashMap<>();
        params2.put("seq_id", "9");
        params2.put("type_c", "MSD");
        List<FileInfo> file_list =  boardDao.fileSelect(params2);
        file_list.stream().forEach(i-> {
            i.setFileS3Url(awsClient.getSignedUrl("LASMCH/"+ i.getFileS3Key()));
        });

        mv.addObject("main_slid_list", file_list);
        mv.addObject("chn_list", boardDao._select(params));
        mv.addObject("youtube_list", youtubeDao.mainPageView());
        return mv;
    }
}
