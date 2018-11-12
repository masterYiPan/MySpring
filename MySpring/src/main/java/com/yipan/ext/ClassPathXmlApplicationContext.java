package com.yipan.ext;

import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import com.yipan.annotation.ExtService;
import com.yipan.utils.ClassUtil;

//手写springIOC注解版本
public class ClassPathXmlApplicationContext {
	private String packageName;//扫包的范围
	public static ConcurrentHashMap<String, Object> beans = null;//bean容器
	
	public ClassPathXmlApplicationContext(String packageName) throws Exception {
		this.packageName = packageName;
		beans = new ConcurrentHashMap<String,Object>();
		initBean();
		initEntryField();
	}
	//初始化属性
	public void initEntryField() throws IllegalArgumentException, IllegalAccessException {
		for (Entry<String,Object> entry : beans.entrySet()) {//1.遍历所有的bean容器对象
			Object object = entry.getValue();
			new AttriAssign(object);
		}
	}
	public Object getBean(String beanId) throws Exception {
		if(beanId == null)
			throw new Exception("beanId不能为空");
		Object object = beans.get(beanId);//使用容器获取bean
		return object;
	}
	//初始化对象
	public void initBean() throws Exception {
		List<Class<?>> classes = ClassUtil.getClasses(packageName);//1.使用Java反射机制进行扫包，获取当前包下所有类
		ConcurrentHashMap<String, Object> classExisAnnotation = findClassExisAnnotation(classes);//2.判断类上是否存在注入bean的注解
		if(classExisAnnotation == null || classExisAnnotation.isEmpty())
			throw new Exception("该包下没有任何类加上注解");
	}
	public ConcurrentHashMap<String, Object> findClassExisAnnotation(List<Class<?>> classes) throws InstantiationException, IllegalAccessException {
		for (Class<?> classInfo : classes) {
			ExtService annotation = classInfo.getAnnotation(ExtService.class);
			if(annotation!=null) {
				Class<?>[] interfaces = classInfo.getInterfaces();//获取当前类实现的接口
				Object newInstance = classInfo.newInstance();
				for (Class<?> class1 : interfaces) {
					beans.put(class1.getSimpleName(), newInstance);
				}
			}	
		}
		return beans;
	}
}
