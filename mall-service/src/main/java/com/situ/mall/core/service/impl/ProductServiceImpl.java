package com.situ.mall.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.entity.Product;
import com.situ.mall.core.mapper.ProductMapper;
import com.situ.mall.core.service.IProductService;
@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	private ProductMapper productMapper;
	@Override
	public ServerResponse<List<Product>> pageList(Integer page, Integer limit,Product product) {
		//1.设置分页
		PageHelper.startPage(page,limit);
		System.out.println(product.getStatus());
		List<Product> list = productMapper.pageList(product);
		//第二种分页
		PageInfo pageInfo = new PageInfo<>(list);
		Integer count = (int) pageInfo.getTotal();
		
		return ServerResponse.createSuccess("查询成功", count,list);
	}
	@Override
	public ServerResponse deleteById(Integer id) {
		
		int count = productMapper.deleteByPrimaryKey(id);
		if (count>0) {
			return ServerResponse.createSuccess("删除成功");
		}
		return ServerResponse.createError("删除失败"); 
	}
	@Override
	public ServerResponse deleteAll(String ids) {
		String[] idsArray = ids.split(",");
		int count = productMapper.deleteAll(idsArray);
		if (count ==idsArray.length) {
			return ServerResponse.createSuccess("批量删除成功");
		}
		return ServerResponse.createError("批量删除失败");
	}
	@Override
	public ServerResponse add(Product product) {
		int count = productMapper.insert(product);
		if (count>0) {
			return ServerResponse.createSuccess("添加成功");
		}
		return  ServerResponse.createError("添加失敗");
	}
	@Override
	public Product selectById(Integer id) {
		Product product = productMapper.selectByPrimaryKey(id);
		return product;
	}
	@Override
	public ServerResponse update(Product product) {
		int rowCount = productMapper.updateByPrimaryKeySelective(product);
		if (rowCount > 0) {
			return ServerResponse.createSuccess("更新商品成功");
		}
		return ServerResponse.createError("更新商品失败");
	}
	@Override
	public List<Product> selectBycategoryId(Integer categoryId) {
		List<Product> list = productMapper.selectBycategoryId(categoryId);
		return list;
	}

}
