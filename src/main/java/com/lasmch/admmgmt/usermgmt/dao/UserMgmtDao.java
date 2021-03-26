package com.lasmch.admmgmt.usermgmt.dao;

import com.lasmch.admmgmt.usermgmt.domain.UserMgmt;
import com.lasmch.util.IDaoProvider;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.Optional;

@Mapper
public interface UserMgmtDao extends IDaoProvider {
    Optional<UserMgmt> logintUserInfo(Map<String, Object> params);

}
