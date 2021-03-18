package com.lasmch.lasm.controller;

import com.lasmch.lasm.dao.LasmchDao;
import com.lasmch.lasm.domain.User;
import com.lasmch.lasm.service.LasmchService;
import com.lasmch.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/lasmch")
@Slf4j
@Transactional
public class LasmchController {

    @Autowired
    LasmchService lasmchService;

    @Autowired
    LasmchDao lasmchDao;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> userInfo(@RequestParam Map<String, Object> params) throws Exception {
        Map<String, Object> map = new HashMap<>();
        BeanUtil.copyToMap(params, map);
        return ResponseEntity.ok(lasmchDao._select(map));
    }
}
