package com.forever.zhb.utils.jfree;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;

import com.forever.zhb.vo.StatisticsResultVO;

public class JFreeUtil {
    
    public static final String OUT_IMAGE_PATH    = "/log/download/jfree/";
    
    public static final String IMAGE_FORMAT = "jpeg";
    
    public static final String IMAGE_LINE_NAME = "line." + IMAGE_FORMAT;
    public static final String IMAGE_PIE_NAME = "pie." + IMAGE_FORMAT;
    public static final String IMAGE_BAR_NAME = "bar." + IMAGE_FORMAT;
    
    public static final int IMAGE_WIDTH = 600;
    public static final int IMAGE_HEIGHT = 600;

    public static final Font TITLE_FONT = new Font("宋体", Font.BOLD, 20);
    public static final Font LABEL_FONT = new Font("宋体", Font.PLAIN, 15);
    public static final Font ITEM_FONT = new Font("宋体", Font.PLAIN, 15);

    public static final Color BLUE_COLOR = new Color(46, 198, 201);
    
    public static final int JFREE_PIE_DEFAULT_NUM = 20;
    
    public static void generatorStatistics(List<StatisticsResultVO> vos,String title,String x_type,String y_type,String pathId,String statisticsOption,int max) throws IOException{
        line(vos, title, x_type, y_type, pathId, statisticsOption,max);
        pie(vos, title, pathId, statisticsOption);
        bar(vos, title, x_type, y_type, pathId, statisticsOption,max);
    }
    
    // 折线图
    public static void line(List<StatisticsResultVO> vos,String title,String x_type,String y_type,String pathId,String statisticsOption,int max) throws IOException {
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        if (null != vos && !vos.isEmpty()) {
            for (StatisticsResultVO vo : vos) {
                dcd.setValue(Double.valueOf(vo.getCount()), "数据", vo.getTitle());
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
            //na.setAutoRangeMinimumSize(100);// 最小跨度
            na.setLowerBound(0);// 最小值显示
            if (max != 0) {
              na.setUpperBound(max);//最大值显示
            }
            
            //线条
            LineAndShapeRenderer lasp = (LineAndShapeRenderer) plot.getRenderer();
            lasp.setBaseShapesVisible(true);// 设置拐点是否可见/是否显示拐点
            lasp.setDrawOutlines(true);// 设置拐点不同用不同的形状
            lasp.setUseFillPaint(true);// 设置线条是否被显示填充颜色
            lasp.setBaseFillPaint(Color.BLACK);//// 设置拐点颜色
            lasp.setSeriesPaint(0, BLUE_COLOR);//绿色
            
            //显示持久化
            String filePath = OUT_IMAGE_PATH + pathId ;
            if (StringUtils.isNotBlank(statisticsOption)) {
                filePath += "/" + statisticsOption;
            }
            File file = new File(filePath);
            file.mkdirs();
            filePath += "/" + IMAGE_LINE_NAME ;
            file = new File(filePath);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            } else {
                file.createNewFile();
            }
            ChartUtilities.saveChartAsJPEG(file, chart, IMAGE_WIDTH, IMAGE_HEIGHT);
        }
    }

    // 饼状图
    public static void pie(List<StatisticsResultVO> vos, String title,String pathId,String statisticsOption) throws IOException {
        // 如果不使用Font,中文将显示不出来
        DefaultPieDataset dpd = new DefaultPieDataset();
        if (null != vos && !vos.isEmpty()) {
            for (StatisticsResultVO nameValueVO : vos) {
                dpd.setValue(nameValueVO.getTitle(), Double.valueOf(nameValueVO.getCount()));
            }
        }
        // 图表的标题;需要提供对应图表的DateSet对象;是否显示图例;是否生成贴士;是否生成URL链接
        JFreeChart chart = ChartFactory.createPieChart(title, dpd, true, false, true);
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

        String filePath = OUT_IMAGE_PATH + pathId ;
        if (StringUtils.isNotBlank(statisticsOption)) {
            filePath += "/" + statisticsOption;
        }
        File file = new File(filePath);
        file.mkdirs();
        filePath += "/" + IMAGE_PIE_NAME ;
        file = new File(filePath);
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        } else {
            file.createNewFile();
        }

        // 将内存中的图片写到本地硬盘
        ChartUtilities.saveChartAsJPEG(file, chart, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    //柱状图
    public static void bar(List<StatisticsResultVO> vos,String title,String x_type,String y_type,String pathId,String statisticsOption,int max) throws IOException {
        if (null != vos && !vos.isEmpty()) {
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            for (StatisticsResultVO vo : vos) {
                dcd.setValue(Double.valueOf(vo.getCount()), "数据", vo.getTitle());
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
            //na.setAutoRangeMinimumSize(100);// 最小跨度
            na.setLowerBound(0);// 最小值显示
            if (max != 0) {
                na.setUpperBound(max);//最大值显示
              }
            
            //显示持久化
            String filePath = OUT_IMAGE_PATH + pathId ;
            if (StringUtils.isNotBlank(statisticsOption)) {
                filePath += "/" + statisticsOption;
            }
            File file = new File(filePath);
            file.mkdirs();
            filePath += "/" + IMAGE_BAR_NAME ;
            file = new File(filePath);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            } else {
                file.createNewFile();
            }
            ChartUtilities.saveChartAsJPEG(file, chart, IMAGE_WIDTH, IMAGE_HEIGHT);
        }
    }

}
