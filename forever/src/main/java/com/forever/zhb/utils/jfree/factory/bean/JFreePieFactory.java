package com.forever.zhb.utils.jfree.factory.bean;

import java.util.ArrayList;
import java.util.List;

import com.forever.zhb.utils.jfree.bean.JFreeDraw;
import com.forever.zhb.utils.jfree.bean.impl.JFreeDrawPie;
import com.forever.zhb.utils.jfree.factory.JFreeFactory;

public class JFreePieFactory extends JFreeFactory {

	@Override
	public JFreeDraw getJFreeDraw() {
		return new JFreeDrawPie();
	}
	
	public List<JFreeDraw> getAllJFreeDraw(){
		List<JFreeDraw> draws = new ArrayList<JFreeDraw>();
		draws.add(new JFreeDrawPie());
		return draws;
	}

}
