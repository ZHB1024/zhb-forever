package com.forever.zhb.controller.annotation.test;

public class Integer {
	
	private final int value ;
	
	public Integer(int value){
		this.value = value;
	}
	
	public static String toString(int i) {
        return new String("this is a test " + i);
    }


}
