package com.forever.zhb.design.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.xml.bind.Binder;

public class HomeLink implements InvocationHandler {
	
	private Object object;
	
	public Object bind(Object object){
		this.object = object;
		Class clazz = this.object.getClass();
		return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		method.invoke(this.object, args);
		return null;
	}

}
