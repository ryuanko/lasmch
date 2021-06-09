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


        // 교회 소식
        Map<String, Object> params = new HashMap<>();
        params.put("type_c", "CHN");
        params.put("fetchSize", "5"); // 교회소식 5개만 가져오기
        params = commonService.setOffset(params);


        // 메인슬라이드이미지
        Map<String, Object> params2 = new HashMap<>();
        // params2.put("seq_id", "9");
        params2.put("type_c", "MSD");
        Board temp = (Board)boardDao._select(params2).stream().findFirst().orElse(new Board());
        params2.put("seq_id", temp.getSeqId());
        List<FileInfo> file_list =  boardDao.fileSelect(params2);
        file_list.stream().forEach(i-> {
            i.setFileS3Url(awsClient.getSignedUrl("LASMCH/"+ i.getFileS3Key()));
        });

        // 목사님 말씀
        params2 = new HashMap<>();
        // params2.put("seq_id", "11");
        params2.put("type_c", "PST");
        temp = (Board)boardDao._select(params2).stream().findFirst().orElse(new Board());
        params2.put("seq_id", temp.getSeqId());

        FileInfo minister_file =  boardDao.fileSelect(params2).stream().findFirst().orElse(new FileInfo());
        Board minister_params = (Board)boardDao._view(params2);
        minister_params.setFileS3Url(awsClient.getSignedUrl("LASMCH/"+ minister_file.getFileS3Key()));


        mv.addObject("main_slid_list", file_list);
        mv.addObject("chn_list", boardDao._select(params));
        mv.addObject("youtube_list", youtubeDao.mainPageView());
        mv.addObject("minister_params", minister_params); // 목사님 말씀
        return mv;
    }
}
