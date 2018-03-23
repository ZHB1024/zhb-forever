package com.forever.zhb.design.pattern.singleton;

public class Singleton {
	
	private Singleton(){}
	
	private static class SingletonFactory{
		private static final Singleton singletion = new Singleton();
	}
	
	public static Singleton getInstance(){
		return SingletonFactory.singletion;
	}

}
