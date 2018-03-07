package com.situ.mall.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.entity.Product;
import com.situ.mall.core.entity.User;
import com.situ.mall.core.mapper.UserMapper;
import com.situ.mall.core.service.IUserService;
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping(value="/getLoginPage")
	public String getLoginPage(){
		return "login";
	}
	
	@RequestMapping(value="/getUserpage")
	public String getUserpage(){
		return "user_list";
	}
	
	@RequestMapping(value="/updatePage")
	public String updatePage(Integer id,Model model){
		User user = userMapper.selectByPrimaryKey(id);
		model.addAttribute("user", user);
		
		
		return "user_edit";
	}
	@RequestMapping(value="pageList")
	@ResponseBody
	public ServerResponse<List<User>> pageList(Integer page, Integer limit,User user){
		return userService.pageList(page,limit,user);
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
	
	@RequestMapping(value="deleteById")
	@ResponseBody
	public ServerResponse deleteById(Integer id){
		return userService.deleteById(id);
	}
	
	@RequestMapping(value="deleteAll")
	@ResponseBody
	public ServerResponse deleteAll(String ids){
		return userService.deleteAll(ids);
	}

	@RequestMapping(value="/getAdd")
	public String getAdd(){
		return "user_add";
	}
	
	@RequestMapping(value="/add")
	@ResponseBody
	public ServerResponse add(User user){
		return userService.add(user);
	}
	@RequestMapping(value="/update")
	@ResponseBody
	public ServerResponse update(User user){
		return userService.update(user);
	}
}
