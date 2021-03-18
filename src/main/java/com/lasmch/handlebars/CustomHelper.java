package com.lasmch.handlebars;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Options;
import devtools.handlebars.HandlebarsHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@HandlebarsHelper
public class CustomHelper {

  final ObjectMapper mapper = new ObjectMapper();


   public String raw(Object value, Options options) throws IOException {
     return options.fn().toString();
   }


  /**
   * java에서
   * data 는 pojo이거나 데이터이고
   * 화면에서 아래와 같이 사용시 json을 출력됨
   * {{json data}}
   * @param value
   * @param options
   * @return
   * @throws JsonProcessingException
   */
   public CharSequence json(Object value, Options options) throws JsonProcessingException {
      return new Handlebars.SafeString(mapper.writeValueAsString(value));
   }


  /**
   * example) <br/>
   * {{math data '+' 1}}
   * @param value
   * @param options
   * @return
   */
   public int math(Object value, Options options) {

     if(value == null)
       return 0;

     String operator = options.param(0);
     int left = (int) value;
     int right = options.param(1);

     switch (operator) {

       case "+":
         return left + right;

       case "-":
         return left - right;

       case "*":
         return left * right;

       case "/":
         return left / right;

       case "%":
         return left % right;
     }

     return 0;
   }


   public String getParam(HttpServletRequest request, Options options) {

     if(request == null)
       return null;

     String param = options.param(0);
     if( param != null) {
       return request.getParameter(param);
     }

     return null;
   }


  /**
   * Enum과 비교
   * eq는 문자열끼리기 비교하기 때문에 적용안됨
   * @param e
   * @param options
   * @return
   */
  public boolean enq(Enum e, Options options) {
    String param = options.param(0);
    if (param == null)
      return false;

    return e.name().equals(param);
  }
}
