package com.lasmch.board.controller;

import com.lasmch.aws.AwsClient;
import com.lasmch.board.dao.BoardDao;
import com.lasmch.board.service.BoardService;
import com.lasmch.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/board")
@Slf4j
@Transactional
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardDao boardDao;

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
        return boardService.select(params).asModel("board/board_list");
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

        ModelAndView mv = new ModelAndView("board/board_view");
        mv.addObject("params", boardDao._view(params));
        return mv;
    }

    @GetMapping("/write")
    @ResponseBody
    public ModelAndView write(@RequestParam Map<String, Object> params) throws Exception {
        return write_post(params);
    }

    @PostMapping("/write")
    @ResponseBody
    public ModelAndView write_post(@RequestParam Map<String, Object> params) throws Exception {

        ModelAndView mv = new ModelAndView("board/board_write");
        if (params.get("seq_id") != null && !params.get("seq_id").equals("")) {
            mv.addObject("params", boardDao._view(params));
            mv.addObject("return_params", params);
        }

        return mv;
    }


    @PutMapping
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody Map<String, Object> params, @AuthenticationPrincipal UserPrincipal principal) throws Exception {

        params.put("update_name", principal.getNickname());

        log.debug("{}", params);

        if (params.get("seq_id") != null && !params.get("seq_id").equals("")) {
            ResponseEntity.ok(boardDao._update(params));
        } else {
            ResponseEntity.ok(boardDao._insert(params));
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{seq_id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable String seq_id) throws Exception {
        return ResponseEntity.ok(boardDao._delete(seq_id));
    }
}
