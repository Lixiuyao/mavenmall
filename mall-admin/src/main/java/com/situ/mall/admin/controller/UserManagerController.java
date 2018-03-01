package com.situ.mall.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.entity.User;
import com.situ.mall.core.service.IUserService;
@Controller
@RequestMapping("manager/user")
public class UserManagerController {
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/getLoginPage")
	public String getLoginPage(){
		return "login";
	}
	
	@RequestMapping(value="/getUserpage")
	public String getUserpage(){
		return "user_list";
	}
	@RequestMapping(value="pageList")
	@ResponseBody
	public ServerResponse<List<User>> pageList(Integer page, Integer limit){
		return userService.pageList(page,limit);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public ServerResponse login(String username,String password){
		ServerResponse<User> response = userService.login(username,password);
		System.out.println(username+" "+ password);
//		if (response.isSuccess()) {
//			User user = response.getData();
//			System.out.println(response.getData());
//		}
		//System.err.println(response.toString());
		return response;
		
	}
}
