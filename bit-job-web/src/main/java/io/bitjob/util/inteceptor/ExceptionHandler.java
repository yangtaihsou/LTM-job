/*
package io.bitjob.util.inteceptor;

import DataResult;
import GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;

*/
/**
 * Created with IntelliJ IDEA.
 * User: yangkuan
 * Date: 13-12-4
 * Time: 下午6:34
 * To change this template use File | Settings | File Templates.
 *//*

public class ExceptionHandler implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("ExceptionHandler捕获到了异常", ex);

*/
/*        if(!(request.getHeader("accept").indexOf("application/json")>-1)|| request.getHeader("X-Requested-With").indexOf("XMLHttpRequest")>-1){// 如果不是异步请求
            if (exception instanceof ErpException) {
                return new ModelAndView("error").addObject("message", "对不起，发生了错误!" + exception.getMessage());
            } else {
                return new ModelAndView("error").addObject("message", "对不起，发生了错误");
            }
        }else{*//*

        if(ex  instanceof UndeclaredThrowableException){
            Throwable exception = ((UndeclaredThrowableException) ex).getCause();//从第三方jar抛出的异常，不明白spring为什么这样抛出来了
            try {
                DataResult result = new DataResult();
                result.setStatus(Boolean.FALSE);
               String message =exception.getMessage();
                if (exception instanceof ValidatorException) {
                    result.setReason(((ValidatorException) exception).getMessage());
                }*/
/*else if (exception instanceof ErpException) {
                    result.setReason(((ErpException) exception).getMessage());
                }else if (exception instanceof AuthException) {
                    result.setReason(((AuthException) exception).getMessage());
                }else if (exception instanceof AuthException) {
                    return new ModelAndView("error").addObject("message", "对不起，发生了错误!" + exception.getMessage());
                }*//*
else{
                    result.setReason("系统异常");
                }
                response.setContentType("Content-Type:application/json; charset=UTF-8");
                response.getWriter().write(GsonUtils.toJson(result));
            } catch (IOException e) {
                logger.error("response输出流异常",e.getMessage());
            }
            return new ModelAndView();
        }else{
          return new ModelAndView("error").addObject("message", "对不起，发生了错误!" + ex.getMessage()==null?"":ex.getMessage());
     }
    }
}
*/
