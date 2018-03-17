package com.situ.mall.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.entity.Category;
import com.situ.mall.core.mapper.CategoryMapper;
import com.situ.mall.core.service.ICategoryService;
import com.situ.mall.core.vo.CategoryCountVo;
@Controller
@RequestMapping("/category")
public class CatergoryController {
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private CategoryMapper categoryMapper;
	
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
	
	@RequestMapping(value="/getcategorypage")
	public String getcategorypage(){
		return "category_list";
	}
	
	@RequestMapping(value="/updatePage")
	public String updatePage(Integer id,Model model){
		List<Category> list = categoryMapper.selectSecondCategory(id);
		model.addAttribute("list", list);
		return "category_edit";
	}

	@RequestMapping(value="/getAdd")
	public String getAdd(){
		return "category_add";
	}
	
	@RequestMapping(value="/getAddTwo")
	public String getAddTwo(){
		return "category_add1";
	}
	
	
	@RequestMapping(value="/add")
	@ResponseBody
	public ServerResponse add(Category category){
		
		if (category.getName()==null&&category.getName()=="") {
			return ServerResponse.createError("名字不能为空或者是不规则语言");
		}
		return categoryService.add(category);
	}
	
	@RequestMapping("/getCountAnalysisPage")
	public String getCountAnalysisPage(){
		return "category_count_Ecarts";
	}
	
	@RequestMapping("/getCategoryCountAnalysis")
	@ResponseBody
	public  ServerResponse getCategoryCountAnalysis(){
		List<CategoryCountVo> list =categoryService.getCategoryCountAnalysis();

		if (list==null) {
			return ServerResponse.createError("查询失败或者没有此数据");
		}
		return ServerResponse.createSuccess("查询成功", list);
	}
	
}
