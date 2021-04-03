package com.lasmch.worshipTime.controller;

import com.lasmch.aws.AwsClient;
import com.lasmch.board.dao.BoardDao;
import com.lasmch.board.domain.Board;
import com.lasmch.board.domain.FileInfo;
import com.lasmch.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/worship-time")
@Slf4j
@Transactional
public class WorshipTimeController {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardDao boardDao;

    @Autowired
    AwsClient awsClient;


    @GetMapping
    @ResponseBody
    public ModelAndView main() throws Exception {
        return new ModelAndView("worshipTime/worship_time_list");
    }

    @GetMapping("/view")
    @ResponseBody
    public ModelAndView view() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("seq_id", "12");
        params.put("type_c", "WHT");
        Board temp = (Board)boardDao._view(params);
        List<FileInfo> file_list =  boardDao.fileSelect(params);

        file_list.stream().forEach(i-> {
            i.setFileS3Url(awsClient.getSignedUrl("LASMCH/"+ i.getFileS3Key()));
        });

        ModelAndView mv = new ModelAndView("worshipTime/worship_time_view");
        mv.addObject("file_list", file_list);
        mv.addObject("params", temp);
        return mv;
    }
}
