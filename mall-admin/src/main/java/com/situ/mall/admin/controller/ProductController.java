package com.situ.mall.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.entity.Product;
import com.situ.mall.core.service.ICategoryService;
import com.situ.mall.core.service.IProductService;
@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private IProductService productService;
	@Autowired
	private ICategoryService categoryService;
	
	@RequestMapping(value="/getproduct")
	public String getEditPage(){
		return "product_list";
		
	}
	@RequestMapping(value="/getEditPage")
	public String getUserpage(Integer id,Model model){
		Product product = productService.selectById(id);
		model.addAttribute("product",product);
		Integer parentCategoryId = categoryService.selectParentCategoryId(product.getCategoryId());
		model.addAttribute("parentCategoryId",parentCategoryId);
		return "product_edit";
	}
	@RequestMapping(value="pageList")
	@ResponseBody
	public ServerResponse<List<Product>> pageList(Integer page, Integer limit,Product product){
		return productService.pageList(page,limit,product);
	}
	
	@RequestMapping(value="/deleteById")
	@ResponseBody
	public ServerResponse deleteById(Integer id){
		return productService.deleteById(id);
	}
	@RequestMapping(value="/getAdd")
	public String getAdd(){
		return "product_add";
	}
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public ServerResponse deleteAll(String ids){
		
		
		return productService.deleteAll(ids);
	}
	@RequestMapping(value="/add")
	@ResponseBody
	public ServerResponse add(Product product){
		return productService.add(product);
	}
	@RequestMapping(value="/update")
	@ResponseBody
	public ServerResponse update(Product product){
		return productService.update(product);
	}
}
