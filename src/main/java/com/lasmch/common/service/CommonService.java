package com.lasmch.common.service;


import com.lasmch.pager.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CommonService {
    public Map<String, Object> setOffset(Map<String, Object> map) {
        Map<String, Object> params = new HashMap<>(map);
        if (params.get("page") == null || params.get("page").equals("")) params.put("page", 1);
        if (params.get("fetchSize") == null || params.get("fetchSize").equals("")) params.put("fetchSize", 13);
        params.put("offset", Pagination.offset_map(params));
        return params;
    }
}
