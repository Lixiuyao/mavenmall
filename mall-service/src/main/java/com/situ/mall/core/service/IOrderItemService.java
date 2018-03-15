package com.situ.mall.core.service;

import java.util.List;

import com.situ.mall.core.entity.OrderItem;

public interface IOrderItemService {

	void addOrderItem(OrderItem orderItem);

	List<OrderItem> selectByOrderNo(Long orderNo);

}
