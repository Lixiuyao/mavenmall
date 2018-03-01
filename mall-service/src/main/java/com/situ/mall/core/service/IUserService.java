package com.situ.mall.core.service;

import java.util.List;

import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.entity.User;

public interface IUserService {

	ServerResponse<User> login(String username, String password);

	ServerResponse<List<User>> pageList(Integer page, Integer limit);
	
}
