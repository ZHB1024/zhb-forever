package com.forever.zhb.utils.jfree.bean.impl;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import com.forever.zhb.dic.JFreeDrawTypeEnum;
import com.forever.zhb.utils.jfree.bean.JFreeDraw;
import com.forever.zhb.vo.NameValueVO;

public class JFreeDrawBar implements JFreeDraw {

	@Override
	public int draw(List<NameValueVO> vos, String title, String x_type, String y_type) throws IOException {
		if (null != vos && !vos.isEmpty()) {
			DefaultCategoryDataset dcd = new DefaultCategoryDataset();
			for (NameValueVO vo : vos) {
				dcd.setValue(Double.valueOf(vo.getValue()), "张会彬", vo.getName());
			}
			
			JFreeChart chart = ChartFactory.createBarChart3D(title, x_type, y_type, dcd);
			
			//设置整个图片的标题字体
			chart.getTitle().setFont(TITLE_FONT);
			//设置提示条字体
			chart.getLegend().setItemFont(ITEM_FONT);
			
			//得到绘图区
			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			
			//设置绘图区
			plot.setBackgroundPaint(Color.white);//背景颜色
			plot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线
			//plot.setOutlinePaint(Color.RED);//边界线
			
			// 显示每个柱的数值，并修改该数值的字体属性  
	        BarRenderer3D renderer= (BarRenderer3D)plot.getRenderer(); 
	        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
	        renderer.setBaseItemLabelsVisible(true);  
	        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));  
	        renderer.setItemLabelAnchorOffset(10D);    
	        // 设置平行柱的之间距离  
	        renderer.setItemMargin(0.4);  
	        renderer.setSeriesPaint(0, BLUE_COLOR);
			
			//X-横轴
			CategoryAxis categoryAxis = plot.getDomainAxis();
			//设置标签的字体
			categoryAxis.setLabelFont(LABEL_FONT);
			//设置坐标轴标尺值字体
			categoryAxis.setTickLabelFont(LABEL_FONT);
			//防止最后边的一个数据靠近了坐标轴。
			categoryAxis.setLowerMargin(0.01);// 左边距 边框距离
			categoryAxis.setUpperMargin(0.06);// 右边距 边框距离
			//横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
			//categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			
			//Y-纵轴
			NumberAxis na = (NumberAxis) plot.getRangeAxis();
			//设置标签的字体
			na.setLabelFont(LABEL_FONT);
			na.setUpperMargin(0.1);
			na.setLowerMargin(0.1);
			na.setAutoRangeMinimumSize(1000);// 最小跨度
			//na.setLowerBound(0);// 最小值显示
			//na.setUpperBound(1);//最大值显示
			
			//显示持久化
			File file = new File(OUT_BAR_GRAPH);
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			} else {
				file.createNewFile();
			}
			ChartUtilities.saveChartAsJPEG(file, chart, IMAGE_WIDTH, IMAGE_HEIGHT);
			return JFreeDrawTypeEnum.BAR.getIndex();
		}
		return JFreeDrawTypeEnum.LINE.getIndex();
	}

}
