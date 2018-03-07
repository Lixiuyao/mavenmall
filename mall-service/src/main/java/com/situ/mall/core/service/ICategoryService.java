package com.situ.mall.core.service;

import java.util.List;

import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.entity.Category;

public interface ICategoryService {

	ServerResponse selectTopCategory();

	ServerResponse selectSecondCategory(Integer topCategoryId);

	Integer selectParentCategoryId(Integer categoryId);

	ServerResponse add(Category category);

	List<Category> selectTopCategoryList();

	List<Category> selectSecondCategoryList();

	

}
