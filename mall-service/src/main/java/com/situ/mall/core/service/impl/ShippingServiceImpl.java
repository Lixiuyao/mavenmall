package com.situ.mall.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.mall.core.entity.Shipping;
import com.situ.mall.core.mapper.ShippingMapper;
import com.situ.mall.core.service.IShippingService;
@Service
public class ShippingServiceImpl implements IShippingService {
	@Autowired
	public ShippingMapper shippingMapper;
	@Override
	public Shipping findByUserId(Integer id) {
		
		return shippingMapper.selectByPrimaryUserKey(id);
	}

}
