package com.lasmch.lasm.dao;

import com.lasmch.lasm.domain.User;
import com.lasmch.util.IDaoProvider;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface LasmchDao extends IDaoProvider {
    Optional<User> getUserInfo(String id);
}
