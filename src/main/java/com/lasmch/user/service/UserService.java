package com.lasmch.user.service;


import com.lasmch.common.service.CommonService;
import com.lasmch.pager.Pagination;
import com.lasmch.user.dao.UserDao;
import com.lasmch.user.domain.User;
import com.lasmch.youtube.domain.Youtube;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService {
    @Autowired
    CommonService commonService;

    @Autowired
    UserDao userDao;

    public Pagination select(Map<String ,Object> map) {
        Map<String, Object> params = commonService.setOffset(map);

        List<User> list = userDao._select(params);

        return new Pagination(
                Integer.parseInt(params.get("page").toString()),
                Integer.parseInt(params.get("fetchSize").toString()),
                userDao.selectCount(params),
                list,
                params
        );
    }
}
