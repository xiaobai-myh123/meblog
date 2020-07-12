package com.myh.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myh.pojo.User;
import com.myh.service.impl.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller 
@Api(tags = "后台-登录接口")
@RequestMapping("/admin")
@Transactional
public class LoginController {
	
	@Autowired
	private UserServiceImpl userSerivce;
	/**
	   *      去登录页面
	 * @return
	 */
	@ApiOperation(value = "去登录页面",notes = "去登录页面")
	@RequestMapping(method = RequestMethod.GET,value = {"/","/login.html","/login"})
	public String loginPage() {
		return "admin/login";
	}
	/**
	 *     登录页面判断
	 * @param username
	 * @param password
	 * @param session
	 * @param attributes
	 * @return
	 */
	@ApiOperation(value = "登录页面判断",notes = "根据用户名和密码判断是否登录成功")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "Param",name = "username",value = "用户名",required = true),
		@ApiImplicitParam(paramType = "Param",name = "password",value = "用户密码",required = true)
	})
	@RequestMapping(method = RequestMethod.POST,value = "/login")
	public String loging(
			@RequestParam(value = "username")String username,
			@RequestParam("password")String password,
			HttpSession session,
			RedirectAttributes attributes
	) {
		User user = userSerivce.selectOne(username, password);
		if(user!=null) {
			//登录成功
			user.setPassword(null);
			session.setAttribute("user", user);
			return "admin/index";
		}
		//登录失败
		attributes.addFlashAttribute("message", "用户名或者密码错误");
		attributes.addFlashAttribute("username", username);
		return "redirect:/admin";
	}
	/**
	 *     退出方法
	 * @param session
	 * @return
	 */
	@ApiOperation(value = "退出登录",notes = "退出方法")
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/admin";
	}
}
