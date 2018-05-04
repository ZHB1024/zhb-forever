package com.forever.zhb.utils;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import com.forever.zhb.dic.JFreeDrawTypeEnum;
import com.forever.zhb.utils.jfree.bean.JFreeDraw;
import com.forever.zhb.utils.jfree.factory.JFreeFactory;
import com.forever.zhb.utils.jfree.factory.bean.JFreeBarFactory;
import com.forever.zhb.utils.jfree.factory.bean.JFreeLineFactory;
import com.forever.zhb.utils.jfree.factory.bean.JFreePieFactory;
import com.forever.zhb.vo.NameValueVO;

public class JFreeUtil {
	
	public static List<JFreeDraw> getJFreeDraw(String drawType){
		if (StringUtil.isBlank(drawType)) {
			return new JFreeLineFactory().getAllJFreeDraw();
		}else if(drawType.equals(String.valueOf(JFreeDrawTypeEnum.LINE.getIndex()))){
			return new JFreeLineFactory().getAllJFreeDraw();
		}else if(drawType.equals(String.valueOf(JFreeDrawTypeEnum.PIE.getIndex()))){
			return new JFreePieFactory().getAllJFreeDraw();
		}else if(drawType.equals(String.valueOf(JFreeDrawTypeEnum.BAR.getIndex()))){
			return new JFreeBarFactory().getAllJFreeDraw();
		}else if(drawType.equals(String.valueOf(JFreeDrawTypeEnum.ALL.getIndex()))){
			return JFreeFactory.getAllJFreeDrawStatic();
		}
		return new JFreeLineFactory().getAllJFreeDraw();
	}
	
	public static String getFilePath(String drawType){
		if (StringUtil.isBlank(drawType)) {
			return OUT_LINE_GRAPH;
		}else if(drawType.equals(String.valueOf(JFreeDrawTypeEnum.LINE.getIndex()))){
			return OUT_LINE_GRAPH;
		}else if(drawType.equals(String.valueOf(JFreeDrawTypeEnum.PIE.getIndex()))){
			return OUT_PIE_GRAPH;
		}else if(drawType.equals(String.valueOf(JFreeDrawTypeEnum.BAR.getIndex()))){
			return OUT_BAR_GRAPH;
		}
		return OUT_LINE_GRAPH;
	}
	
	public static final String OUT_LINE_GRAPH = "E:/chart/line.jpeg";
	public static final String OUT_PIE_GRAPH = "E:/chart/pie.jpeg";
	public static final String OUT_BAR_GRAPH = "E:/chart/bar.jpeg";

	public static final Font TITLE_FONT = new Font("黑体", Font.BOLD, 20);
	public static final Font LABEL_FONT = new Font("宋体", Font.PLAIN, 15);
	public static final Font ITEM_FONT = new Font("宋体", Font.PLAIN, 15);

	public static final Color BLUE_COLOR = new Color(46, 198, 201);
	// 折线图
	public static void line(List<NameValueVO> vos,String title,String x_type,String y_type) throws IOException {
		/*StandardChartTheme mChartTheme = new StandardChartTheme("CN");
		mChartTheme.setLargeFont(TITLE_FONT);
		mChartTheme.setExtraLargeFont(LABEL_FONT);
		mChartTheme.setRegularFont(LABEL_FONT);
		ChartFactory.setChartTheme(mChartTheme);*/
		
		DefaultCategoryDataset dcd = new DefaultCategoryDataset();
		if (null != vos && !vos.isEmpty()) {
			for (NameValueVO vo : vos) {
				dcd.setValue(Double.valueOf(vo.getValue()), "张会彬", vo.getName());
			}
			JFreeChart chart = ChartFactory.createLineChart(title, x_type, y_type, dcd,
					                          PlotOrientation.VERTICAL, 
					                          true, true, false);
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
			
			
			//线条
			LineAndShapeRenderer lasp = (LineAndShapeRenderer) plot.getRenderer();
			lasp.setBaseShapesVisible(true);// 设置拐点是否可见/是否显示拐点
			lasp.setDrawOutlines(true);// 设置拐点不同用不同的形状
			lasp.setUseFillPaint(true);// 设置线条是否被显示填充颜色
			lasp.setBaseFillPaint(Color.BLACK);//// 设置拐点颜色
			lasp.setSeriesPaint(0, BLUE_COLOR);//绿色
			
			//显示持久化
			File file = new File(OUT_LINE_GRAPH);
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			} else {
				file.createNewFile();
			}
			ChartUtilities.saveChartAsJPEG(file, chart, 1000, 600);
		}
	}

	public static void line1() throws IOException {
		// 首先构造数据
		TimeSeries timeSeries = new TimeSeries("BMI", Month.class);
		// 时间曲线数据集合
		TimeSeriesCollection lineDataset = new TimeSeriesCollection();
		// 构造数据集合
		timeSeries.add(new Month(1, 2009), 45);
		timeSeries.add(new Month(2, 2009), 46);
		timeSeries.add(new Month(3, 2009), 1);
		timeSeries.add(new Month(4, 2009), 500);
		timeSeries.add(new Month(5, 2009), 43);
		timeSeries.add(new Month(6, 2009), 324);
		timeSeries.add(new Month(7, 2009), 632);
		timeSeries.add(new Month(8, 2009), 34);
		timeSeries.add(new Month(9, 2009), 12);
		timeSeries.add(new Month(10, 2009), 543);
		timeSeries.add(new Month(11, 2009), 32);
		timeSeries.add(new Month(12, 2009), 225);
		lineDataset.addSeries(timeSeries);
		JFreeChart chart = ChartFactory.createTimeSeriesChart("", "date", "bmi", lineDataset, true, true, true);
		// 增加标题
		chart.setTitle(new TextTitle("XXXBMI指数", new Font("隶书", Font.ITALIC, 15)));
		chart.setAntiAlias(true);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setAxisOffset(new RectangleInsets(10, 10, 10, 10));// 图片区与坐标轴的距离
		plot.setOutlinePaint(Color.PINK);
		plot.setInsets(new RectangleInsets(15, 15, 15, 15));// 坐标轴与最外延的距离
		// plot.setOrientation(PlotOrientation.HORIZONTAL);//图形的方向，包括坐标轴。
		AxisSpace as = new AxisSpace();
		as.setLeft(25);
		as.setRight(25);
		plot.setFixedRangeAxisSpace(as);
		chart.setPadding(new RectangleInsets(5, 5, 5, 5));
		chart.setNotify(true);
		// 设置曲线是否显示数据点
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) plot.getRenderer();
		xylineandshaperenderer.setBaseShapesVisible(true);
		// 设置曲线显示各数据点的值
		XYItemRenderer xyitem = plot.getRenderer();
		xyitem.setBaseItemLabelsVisible(true);
		xyitem.setBasePositiveItemLabelPosition(
				new ItemLabelPosition(ItemLabelAnchor.INSIDE10, TextAnchor.BASELINE_LEFT));
		xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 14));
		plot.setRenderer(xyitem);
		// 显示
		File file = new File(OUT_LINE_GRAPH);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		} else {
			file.createNewFile();
		}
		ChartUtilities.saveChartAsJPEG(file, chart, 800, 500);
		/*
		 * ChartFrame frame = new ChartFrame("try1", chart); frame.pack();
		 * frame.setVisible(true);
		 */
	}

	// 饼状图
	public static void pie(List<NameValueVO> vos, String title) throws IOException {
		// 如果不使用Font,中文将显示不出来
		DefaultPieDataset dpd = new DefaultPieDataset();
		if (null != vos && !vos.isEmpty()) {
			for (NameValueVO nameValueVO : vos) {
				dpd.setValue(nameValueVO.getName(), Double.valueOf(nameValueVO.getValue()));
			}
		}
		// 图表的标题;需要提供对应图表的DateSet对象;是否显示图例;是否生成贴士;是否生成URL链接
		JFreeChart chart = ChartFactory.createPieChart3D(title, dpd, true, false, true);
		chart.getTitle().setFont(TITLE_FONT);// 设置图片标题的字体
		chart.getLegend().setItemFont(ITEM_FONT);// 设置图例项目字体

		PiePlot plot = (PiePlot) chart.getPlot();// 得到图块,准备设置标签的字体
		plot.setLabelFont(LABEL_FONT);// 设置标签字体
		plot.setForegroundAlpha(0.7f);// 设置plot的前景色透明度
		plot.setBackgroundAlpha(0.0f);// 设置plot的背景色透明度

		// 设置标签生成器(默认{0})
		// {0}:key {1}:value {2}:百分比 {3}:sum
		plot.setNoDataMessage("无数据可供显示！"); // 没有数据的时候显示的内容  
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1}票)/{2}",NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")));

		/**
		 * 设置开始角度(弧度计算) 0° 30° 45° 60° 90° 120° 135° 150° 180° 270° 360° 弧度 0
		 * π/6 π/4 π/3 π/2 2π/3 3π/4 5π/6 π 3π/2 2π
		 */
		plot.setStartAngle(new Float(3.14f / 2f));

		// 设置分裂效果,需要指定分裂出去的key
		// plot.setExplodePercent("天使-彦", 0.1);

		// 设置背景图片,设置最大的背景
		/*
		 * Image img = ImageIO.read(new File("f:/test/1.jpg"));
		 * chart.setBackgroundImage(img);
		 */

		// 设置plot的背景图片
		/*
		 * img = ImageIO.read(new File("f:/test/2.jpg"));
		 * plot.setBackgroundImage(img);
		 */

		File file = new File(OUT_PIE_GRAPH);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		} else {
			file.createNewFile();
		}

		// 将内存中的图片写到本地硬盘
		ChartUtilities.saveChartAsJPEG(file, chart, 1000, 600);
	}

	public static void pie1() throws IOException {

		StandardChartTheme sct = new StandardChartTheme("CN");
		sct.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		sct.setRegularFont(new Font("隶书", Font.BOLD, 20));
		sct.setLargeFont(new Font("隶书", Font.BOLD, 20));

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("苹果", 100);
		dataset.setValue("梨子", 200);
		dataset.setValue("葡萄", 300);
		dataset.setValue("香蕉", 400);
		dataset.setValue("荔枝", 500);

		ChartFactory.setChartTheme(sct);

		JFreeChart jfreechart = ChartFactory.createPieChart3D("水果产量图", dataset, true, true, true);

		File file = new File(OUT_PIE_GRAPH);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		} else {
			file.createNewFile();
		}
		ChartUtilities.saveChartAsJPEG(file, jfreechart, 800, 500);

	}
	
	

	//柱状图
	public static void barGraph(List<NameValueVO> vos,String title,String x_type,String y_type) throws IOException {
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
			ChartUtilities.saveChartAsJPEG(file, chart, 1000, 600);
		}
	}
	
	
	public static void barGraph1() throws IOException {
		// 构造数据
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(100, "JAVA", "1");
		dataset.addValue(200, "js", "1");
		dataset.addValue(200, "C++", "2");
		dataset.addValue(300, "C", "3");
		dataset.addValue(400, "HTML", "4");
		dataset.addValue(400, "CSS", "5");
		/**
		 * public static JFreeChart createBarChart3D( java.lang.String title,
		 * 设置图表的标题 java.lang.String categoryAxisLabel, 设置分类轴的标示 java.lang.String
		 * valueAxisLabel, 设置值轴的标示 CategoryDataset dataset, 设置数据 PlotOrientation
		 * orientation, 设置图表的方向 boolean legend, 设置是否显示图例 boolean tooltips,
		 * 设置是否生成热点工具 boolean urls) 设置是否显示url
		 */
		JFreeChart chart = ChartFactory.createBarChart3D("编程语言统计", "语言", "学习人数", dataset, PlotOrientation.VERTICAL,
				true, false, false);
		// 保存图表
		File file = new File(OUT_BAR_GRAPH);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		} else {
			file.createNewFile();
		}
		ChartUtilities.saveChartAsJPEG(file, chart, 800, 500);
	}

	public static void main(String[] args) throws IOException {

		String title = "选项占比统计图";
		List<NameValueVO> vos = new ArrayList<NameValueVO>();
		vos.add(new NameValueVO("张三", "100"));
		vos.add(new NameValueVO("李四", "90000"));
		vos.add(new NameValueVO("王二", "6000"));
		vos.add(new NameValueVO("网五", "200000"));
		vos.add(new NameValueVO("码子", "343"));
		
		//折线图
	    line(vos, title,"","");

		//饼状图
		pie(vos, title);

		//柱状图
		barGraph(vos, title,"","");
	}

}
