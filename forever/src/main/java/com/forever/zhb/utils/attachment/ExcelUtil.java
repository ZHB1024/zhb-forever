package com.forever.zhb.utils.attachment;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil {
	
	public static void createExcelHead(HSSFWorkbook wb,HSSFSheet sheet){
		HSSFCellStyle defaultHeaderStyle = getHeaderStyle(wb);
		HSSFFont fonttitle = wb.createFont();
		fonttitle.setColor(HSSFColor.BLACK.index);
		fonttitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fonttitle.setFontName("宋体");

		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell firstCell00 = firstRow.createCell(0);
		HSSFCell firstCell01 = firstRow.createCell(1);
		HSSFCell firstCell02 = firstRow.createCell(2);
		HSSFCell firstCell03 = firstRow.createCell(3);
		HSSFCell firstCell04 = firstRow.createCell(4);
		HSSFCell firstCell05 = firstRow.createCell(5);
		HSSFCell firstCell06 = firstRow.createCell(6);
		HSSFCell firstCell07 = firstRow.createCell(7);
		HSSFCell firstCell08 = firstRow.createCell(8);
		HSSFCell firstCell09 = firstRow.createCell(9);
		HSSFCell firstCell10 = firstRow.createCell(10);
		HSSFCell firstCell11 = firstRow.createCell(11);
		HSSFCell firstCell12 = firstRow.createCell(12);
		HSSFCell firstCell13 = firstRow.createCell(13);
		HSSFCell firstCell14 = firstRow.createCell(14);
		HSSFCell firstCell15 = firstRow.createCell(15);
		HSSFCell firstCell16 = firstRow.createCell(16);
		HSSFCell firstCell17 = firstRow.createCell(17);
		HSSFCell firstCell18 = firstRow.createCell(18);
		HSSFCell firstCell19 = firstRow.createCell(19);
		HSSFCell firstCell20 = firstRow.createCell(20);
		HSSFCell firstCell21 = firstRow.createCell(21);
		HSSFCell firstCell22 = firstRow.createCell(22);
		HSSFCell firstCell23 = firstRow.createCell(23);
		HSSFCell firstCell24 = firstRow.createCell(24);
		HSSFCell firstCell25 = firstRow.createCell(25);
		HSSFCell firstCell26 = firstRow.createCell(26);
		HSSFCell firstCell27 = firstRow.createCell(27);
		HSSFCell firstCell28 = firstRow.createCell(28);
		HSSFCell firstCell29 = firstRow.createCell(29);
		HSSFCell firstCell30 = firstRow.createCell(30);
		HSSFCell firstCell31 = firstRow.createCell(31);
		HSSFCell firstCell32 = firstRow.createCell(32);
		HSSFCell firstCell33 = firstRow.createCell(33);
		HSSFCell firstCell34 = firstRow.createCell(34);

		firstCell00.setCellStyle(defaultHeaderStyle);
		firstCell01.setCellStyle(defaultHeaderStyle);
		firstCell02.setCellStyle(defaultHeaderStyle);
		firstCell03.setCellStyle(defaultHeaderStyle);
		firstCell04.setCellStyle(defaultHeaderStyle);
		firstCell05.setCellStyle(defaultHeaderStyle);
		firstCell06.setCellStyle(defaultHeaderStyle);
		firstCell07.setCellStyle(defaultHeaderStyle);
		firstCell08.setCellStyle(defaultHeaderStyle);
		firstCell09.setCellStyle(defaultHeaderStyle);
		firstCell10.setCellStyle(defaultHeaderStyle);
		firstCell11.setCellStyle(defaultHeaderStyle);
		firstCell12.setCellStyle(defaultHeaderStyle);
		firstCell13.setCellStyle(defaultHeaderStyle);
		firstCell14.setCellStyle(defaultHeaderStyle);
		firstCell15.setCellStyle(defaultHeaderStyle);
		firstCell16.setCellStyle(defaultHeaderStyle);
		firstCell17.setCellStyle(defaultHeaderStyle);
		firstCell18.setCellStyle(defaultHeaderStyle);
		firstCell19.setCellStyle(defaultHeaderStyle);
		firstCell20.setCellStyle(defaultHeaderStyle);
		firstCell21.setCellStyle(defaultHeaderStyle);
		firstCell22.setCellStyle(defaultHeaderStyle);
		firstCell23.setCellStyle(defaultHeaderStyle);
		firstCell24.setCellStyle(defaultHeaderStyle);
		firstCell25.setCellStyle(defaultHeaderStyle);
		firstCell26.setCellStyle(defaultHeaderStyle);
		firstCell27.setCellStyle(defaultHeaderStyle);
		firstCell28.setCellStyle(defaultHeaderStyle);
		firstCell29.setCellStyle(defaultHeaderStyle);
		firstCell30.setCellStyle(defaultHeaderStyle);
		firstCell31.setCellStyle(defaultHeaderStyle);
		firstCell32.setCellStyle(defaultHeaderStyle);
		firstCell32.setCellStyle(defaultHeaderStyle);
		firstCell33.setCellStyle(defaultHeaderStyle);
		firstCell34.setCellStyle(defaultHeaderStyle);

		firstCell05.setCellValue("基础工程现场进度表");
		firstCell19.setCellValue("组塔工程现场进度表");
		firstCell30.setCellValue("架线施工现场进度表");

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 18));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 19, 29));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 30, 34));

		HSSFRow secondRow = sheet.createRow(1);
		HSSFCell secondCell00 = secondRow.createCell(0);
		HSSFCell secondCell01 = secondRow.createCell(1);
		HSSFCell secondCell02 = secondRow.createCell(2);
		HSSFCell secondCell03 = secondRow.createCell(3);
		HSSFCell secondCell04 = secondRow.createCell(4);
		HSSFCell secondCell05 = secondRow.createCell(5);
		HSSFCell secondCell06 = secondRow.createCell(6);
		HSSFCell secondCell07 = secondRow.createCell(7);
		HSSFCell secondCell08 = secondRow.createCell(8);
		HSSFCell secondCell09 = secondRow.createCell(9);
		HSSFCell secondCell10 = secondRow.createCell(10);
		HSSFCell secondCell11 = secondRow.createCell(11);
		HSSFCell secondCell12 = secondRow.createCell(12);
		HSSFCell secondCell13 = secondRow.createCell(13);
		HSSFCell secondCell14 = secondRow.createCell(14);
		HSSFCell secondCell15 = secondRow.createCell(15);
		HSSFCell secondCell16 = secondRow.createCell(16);
		HSSFCell secondCell17 = secondRow.createCell(17);
		HSSFCell secondCell18 = secondRow.createCell(18);
		HSSFCell secondCell19 = secondRow.createCell(19);
		HSSFCell secondCell20 = secondRow.createCell(20);
		HSSFCell secondCell21 = secondRow.createCell(21);
		HSSFCell secondCell22 = secondRow.createCell(22);
		HSSFCell secondCell23 = secondRow.createCell(23);
		HSSFCell secondCell24 = secondRow.createCell(24);
		HSSFCell secondCell25 = secondRow.createCell(25);
		HSSFCell secondCell26 = secondRow.createCell(26);
		HSSFCell secondCell27 = secondRow.createCell(27);
		HSSFCell secondCell28 = secondRow.createCell(28);
		HSSFCell secondCell29 = secondRow.createCell(29);
		HSSFCell secondCell30 = secondRow.createCell(30);
		HSSFCell secondCell31 = secondRow.createCell(31);
		HSSFCell secondCell32 = secondRow.createCell(32);
		HSSFCell secondCell33 = secondRow.createCell(33);
		HSSFCell secondCell34 = secondRow.createCell(34);

		secondCell00.setCellStyle(defaultHeaderStyle);
		secondCell01.setCellStyle(defaultHeaderStyle);
		secondCell02.setCellStyle(defaultHeaderStyle);
		secondCell03.setCellStyle(defaultHeaderStyle);
		secondCell04.setCellStyle(defaultHeaderStyle);
		secondCell05.setCellStyle(defaultHeaderStyle);
		secondCell06.setCellStyle(defaultHeaderStyle);
		secondCell07.setCellStyle(defaultHeaderStyle);
		secondCell08.setCellStyle(defaultHeaderStyle);
		secondCell09.setCellStyle(defaultHeaderStyle);
		secondCell10.setCellStyle(defaultHeaderStyle);
		secondCell11.setCellStyle(defaultHeaderStyle);
		secondCell12.setCellStyle(defaultHeaderStyle);
		secondCell13.setCellStyle(defaultHeaderStyle);
		secondCell14.setCellStyle(defaultHeaderStyle);
		secondCell15.setCellStyle(defaultHeaderStyle);
		secondCell16.setCellStyle(defaultHeaderStyle);
		secondCell17.setCellStyle(defaultHeaderStyle);
		secondCell18.setCellStyle(defaultHeaderStyle);
		secondCell19.setCellStyle(defaultHeaderStyle);
		secondCell20.setCellStyle(defaultHeaderStyle);
		secondCell21.setCellStyle(defaultHeaderStyle);
		secondCell22.setCellStyle(defaultHeaderStyle);
		secondCell23.setCellStyle(defaultHeaderStyle);
		secondCell24.setCellStyle(defaultHeaderStyle);
		secondCell25.setCellStyle(defaultHeaderStyle);
		secondCell26.setCellStyle(defaultHeaderStyle);
		secondCell27.setCellStyle(defaultHeaderStyle);
		secondCell28.setCellStyle(defaultHeaderStyle);
		secondCell29.setCellStyle(defaultHeaderStyle);
		secondCell30.setCellStyle(defaultHeaderStyle);
		secondCell31.setCellStyle(defaultHeaderStyle);
		secondCell32.setCellStyle(defaultHeaderStyle);
		secondCell33.setCellStyle(defaultHeaderStyle);
		secondCell34.setCellStyle(defaultHeaderStyle);

		/*
		 * secondCell05.setCellValue("基础基数(基)"); sheet.addMergedRegion(new
		 * CellRangeAddress(1,1,5,6));
		 */

		secondCell06.setCellValue("基坑开挖");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 11));

		secondCell12.setCellValue("基础浇筑");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 12, 17));

		secondCell19.setCellValue("杆塔基数");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 19, 20));

		secondCell21.setCellValue("组塔策划");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 21, 24));

		secondCell25.setCellValue("铁塔组立(基)");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 25, 29));

		secondCell30.setCellValue("折单统计");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 30, 33));

		HSSFRow thirdRow = sheet.createRow(2);
		HSSFCell thirdCell00 = thirdRow.createCell(0);
		HSSFCell thirdCell01 = thirdRow.createCell(1);
		HSSFCell thirdCell02 = thirdRow.createCell(2);
		HSSFCell thirdCell03 = thirdRow.createCell(3);
		HSSFCell thirdCell04 = thirdRow.createCell(4);
		HSSFCell thirdCell05 = thirdRow.createCell(5);
		HSSFCell thirdCell06 = thirdRow.createCell(6);
		HSSFCell thirdCell07 = thirdRow.createCell(7);
		HSSFCell thirdCell08 = thirdRow.createCell(8);
		HSSFCell thirdCell09 = thirdRow.createCell(9);
		HSSFCell thirdCell10 = thirdRow.createCell(10);
		HSSFCell thirdCell11 = thirdRow.createCell(11);
		HSSFCell thirdCell12 = thirdRow.createCell(12);
		HSSFCell thirdCell13 = thirdRow.createCell(13);
		HSSFCell thirdCell14 = thirdRow.createCell(14);
		HSSFCell thirdCell15 = thirdRow.createCell(15);
		HSSFCell thirdCell16 = thirdRow.createCell(16);
		HSSFCell thirdCell17 = thirdRow.createCell(17);
		HSSFCell thirdCell18 = thirdRow.createCell(18);
		HSSFCell thirdCell19 = thirdRow.createCell(19);
		HSSFCell thirdCell20 = thirdRow.createCell(20);
		HSSFCell thirdCell21 = thirdRow.createCell(21);
		HSSFCell thirdCell22 = thirdRow.createCell(22);
		HSSFCell thirdCell23 = thirdRow.createCell(23);
		HSSFCell thirdCell24 = thirdRow.createCell(24);
		HSSFCell thirdCell25 = thirdRow.createCell(25);
		HSSFCell thirdCell26 = thirdRow.createCell(26);
		HSSFCell thirdCell27 = thirdRow.createCell(27);
		HSSFCell thirdCell28 = thirdRow.createCell(28);
		HSSFCell thirdCell29 = thirdRow.createCell(29);
		HSSFCell thirdCell30 = thirdRow.createCell(30);
		HSSFCell thirdCell31 = thirdRow.createCell(31);
		HSSFCell thirdCell32 = thirdRow.createCell(32);
		HSSFCell thirdCell33 = thirdRow.createCell(33);
		HSSFCell thirdCell34 = thirdRow.createCell(34);

		thirdCell00.setCellStyle(defaultHeaderStyle);
		thirdCell01.setCellStyle(defaultHeaderStyle);
		thirdCell02.setCellStyle(defaultHeaderStyle);
		thirdCell03.setCellStyle(defaultHeaderStyle);
		thirdCell04.setCellStyle(defaultHeaderStyle);
		thirdCell05.setCellStyle(defaultHeaderStyle);
		thirdCell06.setCellStyle(defaultHeaderStyle);
		thirdCell07.setCellStyle(defaultHeaderStyle);
		thirdCell08.setCellStyle(defaultHeaderStyle);
		thirdCell09.setCellStyle(defaultHeaderStyle);
		thirdCell10.setCellStyle(defaultHeaderStyle);
		thirdCell11.setCellStyle(defaultHeaderStyle);
		thirdCell12.setCellStyle(defaultHeaderStyle);
		thirdCell13.setCellStyle(defaultHeaderStyle);
		thirdCell14.setCellStyle(defaultHeaderStyle);
		thirdCell15.setCellStyle(defaultHeaderStyle);
		thirdCell16.setCellStyle(defaultHeaderStyle);
		thirdCell17.setCellStyle(defaultHeaderStyle);
		thirdCell18.setCellStyle(defaultHeaderStyle);
		thirdCell19.setCellStyle(defaultHeaderStyle);
		thirdCell20.setCellStyle(defaultHeaderStyle);
		thirdCell21.setCellStyle(defaultHeaderStyle);
		thirdCell22.setCellStyle(defaultHeaderStyle);
		thirdCell23.setCellStyle(defaultHeaderStyle);
		thirdCell24.setCellStyle(defaultHeaderStyle);
		thirdCell25.setCellStyle(defaultHeaderStyle);
		thirdCell26.setCellStyle(defaultHeaderStyle);
		thirdCell27.setCellStyle(defaultHeaderStyle);
		thirdCell28.setCellStyle(defaultHeaderStyle);
		thirdCell29.setCellStyle(defaultHeaderStyle);
		thirdCell30.setCellStyle(defaultHeaderStyle);
		thirdCell31.setCellStyle(defaultHeaderStyle);
		thirdCell32.setCellStyle(defaultHeaderStyle);
		thirdCell33.setCellStyle(defaultHeaderStyle);
		thirdCell34.setCellStyle(defaultHeaderStyle);

		thirdCell25.setCellValue("整基完成(基)");
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 25, 26));

		// four
		HSSFRow fourthRow = sheet.createRow(3);
		HSSFCell fourthCell00 = fourthRow.createCell(0);
		HSSFCell fourthCell01 = fourthRow.createCell(1);
		HSSFCell fourthCell02 = fourthRow.createCell(2);
		HSSFCell fourthCell03 = fourthRow.createCell(3);
		HSSFCell fourthCell04 = fourthRow.createCell(4);
		HSSFCell fourthCell05 = fourthRow.createCell(5);
		HSSFCell fourthCell06 = fourthRow.createCell(6);
		HSSFCell fourthCell07 = fourthRow.createCell(7);
		HSSFCell fourthCell08 = fourthRow.createCell(8);
		HSSFCell fourthCell09 = fourthRow.createCell(9);
		HSSFCell fourthCell10 = fourthRow.createCell(10);
		HSSFCell fourthCell11 = fourthRow.createCell(11);
		HSSFCell fourthCell12 = fourthRow.createCell(12);
		HSSFCell fourthCell13 = fourthRow.createCell(13);
		HSSFCell fourthCell14 = fourthRow.createCell(14);
		HSSFCell fourthCell15 = fourthRow.createCell(15);
		HSSFCell fourthCell16 = fourthRow.createCell(16);
		HSSFCell fourthCell17 = fourthRow.createCell(17);
		HSSFCell fourthCell18 = fourthRow.createCell(18);
		HSSFCell fourthCell19 = fourthRow.createCell(19);
		HSSFCell fourthCell20 = fourthRow.createCell(20);
		HSSFCell fourthCell21 = fourthRow.createCell(21);
		HSSFCell fourthCell22 = fourthRow.createCell(22);
		HSSFCell fourthCell23 = fourthRow.createCell(23);
		HSSFCell fourthCell24 = fourthRow.createCell(24);
		HSSFCell fourthCell25 = fourthRow.createCell(25);
		HSSFCell fourthCell26 = fourthRow.createCell(26);
		HSSFCell fourthCell27 = fourthRow.createCell(27);
		HSSFCell fourthCell28 = fourthRow.createCell(28);
		HSSFCell fourthCell29 = fourthRow.createCell(29);
		HSSFCell fourthCell30 = fourthRow.createCell(30);
		HSSFCell fourthCell31 = fourthRow.createCell(31);
		HSSFCell fourthCell32 = fourthRow.createCell(32);
		HSSFCell fourthCell33 = fourthRow.createCell(33);
		HSSFCell fourthCell34 = fourthRow.createCell(34);

		fourthCell00.setCellStyle(defaultHeaderStyle);
		fourthCell01.setCellStyle(defaultHeaderStyle);
		fourthCell02.setCellStyle(defaultHeaderStyle);
		fourthCell03.setCellStyle(defaultHeaderStyle);
		fourthCell04.setCellStyle(defaultHeaderStyle);
		fourthCell05.setCellStyle(defaultHeaderStyle);
		fourthCell06.setCellStyle(defaultHeaderStyle);
		fourthCell07.setCellStyle(defaultHeaderStyle);
		fourthCell08.setCellStyle(defaultHeaderStyle);
		fourthCell09.setCellStyle(defaultHeaderStyle);
		fourthCell10.setCellStyle(defaultHeaderStyle);
		fourthCell11.setCellStyle(defaultHeaderStyle);
		fourthCell12.setCellStyle(defaultHeaderStyle);
		fourthCell13.setCellStyle(defaultHeaderStyle);
		fourthCell14.setCellStyle(defaultHeaderStyle);
		fourthCell15.setCellStyle(defaultHeaderStyle);
		fourthCell16.setCellStyle(defaultHeaderStyle);
		fourthCell17.setCellStyle(defaultHeaderStyle);
		fourthCell18.setCellStyle(defaultHeaderStyle);
		fourthCell19.setCellStyle(defaultHeaderStyle);
		fourthCell20.setCellStyle(defaultHeaderStyle);
		fourthCell21.setCellStyle(defaultHeaderStyle);
		fourthCell22.setCellStyle(defaultHeaderStyle);
		fourthCell23.setCellStyle(defaultHeaderStyle);
		fourthCell24.setCellStyle(defaultHeaderStyle);
		fourthCell25.setCellStyle(defaultHeaderStyle);
		fourthCell26.setCellStyle(defaultHeaderStyle);
		fourthCell27.setCellStyle(defaultHeaderStyle);
		fourthCell28.setCellStyle(defaultHeaderStyle);
		fourthCell29.setCellStyle(defaultHeaderStyle);
		fourthCell30.setCellStyle(defaultHeaderStyle);
		fourthCell31.setCellStyle(defaultHeaderStyle);
		fourthCell32.setCellStyle(defaultHeaderStyle);
		fourthCell33.setCellStyle(defaultHeaderStyle);
		fourthCell34.setCellStyle(defaultHeaderStyle);

		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 4, 4));

		sheet.addMergedRegion(new CellRangeAddress(1, 3, 5, 5));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 6, 6));

		sheet.addMergedRegion(new CellRangeAddress(2, 3, 7, 7));

		sheet.addMergedRegion(new CellRangeAddress(2, 3, 8, 8));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 9, 9));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 10, 10));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 11, 11));

		sheet.addMergedRegion(new CellRangeAddress(2, 3, 12, 12));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 13, 13));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 14, 14));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 15, 15));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 16, 16));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 17, 17));

		sheet.addMergedRegion(new CellRangeAddress(1, 3, 18, 18));

		sheet.addMergedRegion(new CellRangeAddress(2, 3, 19, 19));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 20, 20));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 21, 21));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 22, 22));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 23, 23));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 24, 24));

		sheet.addMergedRegion(new CellRangeAddress(2, 3, 27, 27));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 28, 28));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 29, 29));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 30, 30));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 31, 31));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 32, 32));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 33, 33));

		sheet.addMergedRegion(new CellRangeAddress(1, 3, 34, 34));

		secondCell00.setCellValue("业主项目部");
		secondCell01.setCellValue("监理项目部");
		secondCell02.setCellValue("标包");
		secondCell03.setCellValue("施工单位");
		secondCell04.setCellValue("施工地点");
		secondCell05.setCellValue("具备进场条件（基）");
		secondCell18.setCellValue("余土处理完成数（基）");
		secondCell34.setCellValue("已展放相");

		thirdCell06.setCellValue("总数（基）");
		thirdCell07.setCellValue("钢管塔（基）");
		thirdCell08.setCellValue("整基完成（基）");
		thirdCell09.setCellValue("完成率");
		thirdCell10.setCellValue("塔腿完成（个）");
		thirdCell11.setCellValue("开挖中（基）");
		thirdCell12.setCellValue("总数（基）");
		thirdCell13.setCellValue("钢管塔（基）");
		thirdCell14.setCellValue("整基完成（基）");
		thirdCell15.setCellValue("完成率");
		thirdCell16.setCellValue("塔腿完成（个）");
		thirdCell17.setCellValue("浇筑中（基）");
		thirdCell19.setCellValue("总数");
		thirdCell20.setCellValue("钢管塔");
		thirdCell21.setCellValue("单基策划");
		thirdCell22.setCellValue("落地抱杆");
		thirdCell23.setCellValue("内悬浮外拉线");
		thirdCell24.setCellValue("其他方式");
		thirdCell27.setCellValue("完成率");
		thirdCell28.setCellValue("塔腿完成（个）");
		thirdCell29.setCellValue("组塔中");
		thirdCell30.setCellValue("总长度");
		thirdCell31.setCellValue("已架长度");
		thirdCell32.setCellValue("正架设长度");
		thirdCell33.setCellValue("完成率");

		fourthCell25.setCellValue("总数");
		fourthCell26.setCellValue("钢管塔");
	}
	
	public static HSSFCellStyle getHeaderStyle(HSSFWorkbook wb) {
		HSSFCellStyle defaultHeaderStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		defaultHeaderStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		defaultHeaderStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		defaultHeaderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		defaultHeaderStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		defaultHeaderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		defaultHeaderStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		defaultHeaderStyle.setFillPattern((short) 1);
		HSSFPalette hssfColor = wb.getCustomPalette();
		hssfColor.setColorAtIndex(HSSFColor.LIGHT_BLUE.index, (byte) 0, (byte) 176, (byte) 240);
		defaultHeaderStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
		defaultHeaderStyle.setLocked(true);
		defaultHeaderStyle.setWrapText(false);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 11);
		defaultHeaderStyle.setFont(font);
		return defaultHeaderStyle;
	}
	
	public static HSSFCellStyle getAutoWrapStyle(HSSFWorkbook wb) {
		HSSFCellStyle defalutStyle = getDefaultStyle(wb);
		defalutStyle.setWrapText(true);
		defalutStyle.setBorderRight(HSSFCellStyle.BORDER_THICK);
		return defalutStyle;
	}

	public static HSSFCellStyle getDefaultBottomStyle(HSSFWorkbook wb) {
		HSSFCellStyle defaultStyle = getDefaultStyle(wb);
		defaultStyle.setWrapText(true);
		// defaultStyle.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		return defaultStyle;
	}
	
    public static HSSFCellStyle getDefaultStyle(HSSFWorkbook wb){
    	HSSFCellStyle defalutStyle = wb.createCellStyle();
    	HSSFFont font = wb.createFont();
		defalutStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		defalutStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		defalutStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		defalutStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		defalutStyle.setFillPattern((short)1);
		defalutStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		defalutStyle.setLocked(false);
		defalutStyle.setWrapText(false);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 11);
		defalutStyle.setFont(font);
		defalutStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		defalutStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		return defalutStyle;
    }
    
    public static void addMark(HSSFWorkbook wb,HSSFSheet sheet,int startRow){
		// 添加图例
		HSSFRow first = sheet.createRow(startRow);
		HSSFCell firstCell00 = first.createCell(0);
		HSSFCell firstCell01 = first.createCell(1);
		HSSFCell firstCell02 = first.createCell(2);
		HSSFCell firstCell03 = first.createCell(3);

		firstCell00.setCellStyle(getMarkTitleStyle(wb));
		firstCell01.setCellStyle(getMarkTitleStyle(wb));
		firstCell02.setCellStyle(getMarkTitleStyle(wb));
		firstCell03.setCellStyle(getMarkTitleStyle(wb));

		firstCell00.setCellValue("变色的单元格区域");
		firstCell01.setCellValue("图例");
		firstCell02.setCellValue("含义");
		firstCell03.setCellValue("说明");

		HSSFRow two = sheet.createRow(startRow + 1);
		HSSFCell twoCell00 = two.createCell(0);
		HSSFCell twoCell01 = two.createCell(1);
		HSSFCell twoCell02 = two.createCell(2);
		HSSFCell twoCell03 = two.createCell(3);

		twoCell00.setCellStyle(getDefaultBottomStyle(wb));
		twoCell01.setCellStyle(getMarkStyle(wb, 1));
		twoCell02.setCellStyle(getDefaultStyle(wb));
		twoCell03.setCellStyle(getAutoWrapStyle(wb));

		twoCell00.setCellValue("F列、I列、L列、O列、S列、V列、AD列、AF列、AI列");
		twoCell01.setCellValue("无色");
		twoCell02.setCellValue("正常");
		twoCell03.setCellValue("进度值3天（包含）未更新");

		HSSFRow third = sheet.createRow(startRow + 2);
		HSSFCell thirdCell00 = third.createCell(0);
		HSSFCell thirdCell01 = third.createCell(1);
		HSSFCell thirdCell02 = third.createCell(2);
		HSSFCell thirdCell03 = third.createCell(3);

		thirdCell00.setCellStyle(getDefaultBottomStyle(wb));
		thirdCell01.setCellStyle(getMarkStyle(wb, 2));
		thirdCell02.setCellStyle(getDefaultStyle(wb));
		thirdCell03.setCellStyle(getAutoWrapStyle(wb));

		thirdCell01.setCellValue("浅黄色");
		thirdCell02.setCellValue("轻微延期");
		thirdCell03.setCellValue("进度值3天（不含）~7天（包含）未更新");

		HSSFRow fourth = sheet.createRow(startRow + 3);
		HSSFCell fourthCell00 = fourth.createCell(0);
		HSSFCell fourthCell01 = fourth.createCell(1);
		HSSFCell fourthCell02 = fourth.createCell(2);
		HSSFCell fourthCell03 = fourth.createCell(3);

		fourthCell00.setCellStyle(getDefaultBottomStyle(wb));
		fourthCell01.setCellStyle(getMarkStyle(wb, 3));
		fourthCell02.setCellStyle(getDefaultStyle(wb));
		fourthCell03.setCellStyle(getAutoWrapStyle(wb));

		fourthCell01.setCellValue("黄色");
		fourthCell02.setCellValue("延期");
		fourthCell03.setCellValue("进度值7天（不含）~14（包含）天未更新");

		HSSFRow fifth = sheet.createRow(startRow + 4);
		HSSFCell fifthCell00 = fifth.createCell(0);
		HSSFCell fifthCell01 = fifth.createCell(1);
		HSSFCell fifthCell02 = fifth.createCell(2);
		HSSFCell fifthCell03 = fifth.createCell(3);

		fifthCell00.setCellStyle(getDefaultBottomStyle(wb));
		fifthCell01.setCellStyle(getMarkStyle(wb, 4));
		fifthCell02.setCellStyle(getDefaultStyle(wb));
		fifthCell03.setCellStyle(getAutoWrapStyle(wb));

		fifthCell01.setCellValue("红色");
		fifthCell02.setCellValue("严重延期");
		fifthCell03.setCellValue("进度值14天（不含）以上未更新");

		sheet.addMergedRegion(new CellRangeAddress(startRow + 1, startRow + 4, 0, 0));

	}
	
	public static HSSFCellStyle getMarkTitleStyle(HSSFWorkbook wb) {
		HSSFCellStyle defaultTitleStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font = wb.createFont();
		defaultTitleStyle.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		defaultTitleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		defaultTitleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		defaultTitleStyle.setBorderTop(HSSFCellStyle.BORDER_THICK);
		defaultTitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		defaultTitleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		defaultTitleStyle.setFillPattern((short) 1);
		defaultTitleStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		defaultTitleStyle.setLocked(true);
		defaultTitleStyle.setWrapText(false);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 11);
		defaultTitleStyle.setFont(font);
		return defaultTitleStyle;
	}
	
	/**
     * 取得导出excel颜色 
     * @param type 1,白色 2,浅黄 3,黄 4,红
     * @author zhanghuibin
     * @date 2015/10/27
     */
    public static HSSFCellStyle getMarkStyle(HSSFWorkbook wb,int type){
    	HSSFCellStyle defalutStyle = getDefaultStyle(wb);
    	defalutStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    	switch (type) {
			case 1:
				defalutStyle.setFillForegroundColor(HSSFColor.WHITE.index);//白色
				break;
			case 2:
				defalutStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);//浅黄
				break;
			case 3:
				defalutStyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄
				break;
			case 4:
				defalutStyle.setFillForegroundColor(HSSFColor.RED.index);//红
				break;
			default:
				break;
		}
		return defalutStyle;
    }

}
