package com.situ.mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.situ.mall.core.entity.Category;
import com.situ.mall.core.mapper.CategoryMapper;
import com.situ.mall.core.service.ICategoryService;

@Controller
public class IndexController {
	@Autowired
	public ICategoryService categoryService;
	@RequestMapping("index")
	public String index(Model model){
		
		List<Category> OneList = categoryService.selectTopCategoryList();
		model.addAttribute("OneList", OneList);
		List<Category> TwoList = categoryService.selectSecondCategoryList();
		model.addAttribute("TwoList", TwoList); 
		return "index";
		
	}
	@RequestMapping("login")
	public String login(){
		
		return "login";
		
	}
	
}
