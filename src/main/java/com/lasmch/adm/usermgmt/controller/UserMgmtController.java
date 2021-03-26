package com.lasmch.adm.usermgmt.controller;

import com.lasmch.adm.usermgmt.dao.UserMgmtDao;
import com.lasmch.adm.usermgmt.domain.UserMgmt;
import com.lasmch.adm.usermgmt.service.UserMgmtService;
import com.lasmch.exception.ValidationFailureException;
import com.lasmch.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user-mgmt")
@Slf4j
@Transactional
public class UserMgmtController {

    @Autowired
    UserMgmtService userMgmtService;

    @Autowired
    UserMgmtDao userMgmtDao;

    @GetMapping("/adm-list")
    @ResponseBody
    public ModelAndView index(@RequestParam Map<String, Object> params) throws Exception {
        return index_post(params);
    }

    @PostMapping("/adm-list")
    @ResponseBody
    public ModelAndView index_post(@RequestParam Map<String, Object> params) throws Exception {
        ModelAndView mv = new ModelAndView("usermgmt/usermgmt_list");
        return userMgmtService.select(params).asModel("usermgmt/usermgmt_list");
    }


    @GetMapping("/info")
    @ResponseBody
    public ModelAndView info(@AuthenticationPrincipal UserPrincipal principal ) throws Exception {

        Map<String, Object> params = new HashMap<>();

        params.put("id", principal.getUsername());

        ModelAndView mv = new ModelAndView("usermgmt/usermgmt_view");
        mv.addObject("params", userMgmtDao._view(params));

        return mv;
    }

    @PostMapping("/view")
    @ResponseBody
    public ModelAndView view(@RequestParam Map<String, Object> params) throws Exception {

        ModelAndView mv = new ModelAndView("usermgmt/usermgmt_view");
        params.remove("auth");
        mv.addObject("params", userMgmtDao._view(params));

        return mv;
    }

    @GetMapping("/write")
    @ResponseBody
    public ModelAndView write(@AuthenticationPrincipal UserPrincipal principal ) throws Exception {
        ModelAndView mv = new ModelAndView("usermgmt/usermgmt_view");
        mv.addObject("params", new UserMgmt());
        return mv;
    }

    @GetMapping("/id-chk/{id}")
    @ResponseBody
    public ResponseEntity<?> idChk(@PathVariable String id) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return ResponseEntity.ok(userMgmtDao.selectCount(params));
    }

    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<?> insert(@RequestBody Map<String, Object> params, @AuthenticationPrincipal UserPrincipal principal) throws Exception {

        return ResponseEntity.ok(userMgmtDao._insert(params));
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody Map<String, Object> params, @AuthenticationPrincipal UserPrincipal principal) throws Exception {

        if (!principal.getUsername().equals(params.get("id")) && !principal.getUserDetail().getAuth().equals("SM")) {
            throw new ValidationFailureException("해당 권한이 없습니다.");
        }

        return ResponseEntity.ok(userMgmtDao._update(params));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@RequestBody Map<String, Object> params, @AuthenticationPrincipal UserPrincipal principal) throws Exception {

        if (!principal.getUsername().equals(params.get("id")) && !principal.getUserDetail().getAuth().equals("SM")) {
            throw new ValidationFailureException("해당 권한이 없습니다.");
        }

        return ResponseEntity.ok(userMgmtDao._delete(params));
    }

}
