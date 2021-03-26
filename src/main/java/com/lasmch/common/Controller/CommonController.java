package com.lasmch.common.Controller;

import com.lasmch.aws.AwsClient;
import com.lasmch.aws.Response;
import com.lasmch.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}
