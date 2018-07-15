package com.forever.zhb.utils.attachment.pdf;

import com.forever.zhb.pojo.FileInfoData;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfFileSpecification;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;


public class PDFUtil {

    protected static final Logger log = Logger.getLogger(PDFUtil.class);

    /*完全程序生成 pdf 文档*/
    public static void createPdf(Document document , ApplicationData appData, ChangeData changeData) throws DocumentException,IOException {

        document.open();
        // 设置宋体
        //BaseFont songti = BaseFont.createFont("STSong-Light" ,BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        BaseFont songti = BaseFont.createFont("STSong-Light" ,"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        //标题 字体
        Font titNormFont = new Font(songti, 12, Font.BOLD);
        Font titOtherFont = new Font(songti, 10, Font.BOLD);
        //正文 字体
        Font cotNormFont = new Font(songti, 10, Font.NORMAL);
        Font cotOtherFont = new Font(songti, 9, Font.NORMAL);

//标题
        setParagraph(document, "《大陆居民在台湾地区学习证明》申请表", titNormFont, Element.ALIGN_CENTER, 0,0);

//添加6列的表格
        int[] Widths = {18,18,18,18};
        Table firstTable = createTable(4, Widths, 95, Element.ALIGN_CENTER, 2, 5, new Color(0, 125, 255), 1, 0);

        //第一行
        String[] cellValues = {"姓名",appData.getXm(),"联系电话",null==appData.getLxdh()?"":appData.getLxdh()};
        addCells(firstTable, 4, cellValues, cotNormFont);

        //第二行
        firstTable.addCell(getCell("性别", cotNormFont, 1, 1));
        firstTable.addCell(getCell(appData.getXb(), cotNormFont, 1, 1));

        firstTable.addCell(getCell("身份证号", cotNormFont, 1, 1));
        firstTable.addCell(getCell(appData.getSfzh(), cotNormFont, 1, 1));

        //第三行
        firstTable.addCell(getCell("大陆居民往来台湾通行证号码", cotNormFont, 1, 1));
        firstTable.addCell(getCell(appData.getTxzh(), cotNormFont, 3, 1));

        String yxmc = "";
        String cc = "";
        if (null == changeData) {
            yxmc = appData.getYxmc().replace("公立", "").replace("国立", "");
            cc = appData.getCc();
        }else {
            yxmc = changeData.getYxmc().replace("公立", "").replace("国立", "");
            cc = changeData.getCc();
        }

        //第四行
        firstTable.addCell(getCell("在台就读学校", cotNormFont, 1, 1));
        firstTable.addCell(getCell(yxmc, cotNormFont, 1, 1));
        firstTable.addCell(getCell("学生证号", cotNormFont, 1, 1));
        firstTable.addCell(getCell(appData.getXxzjhm(), cotNormFont, 1, 1));

        //第五行
        firstTable.addCell(getCell("入学日期", cotNormFont, 1, 1));
        String rxrq = appData.getRxrq().substring(0,4)+" 年 "+ appData.getRxrq().substring(4,6) +" 月 " + appData.getRxrq().substring(6) + " 日";
        firstTable.addCell(getCell(rxrq, cotNormFont, 1, 1));
        firstTable.addCell(getCell("毕业时期", cotNormFont, 1, 1));
        String byrq = appData.getByrq().substring(0,4)+" 年 "+ appData.getByrq().substring(4,6) +" 月 " ;
        if (appData.getByrq().length() == 8) {
            byrq += appData.getByrq().substring(6) + " 日" ;
        }
        firstTable.addCell(getCell(byrq, cotNormFont, 1, 1));

        //第六行
        firstTable.addCell(getCell("层次", cotNormFont, 1, 1));
        firstTable.addCell(getCell(cc, cotNormFont, 1, 1));
        firstTable.addCell(getCell("证书编号", cotNormFont, 1, 1));
        firstTable.addCell(getCell(appData.getZsbh(), cotNormFont, 1, 1));

        //第七行
        firstTable.addCell(getCell("联系地址、邮编", cotNormFont, 1, 1));
        String dzyb = null==appData.getProvince()?"":appData.getProvince();
        dzyb += null==appData.getCity()?"":appData.getCity();
        dzyb += null==appData.getDistrict()?"":appData.getDistrict();
        dzyb += null==appData.getLxdz()?"":appData.getLxdz() + "  " ;
        dzyb += null==appData.getYzbm()?"":appData.getYzbm();
        firstTable.addCell(getCell(dzyb, cotNormFont, 3, 1));

        document.add(firstTable);
//添加第一个表结束
        setParagraph(document, "     注：层次和证书编号请按在台获取的最高学位填写.", cotOtherFont, Element.ALIGN_LEFT, 5,0);
        setParagraph(document, "     本人承诺，上述填写内容真实无误。", titOtherFont, Element.ALIGN_LEFT, 5,0);
        setParagraph(document, "申请人：                                       ", cotNormFont, Element.ALIGN_RIGHT, 5,0);
        setParagraph(document, "日  期：                                         ", cotNormFont, Element.ALIGN_RIGHT, 5,0);

        StringBuilder underLineContent = new StringBuilder("                                                ");
        underLineContent.append("                                                                    ");
        underLineContent.append("                                                                 ");
        Paragraph underline = new Paragraph(underLineContent.toString(),
            FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 10, Font.UNDERLINE,Color.BLACK));
        underline.setAlignment(Element.ALIGN_CENTER);
        document.add(underline);

        setParagraph(document, "     以下由工作人员填写", cotOtherFont, Element.ALIGN_LEFT, 10,0);
        setParagraph(document, "", titOtherFont, Element.ALIGN_LEFT, 0,0);

//添加第二个表格
        int[] secWidth = {20,20,15,20};
        Table secTable = createTable(4, secWidth, 95, Element.ALIGN_CENTER, 2, 10, new Color(0, 125, 255), 1, 0);

        //第一行
        secTable.addCell(getCell("证明办理日期", cotNormFont,1,1));
        secTable.addCell(getCell("", cotNormFont,1,1));
        secTable.addCell(getCell("□一年内首次办理   □其他_____________________", cotOtherFont,2,1));

        //第二行
        secTable.addCell(getCell("证 明 编 号", cotNormFont,1,1));
        secTable.addCell(getCell("", cotOtherFont,3,1));

        //第三行
        String[] values = {"证明是否寄回","□是  □否","寄回日期",""};
        addCells(secTable, 4, values, cotNormFont);

        document.add(secTable);
//添加第二个表格结束
        setParagraph(document, "     附：证明回寄票据（复印件） ", cotNormFont, Element.ALIGN_LEFT, 5,0);
        setParagraph(document, "办理人：                                       ", cotNormFont, Element.ALIGN_RIGHT, 5,0);
        setParagraph(document, "日  期：                                       ", cotNormFont, Element.ALIGN_RIGHT, 5,0);

        document.close();

    }

    public static Table createTable(int column,int[] widths,int width,int align,int border,int padding,Color borderColor,int borderWidth,int spacing) throws DocumentException{
        Table table = new Table(column); //列数
        table.setWidths(widths);//设置每列所占比例
        table.setWidth(width); // 占页面宽度 95%
        table.setAlignment(align);//居中显示
        table.setBorderWidth(borderWidth); //边框宽度
        table.setBorderColor(borderColor); //边框颜色
        table.setPadding(padding);//衬距，可以调整单元格的高度
        table.setSpacing(spacing);//单元格之间的间距
        table.setBorder(border);//边框
        return table;
    }

    public static void addCells(Table table,int cols,String[] values,Font font) throws DocumentException{
        for (int i = 0; i < cols; i++) {
            Cell cell = new Cell(new Phrase(values[i], font ));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT); //居中显示
            cell.setVerticalAlignment(Element.ALIGN_CENTER); //纵向居中
            table.addCell(cell);
        }
    }

    public static Cell getCell(String cellValue,Font font,int colspans,int rowspans) throws DocumentException{
        Cell cell = new Cell(new Paragraph(cellValue,font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //居中显示
        cell.setVerticalAlignment(Element.ALIGN_CENTER); //纵向居中
        if (colspans > 1) {
            cell.setColspan(colspans); //和并列
        }
        if (rowspans > 1) {
            cell.setRowspan(rowspans);  //合并行
        }
        return cell;
    }

    public static void setParagraph(Document document ,String paragraphValues,Font font ,int align,int spaceBefore,int spaceAfter) throws DocumentException{
        Paragraph content = new Paragraph(paragraphValues,font);
        content.setAlignment(align); // 设置对齐方式
        //content.setFont(font);// 设置字体
        content.setSpacingBefore(spaceBefore); // 与上一段落（标题）的行距
        content.setSpacingAfter(spaceAfter); // 与下一段落（标题）的行距
        document.add(content);
    }




    /*生成 HTML内容 的pdf*/
    public static void generatePDF(File pdfFile, StringBuffer html) {
        try {
            FileOutputStream fos = new FileOutputStream(pdfFile);
            ITextRenderer irt = new ITextRenderer();
            ITextFontResolver r = irt.getFontResolver();
            String[] fonts = getDefaultFonts();
            for (String font : fonts) {
                r.addFont(font, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            }
            irt.setDocumentFromString(html.toString());
            irt.layout();
            irt.createPDF(fos);

        } catch (Exception e) {
            log.error(e);
        }

    }

    public static String[] getDefaultFonts() {
        String base = PDFUtil.getBaseFontsPath();
        List<String> fonts = new ArrayList<String>();
        fonts.add(base + "calibri.ttf");// 英文内容
        fonts.add(base + "calibrii.ttf");// 英文斜体
        fonts.add(base + "calibrib.ttf");// 英文粗体
        fonts.add(base + "times.ttf");// 中文报告日期
        fonts.add(base + "mtcorsva.ttf");// 英文花体of
        fonts.add(base + "simsun.ttf");// 中文报告
        fonts.add(base + "simhei.ttf");// 中文标题
        fonts.add(base + "kaiti.ttf");// 成绩单标签
        fonts.add(base + "simli.ttf");// 中文通知单结果
        fonts.add(base + "simfang.ttf");// 协查函
        String[] rs = new String[fonts.size()];
        return fonts.toArray(rs);
    }

    public static String getBaseFontsPath() {
        return PDFUtil.class.getClassLoader().getResource("fonts/").getPath();
    }

    /*将临时 pdf及附件 合并到新pdf中*/
    public static void addAttachment(String filePdf, String tempPdf, List<FileInfoData> atts) {
        try {
            File pdfFile = new File(filePdf);
            PdfReader reader = new PdfReader(tempPdf);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pdfFile));
            if (null != atts && atts.size() > 0) {
                for (FileInfoData att : atts) {
                    byte[] bytes = new byte[1024];
                    PdfFileSpecification pfs = PdfFileSpecification.fileEmbedded(stamper.getWriter(), null, att.getFileName(), bytes, true);
                    pfs.setUnicodeFileName(att.getFileName(), true);// 中文附件名称乱码
                    stamper.addFileAttachment(null, pfs);
                }
            }
            stamper.close();
        } catch (Exception e) {
            log.error(e);
        }
    }

}
