package com.forever.zhb.controller.annotation.test;

public class StaticTest {
	
	{
		System.out.println("I am ordinary code block ");
	}
	
	static{
		System.out.println("I am static code block ");
	}
	
	public StaticTest(){
		System.out.println(" instance StaticTest ");
	}
	
	public static int VALUE01 =100;
	public static final int VALUE02 =100;
	
	public static String VALUE10 = "100";
	public static final String VALUE20 = "100";
	
	public static void staticMethod(){
		System.out.println("I am static method");
	}
	
	public static StaticTest getInstance(){
		return InnerStaticTest.instance;
	}

	private static class InnerStaticTest{
		{
			System.out.println("I am inner ordinary code block ");
		}
		static{
			System.out.println("I am inner static code block ");
		}
		
		private static final StaticTest instance = new StaticTest();
		
	}
	
	
	public static void main(String[] args) {
    }
}
