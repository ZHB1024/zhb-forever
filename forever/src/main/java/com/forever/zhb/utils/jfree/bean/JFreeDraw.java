package com.forever.zhb.utils.jfree.bean;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.List;

import com.forever.zhb.vo.NameValueVO;

public interface JFreeDraw {
	
	public static final String OUT_LINE_GRAPH = "E:/chart/line.jpeg";
	public static final String OUT_PIE_GRAPH = "E:/chart/pie.jpeg";
	public static final String OUT_BAR_GRAPH = "E:/chart/bar.jpeg";

	public static final Font TITLE_FONT = new Font("黑体", Font.BOLD, 20);
	public static final Font LABEL_FONT = new Font("宋体", Font.PLAIN, 15);
	public static final Font ITEM_FONT = new Font("宋体", Font.PLAIN, 15);
	
	public static final int IMAGE_WIDTH = 500;
	public static final int IMAGE_HEIGHT = 300;

	public static final Color BLUE_COLOR = new Color(46, 198, 201);
	
	public int draw(List<NameValueVO> vos,String title,String x_type,String y_type) throws IOException;

}
