package com.situ.mall.core.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.mall.core.entity.OrderItem;
import com.situ.mall.core.mapper.OrderItemMapper;
import com.situ.mall.core.service.IOrderItemService;

@Service
public class OrderItemServiceImpl implements IOrderItemService {
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Override
	public void addOrderItem(OrderItem orderItem) {
		orderItemMapper.insertSelective(orderItem);
	}
	
}
