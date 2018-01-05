package com.forever.zhb.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.forever.zhb.vo.WatermarkVO;

public class ImageUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);
    
    private static final Color FONT_COLOR = Color.GRAY;

    public static void pressText(InputStream is, OutputStream os, float alpha, int xNum, int yNum, String[] txts)
            throws Exception {
        try {
            Image image = ImageIO.read(is);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            double radian = Math.atan(height / width);
            radian *= 0.8D;
            double sinRadian = Math.sin(radian);
            double cosRadian = Math.cos(radian);

            BufferedImage bufferedImage = new BufferedImage(width, height, 1);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);

            int halfX = width / 2;
            int halfY = height / 2;
            double xiexian = halfX / cosRadian;

            Map TXT_WatermarkVO = new HashMap();

            for (String str : txts) {
                if (StringUtils.isNotBlank(str)) {
                    String fontName;
                    int fitPixelWidth;
                    if (str.matches("^[a-zA-Z]*")) {
                        fontName = "Times New Roman";
                        fitPixelWidth = (int) Math.round(xiexian * 0.0428D * str.length());
                    } else {
                        fontName = "宋体";
                        fitPixelWidth = (int) Math.round(xiexian * 0.06D * str.length());
                    }
                    Font font = getFitFontPointSizeFont(g, fontName, 1, str, fitPixelWidth);
                    g.setFont(font);
                    Rectangle2D r = g.getFontMetrics().getStringBounds(str, g);
                    TXT_WatermarkVO.put(str, new WatermarkVO(font, (int) r.getCenterX(), (int) r.getCenterY()));
                }
            }

            g.setColor(FONT_COLOR);
            g.setComposite(AlphaComposite.getInstance(10, alpha));

            int right_y = (int) (width / xNum * sinRadian);
            int right_x = (int) (width / xNum * cosRadian);
            int top_x = -(int) (height / yNum * sinRadian);
            int top_y = (int) (height / yNum * cosRadian);

            int cnt = 0;
            for (int i = 0; i < yNum; ++i) {
                int index = cnt % txts.length;
                if (i == 0) {
                    g.translate(halfX / xNum, halfY / yNum);
                    g.rotate(-radian);
                } else {
                    g.translate(top_x, top_y);
                }
                WatermarkVO watermarkVO = (WatermarkVO) TXT_WatermarkVO.get(txts[index]);
                g.setFont(watermarkVO.font);
                g.drawString(txts[index], -watermarkVO.centerX, -watermarkVO.centerY);
                ++cnt;
                for (int j = 1; j < xNum; ++j) {
                    g.translate(right_x, right_y);
                    index = cnt % txts.length;
                    watermarkVO = (WatermarkVO) TXT_WatermarkVO.get(txts[index]);
                    g.setFont(watermarkVO.font);
                    g.drawString(txts[index], -watermarkVO.centerX, -watermarkVO.centerY);
                    ++cnt;
                }
                for (int j = 1; j < xNum; ++j) {
                    g.translate(-right_x, -right_y);
                }
            }
            g.dispose();
            ImageIO.write(bufferedImage, "jpg", os);
            os.flush();
        } catch (Exception e) {
        } finally {
            if (is != null)
                is.close();
        }
    }
    
    private static Font getFitFontPointSizeFont(Graphics2D g, String fontName, int fontStyle, String pressText,
            int fitPixelWidth) {
        int pointSize = 0;
        Font font;
        FontMetrics fm;
        int pixelWidthOfPressTxt;
        do {
            pointSize += 2;
            font = new Font(fontName, fontStyle, pointSize);
            fm = g.getFontMetrics(font);
            pixelWidthOfPressTxt = fm.stringWidth(pressText);
        } while (pixelWidthOfPressTxt < fitPixelWidth);
        Font preFont = new Font(fontName, fontStyle, pointSize - 1);

        fm = g.getFontMetrics(preFont);
        int prePixelWidthOfPressTxt = fm.stringWidth(pressText);
        if (Math.abs(prePixelWidthOfPressTxt - fitPixelWidth) < Math.abs(pixelWidthOfPressTxt - fitPixelWidth)) {
            return preFont;
        }
        return font;
    }
    
    public static byte[] getImageBytes(InputStream image,File imageDirAndName) throws IOException{
    	FileUtils.copyInputStreamToFile(image, imageDirAndName);
    	logger.info(imageDirAndName.getAbsolutePath());
    	return FileUtils.readFileToByteArray(imageDirAndName);
    	
    }
}
