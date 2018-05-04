package com.forever.zhb.utils.jfree.factory;

import java.util.ArrayList;
import java.util.List;

import com.forever.zhb.utils.jfree.bean.JFreeDraw;
import com.forever.zhb.utils.jfree.bean.impl.JFreeDrawBar;
import com.forever.zhb.utils.jfree.bean.impl.JFreeDrawLine;
import com.forever.zhb.utils.jfree.bean.impl.JFreeDrawPie;

public abstract class JFreeFactory {
	
	public abstract JFreeDraw getJFreeDraw();
	
	public abstract List<JFreeDraw> getAllJFreeDraw();
	
	public static List<JFreeDraw> getAllJFreeDrawStatic(){
		List<JFreeDraw> draws = new ArrayList<JFreeDraw>();
		draws.add(new JFreeDrawLine());
		draws.add(new JFreeDrawPie());
		draws.add(new JFreeDrawBar());
		return draws;
	}

}
