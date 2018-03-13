package com.situ.mall.portal.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.constant.Const;
import com.situ.mall.core.entity.Category;
import com.situ.mall.core.entity.Product;
import com.situ.mall.core.entity.Shipping;
import com.situ.mall.core.entity.User;
import com.situ.mall.core.mapper.CategoryMapper;
import com.situ.mall.core.service.ICategoryService;
import com.situ.mall.core.service.IProductService;
import com.situ.mall.core.service.IShippingService;
import com.situ.mall.core.service.IUserService;
import com.situ.mall.portal.vo.CartItemVo;
import com.situ.mall.portal.vo.CartVo;

@Controller
public class IndexController {
	@Autowired
	public ICategoryService categoryService;
	@Autowired
	public IShippingService shippingService;
	@Autowired
	public IProductService productService;
	
	@RequestMapping("index")
	public String index(Model model){
		
		List<Category> OneList = categoryService.selectTopCategoryList();
		model.addAttribute("OneList", OneList);
		List<Category> TwoList = categoryService.selectSecondCategoryList();
		model.addAttribute("TwoList", TwoList); 
		return "index";
	}
	
	
	@RequestMapping("order")
	public String order(HttpSession session,Model model,HttpServletRequest request){
		//获得User对象从Session中
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		//2.通过user得到收货地址
		Shipping shipping = shippingService.findByUserId(user.getId()); 
		model.addAttribute("shipping", shipping);
		model.addAttribute("user", user);
		//3.将Cookie里面的购物车信息转换为CartVo对象
			CartVo cartVo = Common.getCartVoFromCookie(request);
		double sum=0;
		//将CartItemVo里面的Product填满信息，因为现在只有一个id
		List<CartItemVo> cartItemVos = cartVo.getCartItemVos();
		for (CartItemVo cartItemVo : cartItemVos) {
			//if(cartItemVo.getIsChecked()==1){
				Product product = productService.selectById(cartItemVo.getProduct().getId());
			//	System.out.println(product);
				cartItemVo.setProduct(product);
			//	BigDecimal price = product.getPrice();
			//	Integer amount = cartItemVo.getAmount();
			//	sum+=price.doubleValue()*amount;
		//	}
			
		}
		model.addAttribute("cartVo", cartVo);
		//model.addAttribute("sum", sum);
		
		return "order";
	}
	

	
}
