package com.situ.mall.core.service;

import com.situ.mall.conmon.response.ServerResponse;

public interface ICategoryService {

	ServerResponse selectTopCategory();

	ServerResponse selectSecondCategory(Integer topCategoryId);

	Integer selectParentCategoryId(Integer categoryId);

}
