package com.lasmch.board.service;


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
public class BoardService {

    @Autowired
    CommonService commonService;

    @Autowired
    BoardDao boardDao;

    @Autowired
    AwsClient awsClient;

    public Pagination select(Map<String ,Object> map) {
        Map<String, Object> params = commonService.setOffset(map);

        List<Board> list = boardDao._select(params);

        return new Pagination(
                Integer.parseInt(params.get("page").toString()),
                Integer.parseInt(params.get("fetchSize").toString()),
                boardDao.selectCount(params),
                list,
                params
        );
    }
    public int insert(Map<String ,Object> params, FileInfo files) throws Exception {
        params.put("seq_id", boardDao.getMaxSeqId());
        fileupdate(params, files);
        return boardDao._insert(params);
    }

    // 파일 리스트 구하고 s3Url 구하기
    public List<FileInfo> fileList(Map<String, Object> params) {
        List<FileInfo> file_list =  boardDao.fileSelect(params);
        file_list.stream().forEach(i-> {
            i.setFileS3Url(awsClient.getSignedUrl("LASMCH/"+ i.getFileS3Key()));
        });

        return file_list;
    }


    // 파일 업로드 , 삭제
    public void fileupdate (Map<String, Object> params, FileInfo files)  throws Exception {

        if (files.getDelfile() != null) {
            files.getDelfile().stream().forEach(i -> {
                Map<String, Object> map =  new HashMap<>();
                map.put("seq_id", params.get("seq_id"));
                map.put("type_c", params.get("type_c"));
                map.put("file_s3_key", i);
                boardDao.fileDelete(map);
                awsClient.delete("LASMCH/"+i);
            });
        }

        if (files.getUpfile() != null) {
            for (MultipartFile i : files.getUpfile()) {

                Integer file_seq_id = boardDao.getMaxFileSeqId(params);
                if (file_seq_id == null) file_seq_id = 1;

                Map<String, Object> map = new HashMap<>();
                String file_s3_key = "BOARD/" + params.get("seq_id") + "-" + params.get("type_c") + "-" + file_seq_id + "-" + UUID.randomUUID().toString();
                map.put("seq_id", params.get("seq_id"));
                map.put("type_c", params.get("type_c"));
                map.put("file_seq_id", file_seq_id);
                map.put("file_name", i.getOriginalFilename());
                map.put("file_s3_key", file_s3_key);
                map.put("size_n", i.getSize());
                boardDao.fileInsert(map);
                awsClient.upStream("LASMCH/" + file_s3_key, i.getInputStream(), i.getContentType());
            }
        }
    }

    // 해당 글 모든 파일 삭제
    public void fileAllDelete(String s3_path) {
        // 파일 정보 S3 삭제
        List<String> list =  awsClient.list("LASMCH/" +s3_path, 50);
        list.forEach(i -> { awsClient.delete(i); });
    }
}
