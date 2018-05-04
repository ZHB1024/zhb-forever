package com.forever.zhb.utils.jfree.bean.impl;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.forever.zhb.dic.JFreeDrawTypeEnum;
import com.forever.zhb.utils.jfree.bean.JFreeDraw;
import com.forever.zhb.vo.NameValueVO;

public class JFreeDrawPie implements JFreeDraw {

	@Override
	public int draw(List<NameValueVO> vos, String title, String x_type, String y_type) throws IOException {
		if (null != vos && !vos.isEmpty()) {
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
			plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1}票)/{2}", NumberFormat.getNumberInstance(),
					new DecimalFormat("0.00%")));

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
			ChartUtilities.saveChartAsJPEG(file, chart, IMAGE_WIDTH, IMAGE_HEIGHT);
			return JFreeDrawTypeEnum.PIE.getIndex();
		}
		return JFreeDrawTypeEnum.LINE.getIndex();
	}

}
