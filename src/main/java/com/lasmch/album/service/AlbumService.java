package com.lasmch.album.service;


import com.lasmch.aws.AwsClient;
import com.lasmch.board.dao.BoardDao;
import com.lasmch.board.domain.Board;
import com.lasmch.board.domain.FileInfo;
import com.lasmch.common.service.CommonService;
import com.lasmch.pager.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class AlbumService {

    @Autowired
    CommonService commonService;

    @Autowired
    BoardDao boardDao;

    @Autowired
    AwsClient awsClient;

    public Pagination select(Map<String ,Object> map) {
        Map<String, Object> params = commonService.setOffset(map);

        List<Board> list = boardDao._select(params);

        list.stream().forEach(i-> {
            Map<String, Object> temp = new HashMap<>();
            temp.put("seq_id", i.getSeqId());
            temp.put("type_c", i.getTypeC());
            FileInfo info = boardDao.fileSelectMin(temp);
            if (info != null && info.getFileS3Key() != null) {
                i.setFileS3Url(awsClient.getSignedUrl("LASMCH/"+ info.getFileS3Key()));
            }
        });

        return new Pagination(
                Integer.parseInt(params.get("page").toString()),
                Integer.parseInt(params.get("fetchSize").toString()),
                boardDao.selectCount(params),
                list,
                params
        );
    }
}
