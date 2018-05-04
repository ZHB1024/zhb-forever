package com.forever.zhb.utils.jfree.factory.bean;

import java.util.ArrayList;
import java.util.List;

import com.forever.zhb.utils.jfree.bean.JFreeDraw;
import com.forever.zhb.utils.jfree.bean.impl.JFreeDrawBar;
import com.forever.zhb.utils.jfree.factory.JFreeFactory;

public class JFreeBarFactory extends JFreeFactory {

	@Override
	public JFreeDraw getJFreeDraw() {
		return new JFreeDrawBar();
	}
	
	public List<JFreeDraw> getAllJFreeDraw(){
		List<JFreeDraw> draws = new ArrayList<JFreeDraw>();
		draws.add(new JFreeDrawBar());
		return draws;
	}

}
