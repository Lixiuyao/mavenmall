package com.situ.mall.core.service.impl;

import java.util.List;

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
	@Override
	public Order selectOrderByUserId(Integer id) {
		return orderMapper.selectByUserId(id);
	}
	@Override
	public List<Order> selectByUserId(Integer id) {
		return orderMapper.selectByUserrId(id);
	}

}
