package com.yipan.ext;

import java.lang.reflect.Field;

import com.yipan.annotation.ExtResource;


public class AttriAssign {
	
	//����ע��ע��ԭ��
	public  AttriAssign(Object object) throws IllegalArgumentException, IllegalAccessException {
		//1.ʹ�÷�����ƻ�ȡ��ǰ�����������
		Class<? extends Object> classInfo = object.getClass();
		Field[] declaredFields = classInfo.getDeclaredFields();
		//2.�жϵ�ǰ���Ƿ����ע��
		for (Field field : declaredFields) {
		ExtResource extResource = field.getAnnotation(ExtResource.class);
		if(extResource!=null) {
			//��ȡ��������
			String beanId = field.getType().getSimpleName();
			Object bean = ClassPathXmlApplicationContext.beans.get(beanId);
				if(bean!=null) {
					//3.Ĭ��ʹ���������Ʋ���bean�������� 1������ǰ����2.���������Ը�ֵ
					field.setAccessible(true);//�������˽������
					field.set(object, bean);
				}
			}			
		}
	}
}
