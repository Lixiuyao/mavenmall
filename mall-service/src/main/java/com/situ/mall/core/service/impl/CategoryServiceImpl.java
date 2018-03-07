package com.situ.mall.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.entity.Category;
import com.situ.mall.core.mapper.CategoryMapper;
import com.situ.mall.core.service.ICategoryService;
@Service
public class CategoryServiceImpl implements ICategoryService{
	@Autowired
	private CategoryMapper categoryMapper;
	@Override
	public ServerResponse selectTopCategory() {
		List<Category> list = categoryMapper.selectTopCategory();
		if (list==null||list.size()==0) {
			return ServerResponse.createError("没有一级菜单");
		}
		return ServerResponse.createSuccess("查询成功", list);
	}
	@Override
	public ServerResponse selectSecondCategory(Integer topCategoryId) {
		System.err.println(topCategoryId);
		List<Category> list = categoryMapper.selectSecondCategory(topCategoryId);
		System.err.println(list.size());
		if (list==null||list.size()==0) {
			return ServerResponse.createError("没有二级菜单");
		}
		return ServerResponse.createSuccess("查询成功", list);
	}
	@Override
	public Integer selectParentCategoryId(Integer categoryId) {
		
		return categoryMapper.selectParentCategoryId(categoryId);
	}
	@Override
	public ServerResponse add(Category category) {
		int count = categoryMapper.insert(category);
		if (count>0) {
			return ServerResponse.createSuccess("新增成功");
		}
		return ServerResponse.createError("新增失败");
	}
	@Override
	public List<Category> selectTopCategoryList() {
		return categoryMapper.selectTopCategory();
	}
	@Override
	public List<Category> selectSecondCategoryList() {
		return categoryMapper.selectSecondCategorys();
	}
	
	
}
