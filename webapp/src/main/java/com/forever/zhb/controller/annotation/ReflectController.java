package com.forever.zhb.controller.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reflectController")
public class ReflectController {
	
	protected Logger logger = LoggerFactory.getLogger(ReflectController.class);
	
	@RequestMapping("/toReflect")
	public void toReflect(){
		Person person = new Person("张三","男",18);
		printFields(person);
		printMethods(person);
		
		Class c = person.getClass();
		try {
			Method method1 = c.getMethod("sayName");
			Object object = method1.invoke(person);
			logger.info("syaName:{}",object.toString());
			
			Method method2 = c.getMethod("sayLove",new Class[]{String.class});
			logger.info("syaLove:{}",method2.invoke(person,"小红"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void printMethods(Object object){
		Class c = object.getClass();
		Method[] methods = c.getMethods();
		if (null != methods) {
			for (Method method : methods) {
				StringBuilder sb = new StringBuilder();
				Class returnType = method.getReturnType();
				String mentodName = method.getName();
				Class[] paras = method.getParameterTypes();
				Parameter[] parameters = method.getParameters();
				sb.append("public " + returnType.getName());
				sb.append(" " + mentodName + "(");
				if (null != parameters && parameters.length > 0) {
					for (Parameter parameter : parameters) {
						sb.append(parameter.getType().getName() + " " + parameter.getName() + ",");
					}
					sb.substring(0, sb.lastIndexOf(","));
				}
				sb.append(")");
				logger.info(sb.toString());
			}
		}
	}
	
	private void printFields(Object object){
		Class c = object.getClass();
		Field[] fields = c.getDeclaredFields();
		if (null != fields) {
			for (Field field : fields) {
				StringBuilder sb = new StringBuilder();
				sb.append("private");
				sb.append(" " + field.getType().getName());
				sb.append(" " + field.getName());
				logger.info(sb.toString());
			}
		}
		
	}
	
	class Person{
		private String name;
		private String sex;
		private int age;
		
		public Person(){
			
		}
		
		public Person(String name,String sex,int age){
			this.name = name;
			this.sex = sex;
			this.age = age;
		}
		
		public String sayName(){
			return "I am " + name ;
		}
		
		public String sayLove(String girl){
			return "I love " + girl;
		}
	}
}
