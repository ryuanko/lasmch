package com.lasmch.user.dao;

import com.lasmch.user.domain.User;
import com.lasmch.util.IDaoProvider;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.Optional;

@Mapper
public interface UserDao extends IDaoProvider {
    Optional<User> logintUserInfo(Map<String, Object> params);

}
