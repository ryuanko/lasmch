package com.lasmch.youtube.dao;

import com.lasmch.util.IDaoProvider;
import com.lasmch.youtube.domain.Youtube;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface YoutubeDao extends IDaoProvider {
    Optional<Youtube> getUserInfo(String id);

    int maxId();

    int read_cnt(Map<String,Object> params);

    List<Youtube> mainPageView ();
}
