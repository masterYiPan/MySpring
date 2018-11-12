package com.yipan.ext;

import java.lang.reflect.Field;

import com.yipan.annotation.ExtResource;


public class AttriAssign {
	
	//依赖注入注解原理
	public  AttriAssign(Object object) throws IllegalArgumentException, IllegalAccessException {
		//1.使用反射机制获取当前类的所有属性
		Class<? extends Object> classInfo = object.getClass();
		Field[] declaredFields = classInfo.getDeclaredFields();
		//2.判断当前类是否存在注解
		for (Field field : declaredFields) {
		ExtResource extResource = field.getAnnotation(ExtResource.class);
		if(extResource!=null) {
			//获取属性名称
			String beanId = field.getType().getSimpleName();
			Object bean = ClassPathXmlApplicationContext.beans.get(beanId);
				if(bean!=null) {
					//3.默认使用属性名称查找bean容器对象 1参数当前对象2.参数给属性赋值
					field.setAccessible(true);//允许访问私有属性
					field.set(object, bean);
				}
			}			
		}
	}
}
