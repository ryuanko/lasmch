package com.lasmch.youtube.service;


import com.lasmch.common.service.CommonService;
import com.lasmch.pager.Pagination;
import com.lasmch.youtube.dao.YoutubeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class YoutubeService {

    @Autowired
    CommonService commonService;

    @Autowired
    YoutubeDao youtubeDao;

    public Pagination select(Map<String ,Object> map) {
        Map<String, Object> params = commonService.setOffset(map);

        return new Pagination(
                Integer.parseInt(params.get("page").toString()),
                Integer.parseInt(params.get("fetchSize").toString()),
                youtubeDao.selectCount(params),
                youtubeDao._select(params),
                params
        );
    }
}
