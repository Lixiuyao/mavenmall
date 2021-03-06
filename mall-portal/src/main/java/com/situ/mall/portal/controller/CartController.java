package com.situ.mall.portal.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.constant.Const;
import com.situ.mall.core.entity.Product;
import com.situ.mall.core.service.IProductService;
import com.situ.mall.portal.vo.CartItemVo;
import com.situ.mall.portal.vo.CartVo;

@Controller
@RequestMapping("/cart")
public class CartController {
	private  String CART_COOKIE = "cart_cookie";
	
	@Autowired
	private IProductService productService; 
	
	@RequestMapping("/getCartPage")
	public String  getCartPage(HttpServletRequest request,Model model) {
		
		CartVo cartVo = getCartVoFromCookie(request);
		if (cartVo!=null) {
			List<CartItemVo> cartItemVos = cartVo.getCartItemVos();
			for (CartItemVo item : cartItemVos) {
				Product product = productService.selectById(item.getProduct().getId());
				item.setProduct(product);
			}
			//将Cart对象放到域对象中
			model.addAttribute("cartVo", cartVo);
		}
		

		
		return "product_cart";
	}
	
	@RequestMapping("/addCart")
	@ResponseBody
	public ServerResponse addCart(Integer productId,Integer amount,HttpServletRequest request,
			HttpServletResponse response,Model model){
		
		
		CartVo cartVo = getCartVoFromCookie(request);
		
		if (cartVo == null) {
			cartVo = new CartVo();
		}
		
		if (null != productId) {
			CartItemVo cartItemVo = new CartItemVo();
			Product productTemp = productService.selectById(productId);
			Product product = new Product();
			product.setId(productId);
			product.setStock(productTemp.getStock());
			cartItemVo.setProduct(product);
			cartItemVo.setIsChecked(Const.CartChecked.UNCHECKD);
			cartItemVo.setAmount(amount);
			cartVo.addItem(cartItemVo);
			
			setCartVoToCookie(response, cartVo);
		}
		
		
		return ServerResponse.createSuccess("添加购物车成功");
	}
	
	
	@RequestMapping("/updateCart")
	@ResponseBody
	public ServerResponse updateCart(Integer productId,Integer amount,HttpServletRequest request,
			HttpServletResponse response,Model model){
		
		
		CartVo cartVo = getCartVoFromCookie(request);
		
		if (cartVo == null) {
			cartVo = new CartVo();
		}
		
		if (null != productId) {
			CartItemVo cartItemVo = new CartItemVo();
			Product productTemp = productService.selectById(productId);
			Product product = new Product();
			product.setId(productId);
			product.setStock(productTemp.getStock());
			cartItemVo.setProduct(product);
			cartItemVo.setIsChecked(Const.CartChecked.UNCHECKD);
			cartItemVo.setAmount(amount);
			cartVo.addItem(cartItemVo);
			
			setCartVoToCookie(response, cartVo);
		}
		
		
		return ServerResponse.createSuccess("添加购物车成功");
	}
	
	
	
	@RequestMapping("/addOrUpdateCart")
	@ResponseBody
	public ServerResponse addOrUpdateCart(Integer productId,Integer amount,Boolean isChecked,HttpServletRequest request,HttpServletResponse response){
		// 将Cookie里面的购物车转换为CartVo对象
		CartVo cartVo = getCartVoFromCookie(request);
		if (cartVo == null) {
			cartVo = new CartVo();
		}
		boolean result= addOrUpdateCarVo(productId, amount, isChecked, cartVo);
		if (result == false) {
			return ServerResponse.createError("添加购物车失败");
		}
		//将CartVo对象设置到Cookie中
		setCartVoToCookie(response, cartVo);
		return ServerResponse.createSuccess("添加购物车成功");
	}
	
	
	@RequestMapping("/delCartItemById")
	@ResponseBody
	public ServerResponse delCartItemById(Integer productId,HttpServletRequest request,HttpServletResponse response){
		// 将Cookie里面的购物车转换为CartVo对象
		CartVo cartVo = getCartVoFromCookie(request);
		if (cartVo == null) {
			return ServerResponse.createError("获取购物车不大行");
		}
		List<CartItemVo> cartItemVos = cartVo.getCartItemVos();
		Iterator<CartItemVo> iterator = cartItemVos.iterator();
		while (iterator.hasNext()) {
			CartItemVo cartItemVo = iterator.next();
			if (productId.intValue() == cartItemVo.getProduct().getId().intValue()) {
				iterator.remove();
			}
			
		}
		setCartVoToCookie(response, cartVo);
		
		return ServerResponse.createSuccess("删除购物车成功");
		
	}
	 
	@RequestMapping("/selectAllPrice")
	@ResponseBody
	public ServerResponse  selectAllPrice(Boolean isChecked,HttpServletRequest request,HttpServletResponse response){
		// 将Cookie里面的购物车转换为CartVo对象
			CartVo cartVo = getCartVoFromCookie(request);
			if (cartVo == null) {
				return ServerResponse.createError("获取购物车不大行");
			}
			List<CartItemVo> cartItemVos = cartVo.getCartItemVos();
			if (isChecked == true) {
				
				for (CartItemVo cartItemVo : cartItemVos) {
					cartItemVo.setIsChecked(Const.CartChecked.CHECKED);
				}	
				}
			else {
				for (CartItemVo cartItemVo : cartItemVos) {
					cartItemVo.setIsChecked(Const.CartChecked.UNCHECKD);
				}
			}
			
				
			setCartVoToCookie(response, cartVo);
			
		
			return ServerResponse.createSuccess("成功");
		
	}

	private boolean addOrUpdateCarVo(Integer productId, Integer amount, Boolean isChecked, CartVo cartVo) {
		Product productTemp = productService.selectById(productId);
		boolean isExist = false;
		List<CartItemVo> cartItemVos = cartVo.getCartItemVos();
		for (CartItemVo item : cartItemVos) {
			// 1.1 这个商品cookie里面已经有了，根据productId找到这件商品，更新数量即可
			if (item.getProduct().getId().intValue() == productId.intValue()) {
				isExist = true;
				if (amount != null) {
					//这个商品新的数量=原来购物车中这个商品数量+新添加这个商品的数量
					int newAmount = item.getAmount()+amount;
					if (newAmount>productTemp.getStock()) {
						return false;
					}
					item.setAmount(newAmount);
				}
				if (isChecked != null) {
					if (isChecked) {
						item.setIsChecked(Const.CartChecked.CHECKED);
					} else {
						item.setIsChecked(Const.CartChecked.UNCHECKD);
					}
				}
				return true;
			}}
			//在原来的购物车中就没有这件商品，直接添加
			if (isExist == false) {
				CartItemVo cartItemVo = new CartItemVo();
				Product product = new Product();
				product.setId(productId);
				cartItemVo.setProduct(product);
				cartItemVo.setIsChecked(Const.CartChecked.CHECKED);
				cartItemVo.setAmount(amount);
				
				cartItemVos.add(cartItemVo);
				return true;
			}
		
			return false;
	
	}

	private void setCartVoToCookie(HttpServletResponse response, CartVo cartVo) {
		ObjectMapper objectMapper = new ObjectMapper();
		// 只有对象中不为null才转换
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		//将cartVo对象以json形式放到cookie
		StringWriter stringWriter = new StringWriter();
		try {
			objectMapper.writeValue(stringWriter, cartVo);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//将购物车json放到cookie
		Cookie cookie = new Cookie(CART_COOKIE, stringWriter.toString());
		//设置存储时间
		cookie.setMaxAge(60*60*24);
		//设置cookie路径
		cookie.setPath("/");
		//将cookie发送到浏览器
		response.addCookie(cookie);
	}
	
	
	public  CartVo getCartVoFromCookie(HttpServletRequest request) {
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
