package com.forever.zhb.utils.jfree.factory.bean;

import java.util.ArrayList;
import java.util.List;

import com.forever.zhb.utils.jfree.bean.JFreeDraw;
import com.forever.zhb.utils.jfree.bean.impl.JFreeDrawBar;
import com.forever.zhb.utils.jfree.bean.impl.JFreeDrawLine;
import com.forever.zhb.utils.jfree.bean.impl.JFreeDrawPie;
import com.forever.zhb.utils.jfree.factory.JFreeFactory;

public class JFreeLineFactory extends JFreeFactory {

	@Override
	public JFreeDraw getJFreeDraw() {
		return new JFreeDrawLine();
	}
	
	@Override
	public List<JFreeDraw> getAllJFreeDraw(){
		List<JFreeDraw> draws = new ArrayList<JFreeDraw>();
		draws.add(new JFreeDrawLine());
		return draws;
	}

}
