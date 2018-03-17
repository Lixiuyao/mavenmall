package com.situ.mall.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.mall.conmon.response.ServerResponse;
import com.situ.mall.core.entity.Order;
import com.situ.mall.core.entity.OrderItem;
import com.situ.mall.core.service.IOrderItemService;
import com.situ.mall.core.service.IOrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderItemService orderItemService;
	
	
	@RequestMapping("/getorderpage")
	public String getorderpage(){
		return "order_list";
	}
	
	@RequestMapping("/selectAllOrder")
	@ResponseBody
	public ServerResponse selectAllOrder(){
		List<Order> list = orderService.selectAllOrder();
		if (list.size()>0) {
				return ServerResponse.createSuccess("查找成功", list);
		}
		return null;
	
	}
	
	@RequestMapping("/toOrderItemm")
	public String toOrderItemm(Long orderNo,Model model){
		System.out.println(orderNo);
//		List<OrderItem> list = orderItemService.selectByOrderNo(orderNo);
		model.addAttribute("orderNo", orderNo);
		return "order_see";
	
	}
	
	@RequestMapping("/OrderItemm")
	@ResponseBody
	public ServerResponse OrderItemm(Long orderNo,Model model){
		System.out.println(orderNo);
		List<OrderItem> list = orderItemService.selectByOrderNo(orderNo);
//		model.addAttribute("list", list);
		if (list.size()>0) {
			return ServerResponse.createSuccess("查找成功", list);
		}
		return null;
		
	
	}
	
	
	
	
	
	}