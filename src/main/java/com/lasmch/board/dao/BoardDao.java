package com.lasmch.board.dao;

import com.lasmch.board.domain.FileInfo;
import com.lasmch.util.IDaoProvider;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDao extends IDaoProvider {

    int setHit(Map<String,Object> params);

    Integer getMaxSeqId();

    Integer getMaxFileSeqId(Map<String,Object> params);
    List<FileInfo> fileSelect (Map<String, Object> map);
    int fileInsert(Map<String,Object> params);
    int fileDelete(Map<String,Object> params);

}
