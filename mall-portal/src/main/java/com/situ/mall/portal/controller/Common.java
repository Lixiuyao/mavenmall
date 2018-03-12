package com.situ.mall.portal.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.situ.mall.portal.vo.CartVo;

public class Common  {
	private static  String CART_COOKIE = "cart_cookie";
	
	static CartVo getCartVoFromCookie(HttpServletRequest request) {
		ObjectMapper objectMapper = new ObjectMapper();
		// 只有对象中不为null才转换
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		CartVo cartVo = null;
		//2、将cookie里面所有的商品查询出来，转换成Cart这个对象
		//将客户端中购物车cookie拿出来
				Cookie[] cookies = request.getCookies();
				if (null!=cookies &&cookies.length!=0) {
					for (Cookie cookie : cookies) {
						//这俩一定一样吗
						if (CART_COOKIE.equals(cookie.getName())) {
							String value =cookie.getValue();
							try {
								//将json字符串转换为对象
								cartVo = objectMapper.readValue(value, CartVo.class);
							} catch (IOException e) {
								e.printStackTrace();
							}
							
						}
					}
				}
		return cartVo;
	}
}
