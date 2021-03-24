package com.lasmch.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

  /*
     message: 에러 메시지 (validation 에러 발생시 메시지는 존재하지 않음),
     errors: [] //validation 에러메시지 ,
     status: Http Status 상태,
     trace: StackTrace,
     timestamp: 에러발생 시간
   */
  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

    Map<String, Object> errors = super.getErrorAttributes(webRequest, options);

    Throwable t = getError(webRequest);

    if (t == null)
      return errors;


    if (t instanceof BindException) { //Validation 에러

      errors.remove("message");
      BindingResult bindingResult = ((BindException) t).getBindingResult();

      List<String> errs = bindingResult.getAllErrors().stream().map(err -> String.format("%s 은(는) %s", ((FieldError) err).getField(), err.getDefaultMessage()))
        .collect(Collectors.toList());

      errors.put("errors", errs);
      errors.put("status", 400);

    } else {
      errors.put("message", Optional.ofNullable(t.getMessage()).orElse("Internal Server Error"));
    }


    return errors;
  }


}
