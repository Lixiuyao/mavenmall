package com.situ.mall.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.mall.core.entity.Order;
import com.situ.mall.core.mapper.OrderMapper;
import com.situ.mall.core.service.IOrderService;
@Service
public class OrderServiceImpl implements IOrderService {
	@Autowired
	private OrderMapper orderMapper;
	@Override
	public void add(Order order) {
		orderMapper.insertSelective(order);
	}

}
