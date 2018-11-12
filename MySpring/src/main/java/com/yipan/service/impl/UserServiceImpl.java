package com.yipan.service.impl;


import com.yipan.annotation.ExtResource;
import com.yipan.annotation.ExtService;
import com.yipan.service.OrderService;
import com.yipan.service.UserService;

@ExtService
public class UserServiceImpl implements UserService {
	
	@ExtResource
	private OrderService orderServiceImpl1;

	public void add() {
		orderServiceImpl1.addOrder();
		System.out.println("################");
	}

}
