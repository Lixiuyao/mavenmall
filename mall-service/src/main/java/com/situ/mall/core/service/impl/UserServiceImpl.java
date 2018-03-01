package com.situ.mall.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.entity.User;
import com.situ.mall.core.mapper.UserMapper;
import com.situ.mall.core.service.IUserService;
@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserMapper userMapper;
	@Override
	public ServerResponse<User> login(String username, String password) {
		int resultCount = userMapper.checkUsername(username);
		if (resultCount == 0) {
			return ServerResponse.createError("用户名不存在");
		}
	//	String md5Password=MD5Util.EncodeUtf8(password);
		User user = userMapper.selectLogin(username,password);
		if (user==null) {
			return ServerResponse.createError("密码错误");
		}
		user.setPassword(StringUtils.EMPTY);
		return ServerResponse.createSuccess("登录成功", user);
	}
	@Override
	public ServerResponse<List<User>> pageList(Integer page, Integer limit) {
		//1.设置分页
		PageHelper.startPage(page, limit);
		//2.执行查询（分页了）
		List<User> list = userMapper.pageList();
		Integer count = (int) ((Page) list).getTotal();
		return ServerResponse.createSuccess("查询成功", count, list);
	}
	
}
