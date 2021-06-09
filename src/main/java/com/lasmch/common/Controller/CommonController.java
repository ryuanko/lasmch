package com.lasmch.common.Controller;

import com.amazonaws.util.IOUtils;
import com.lasmch.aws.AwsClient;
import com.lasmch.aws.Response;
import com.lasmch.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

@Controller
@RequestMapping("/common")
@Slf4j
@Transactional
public class CommonController {

    @Autowired
    AwsClient awsClient;

    @Autowired
    CommonService commonService;

    @PostMapping("/download")
    @ResponseBody
    public ResponseEntity<?> Download(@RequestHeader(value = "User-Agent") String agent, @RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fullKey	=	 "LASMCH/"+request.getParameter("filekey").toString();
        log.debug("S3KEY:"+fullKey);
        Response responseFile = awsClient.downStream(fullKey);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (request.getParameter("origin_name").toString() != null) {
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(request.getParameter("origin_name").toString(), Charset.forName("UTF-8")).build());
        }
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(responseFile.getInputStream()));
    }

    // 이미지뷰 (이미지 미리보기 기능을 제공한다)
    @RequestMapping(value = "/imageview")
    public ResponseEntity<byte[]> IMAGEVIEW_S3(HttpServletResponse response, HttpServletRequest request) throws Exception {

        String fileNm =request.getParameter("img_nm"); // toLowerCase()
        String s3_key =request.getParameter("s3_key"); // toLowerCase()


        int len = fileNm.lastIndexOf(".");
        String format = fileNm.substring(len + 1, fileNm.length());
        String format_dot = fileNm.substring(len , fileNm.length());
        fileNm = fileNm.substring(0, len)+format_dot.toLowerCase();


        ResponseEntity<byte[]> entity = null;
        InputStream inputStream=null;
        try {
            MediaType mType = MediaType.IMAGE_JPEG;

            if ("GIF".equals(format.toUpperCase())) {
                mType = MediaType.IMAGE_GIF;
            } else if ("PNG".equals(format.toUpperCase())) {
                mType = MediaType.IMAGE_PNG;
            }

            HttpHeaders header = new HttpHeaders();

            //파일다운로드 시작
            inputStream =  awsClient.downStream("LASMCH/"+ s3_key).getInputStream() ;

            header.setContentType(mType);
            header.set("X-Content-Type-Options", ""); // 익스 때문에  기본값 nosniff 제거

            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(inputStream), header, HttpStatus.CREATED);


        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            if(inputStream!=null) {
                inputStream.close();
            }
        }
        catch (IOException e) {
            log.error(e.getMessage());
            if(inputStream!=null) {
                inputStream.close();
            }
        }
        catch (Exception k) {
            log.error(k.getMessage());
            if(inputStream!=null) {
                inputStream.close();
            }
        }
        finally {
            try {
                if(inputStream!=null) {
                    inputStream.close();
                }
            }catch(Exception e) {
                System.out.println(e.toString());
            }
        }
        log.debug("imageView ----------- END");

        return entity;
    }
}
