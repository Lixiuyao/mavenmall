package com.situ.mall.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.service.ICategoryService;
@Controller
@RequestMapping("/catergory")
public class CatergoryManagerController {
	@Autowired
	private ICategoryService categoryService;
	@RequestMapping(value="/selectTopCategory")
	@ResponseBody
	public ServerResponse selectTopCategory(){
		return categoryService.selectTopCategory();
		
	}
	@RequestMapping(value="/selectSecondCategory")
	@ResponseBody
	public ServerResponse selectSecondCategory(Integer topCategoryId){
		System.err.println(topCategoryId+"1321123");
		return categoryService.selectSecondCategory(topCategoryId);
		
	}
}
