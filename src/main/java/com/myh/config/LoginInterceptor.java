package com.myh.config;

import java.util.logging.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 拦截器
 * @author 莫耀华
 *
 */

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(request.getSession().getAttribute("user")!=null) {
			return true;
		}
		
		request.setAttribute("message", "没有权限，请先登录！");
		request.getRequestDispatcher("/admin/login.html").forward(request, response);
		return false;
	}

}
