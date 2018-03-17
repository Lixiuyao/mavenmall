package com.situ.mall.portal.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.constant.Const;
import com.situ.mall.core.entity.Order;
import com.situ.mall.core.entity.OrderItem;
import com.situ.mall.core.entity.Product;
import com.situ.mall.core.entity.User;
import com.situ.mall.core.service.IOrderItemService;
import com.situ.mall.core.service.IOrderService;
import com.situ.mall.core.service.IProductService;
import com.situ.mall.portal.vo.CartItemVo;
import com.situ.mall.portal.vo.CartVo;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private IOrderService  orderService;
	@Autowired
	private IOrderItemService  orderItemService;
	@Autowired
	private IProductService productService;

	@RequestMapping("/addOrder")
	@ResponseBody
	public ServerResponse addOrder(HttpSession session,
			Integer shippingId,BigDecimal totalprice,
			Integer paytype,Integer postage,HttpServletRequest request ,HttpServletResponse response) {
	
		//1.创建订单对象
		Order order = new Order();
		//设置当前时间为订单编号
		Long cuzz = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(cuzz);
		order.setOrderNo(Long.parseLong(format.format(date)));
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		order.setUserId(user.getId());
		order.setShippingId(shippingId);
		order.setPayment(totalprice);
		order.setPaymentType(paytype);
		order.setPostage(postage);
		order.setSendTime(new Date());
		order.setCreateTime(new Date());
		order.setEndTime(new Date());
		//2,将订单插入数据库
		orderService.add(order);
		//3.从cookie中得到购物车CartVo
		CartVo cartVo= Common.getCartVoFromCookie(request);
		List<CartItemVo> cartItemVos = cartVo.getCartItemVos();
		for (CartItemVo cartItemVo : cartItemVos) {
			//购物车里面被选中的才加入数据库
			if (cartItemVo.getIsChecked() == Const.CartChecked.CHECKED) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderNo(order.getOrderNo());
				orderItem.setUserId(user.getId());
				Product product = productService.selectById(cartItemVo.getProduct().getId());
				orderItem.setProductId(product.getId());
				orderItem.setProductName(product.getName());
				orderItem.setProductImage(product.getMainImage());
				orderItem.setCurrentUnitPrice(product.getPrice());
				orderItem.setQuantity(cartItemVo.getAmount());
				orderItem.setTotalPrice(totalprice);
				orderItem.setCreateTime(new Date());
				orderItem.setUpdateTime(new Date());
				orderItemService.addOrderItem(orderItem);
			}
			}
	      //4.遍历cartVo将所有isChecked是1的删除，然后再写到cookie
		Iterator<CartItemVo> iterator = cartItemVos.iterator();
		while (iterator.hasNext()) {
			CartItemVo cartItemVo = iterator.next();
			if (1 == cartItemVo.getIsChecked()) {
				iterator.remove();
			}
			
		}
		Common.setCartVoToCookie(response, cartVo);
		
		return ServerResponse.createSuccess("订单完成");
		
	}
	@RequestMapping("/getpay")
	public String getpay(HttpSession session,Model model){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		model.addAttribute("user", user);
		//Order order =orderService.selectOrderByUserId(user.getId()); 
		//List<OrderItem> orderItem = orderItemService.selectByOrderNo(order.getOrderNo());
		//model.addAttribute("orderItem", orderItem);
		return "my-pay";
		
	}
	@RequestMapping("/allOrder")
	public String allOrder(HttpSession session,Model model){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		model.addAttribute("user", user);
		List<Order> list = orderService.selectByUserId(user.getId());
		for (Order order : list) {
			System.err.println(order);
		}
		model.addAttribute("list", list);
		return "my-dingdan";
		
	}
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		Long cuzz = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(cuzz);
		System.out.println(date);
		System.out.println(Long.parseLong(format.format(date)));
	}
	
}
