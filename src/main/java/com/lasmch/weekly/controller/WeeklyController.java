package com.lasmch.weekly.controller;

import com.lasmch.aws.AwsClient;
import com.lasmch.board.dao.BoardDao;
import com.lasmch.board.domain.Board;
import com.lasmch.board.domain.FileInfo;
import com.lasmch.board.service.BoardService;
import com.lasmch.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weekly")
@Slf4j
@Transactional
public class WeeklyController {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardDao boardDao;

    @Autowired
    AwsClient awsClient;

    @GetMapping("/temp")
    @ResponseBody
    public ModelAndView temp() throws Exception {
        return new ModelAndView("weekly/weekly");
    }


    @GetMapping
    @ResponseBody
    public ModelAndView index(@RequestParam Map<String, Object> params) throws Exception {
        return index_post(params);
    }

    @PostMapping
    @ResponseBody
    public ModelAndView index_post(@RequestParam Map<String, Object> params) throws Exception {
        params.put("type_c", "WKY");
        return boardService.select(params).asModel("weekly/weekly_list");
    }


    @GetMapping("/view")
    @ResponseBody
    public ModelAndView view(@RequestParam Map<String, Object> params) throws Exception {
        return view_post(params);
    }
    @PostMapping("/view")
    @ResponseBody
    public ModelAndView view_post(@RequestParam Map<String, Object> params) throws Exception {

        boardDao.setHit(params);

        Board temp = (Board)boardDao._view(params);
        if (temp.getDescription() != null  && !temp.getDescription().equals("")) {
            temp.setDescription(HtmlUtils.htmlEscape(temp.getDescription()));
        }
        ModelAndView mv = new ModelAndView("weekly/weekly_view");

        List<FileInfo> file_list =  boardDao.fileSelect(params);

        file_list.stream().forEach(i-> {
            i.setFileS3Url(awsClient.getSignedUrl("LASMCH/"+ i.getFileS3Key()));
        });

        mv.addObject("file_list", file_list);
        mv.addObject("params", temp);
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

        ModelAndView mv = new ModelAndView("weekly/weekly_write");

        Board view_params = new Board();
        List<FileInfo> file_list = new ArrayList<>();
        view_params.setTypeC("WKY");

        if (params.get("seq_id") != null && !params.get("seq_id").equals("")) {
            view_params = (Board)boardDao._view(params);
            file_list = boardDao.fileSelect(params);
        }

        mv.addObject("file_list", file_list);
        mv.addObject("params", view_params);
        mv.addObject("return_params", params);
        return mv;
    }


    @PostMapping("/adm-update")
    @ResponseBody
    public ResponseEntity<?> update(@RequestParam Map<String, Object> params, FileInfo files, @AuthenticationPrincipal UserPrincipal principal) throws Exception {

        params.put("update_nm", principal.getNickname());

        if (params.get("seq_id") != null && !params.get("seq_id").equals("")) {
            boardService.fileupdate(params, files);
            return ResponseEntity.ok(boardDao._update(params));
        } else {
            return ResponseEntity.ok(boardService.insert(params, files));
        }
    }

    @DeleteMapping("/adm-delete/{seq_id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable String seq_id, @RequestParam Map<String, Object> params) throws Exception {
        boardService.fileAllDelete("BOARD/"+ seq_id + "-" + params.get("type_c"));
        boardDao.fileDelete(params);
        return ResponseEntity.ok(boardDao._delete(params));
    }
}
