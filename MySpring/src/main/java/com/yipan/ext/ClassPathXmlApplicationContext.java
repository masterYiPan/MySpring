package com.yipan.ext;

import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import com.yipan.annotation.ExtService;
import com.yipan.utils.ClassUtil;

//��дspringIOCע��汾
public class ClassPathXmlApplicationContext {
	private String packageName;//ɨ���ķ�Χ
	public static ConcurrentHashMap<String, Object> beans = null;//bean����
	
	public ClassPathXmlApplicationContext(String packageName) throws Exception {
		this.packageName = packageName;
		beans = new ConcurrentHashMap<String,Object>();
		initBean();
		initEntryField();
	}
	//��ʼ������
	public void initEntryField() throws IllegalArgumentException, IllegalAccessException {
		for (Entry<String,Object> entry : beans.entrySet()) {//1.�������е�bean��������
			Object object = entry.getValue();
			new AttriAssign(object);
		}
	}
	public Object getBean(String beanId) throws Exception {
		if(beanId == null)
			throw new Exception("beanId����Ϊ��");
		Object object = beans.get(beanId);//ʹ��������ȡbean
		return object;
	}
	//��ʼ������
	public void initBean() throws Exception {
		List<Class<?>> classes = ClassUtil.getClasses(packageName);//1.ʹ��Java������ƽ���ɨ������ȡ��ǰ����������
		ConcurrentHashMap<String, Object> classExisAnnotation = findClassExisAnnotation(classes);//2.�ж������Ƿ����ע��bean��ע��
		if(classExisAnnotation == null || classExisAnnotation.isEmpty())
			throw new Exception("�ð���û���κ������ע��");
	}
	public ConcurrentHashMap<String, Object> findClassExisAnnotation(List<Class<?>> classes) throws InstantiationException, IllegalAccessException {
		for (Class<?> classInfo : classes) {
			ExtService annotation = classInfo.getAnnotation(ExtService.class);
			if(annotation!=null) {
				Class<?>[] interfaces = classInfo.getInterfaces();//��ȡ��ǰ��ʵ�ֵĽӿ�
				Object newInstance = classInfo.newInstance();
				for (Class<?> class1 : interfaces) {
					beans.put(class1.getSimpleName(), newInstance);
				}
			}	
		}
		return beans;
	}
}
