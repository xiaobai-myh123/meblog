package com.myh.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.parser.Vary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/*
**2020年6月30日--上午11:37:53
**@version:
**莫耀华:
**@Description:   处理全局的异常
*/
@ControllerAdvice
//处理Controller的Exception的异常
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    /**
     * 异常处理
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public ModelAndView handleException(HttpServletRequest request, Exception e) throws Exception {
    	//如果抛出的异常没有转态码 就去error 和自定义异常一起使用
        logger.error("Request URL : {} , Exception : {}", request.getRequestURL(), e);
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", request.getRequestURL());
        mav.addObject("exception", e);
        mav.setViewName("error/error");
        return mav;
    }
    /**
     * 这个方法也可以写在类中 默认处理本类异常 如果全局 也有 优先选择近的
     * 1.告诉springmvc这个方法处理这个类的异常
     * 2.给携带异常信息不能给参数位置写Model
     * 3.返回ModelAndView就行(默认还是会走视图解析器)
     */
}
