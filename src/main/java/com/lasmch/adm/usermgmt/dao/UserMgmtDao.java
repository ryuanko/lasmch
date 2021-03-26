package com.lasmch.adm.usermgmt.dao;

import com.lasmch.adm.usermgmt.domain.UserMgmt;
import com.lasmch.util.IDaoProvider;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.Optional;

@Mapper
public interface UserMgmtDao extends IDaoProvider {
    Optional<UserMgmt> logintUserInfo(Map<String, Object> params);

}
