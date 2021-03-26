package com.lasmch.admmgmt.usermgmt.service;


import com.lasmch.admmgmt.usermgmt.dao.UserMgmtDao;
import com.lasmch.admmgmt.usermgmt.domain.UserMgmt;
import com.lasmch.common.service.CommonService;
import com.lasmch.pager.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserMgmtService {
    @Autowired
    CommonService commonService;

    @Autowired
    UserMgmtDao userMgmtDao;

    public Pagination select(Map<String ,Object> map) {
        Map<String, Object> params = commonService.setOffset(map);

        List<UserMgmt> list = userMgmtDao._select(params);

        return new Pagination(
                Integer.parseInt(params.get("page").toString()),
                Integer.parseInt(params.get("fetchSize").toString()),
                userMgmtDao.selectCount(params),
                list,
                params
        );
    }
}
