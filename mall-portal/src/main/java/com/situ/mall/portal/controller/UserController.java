package com.situ.mall.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.constant.Const;
import com.situ.mall.core.entity.User;
import com.situ.mall.core.service.IUserService;

@Controller
public class UserController {
	@Autowired
	public IUserService userService;
	
	
	@RequestMapping(value="/Glogin",method=RequestMethod.POST)
	@ResponseBody
	public ServerResponse login(String username,String password,HttpSession session){
		ServerResponse<User> response = userService.login(username,password);
		System.out.println(username+" "+ password);
		User user = response.getData();
		session.setAttribute(Const.CURRENT_USER, user);
		return response;
		
	}
	@RequestMapping("tologin")
	public String login(){
		
		return "login";
		
	}
}
