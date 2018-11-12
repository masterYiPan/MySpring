package com.yipan;

import com.yipan.ext.ClassPathXmlApplicationContext;
import com.yipan.service.UserService;

public class Test001 {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("com.yipan");
		UserService userService = (UserService)app.getBean(UserService.class);
		userService.add();
	}
	

}
