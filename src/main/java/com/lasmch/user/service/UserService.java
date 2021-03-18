package com.lasmch.user.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserService {

    public Map<String, Object> list(Map<String, Object> params) throws Exception {
        log.debug("{}", params);

        Map<String, Object> rst = new HashMap<>();
        return rst;
    }
}
