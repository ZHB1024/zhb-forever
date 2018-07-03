package com.forever.zhb.utils;

import com.forever.zhb.utils.image.ImageVO;
import com.forever.zhb.utils.image.JpegReader;
import com.forever.zhb.vo.WatermarkVO;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.IIOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.util.IOUtils;

public class ImageUtil {

    /*static {
        InputStream is = null;
        InputStream is2 = null;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            is = ImageUtil.class.getResourceAsStream("/fonts/times.ttf");
            Font font = Font.createFont(0, is);
            ge.registerFont(font);
            is2 = ImageUtil.class.getResourceAsStream("/fonts/FZXBSJW.TTF");
            font = Font.createFont(0, is2);
            ge.registerFont(font);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                if (is2 != null)
                    is2.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }*/

    private static final String FONT_NAME = "宋体";
    private static final int FONT_STYLE = 1;
    private static final Color FONT_COLOR = Color.GRAY;
    private static final float FONT_ALPHA = 0.3F;
    private static final float scaleSize = 0.5F;
    private static final String PICTRUE_FORMATE_JPG = "jpg";
    private static final String FONT_TIMES_NEW_ROMAN = "Times New Roman";
    private static final String FONT_FZXBSJT = "方正小标宋简体";

    public static void pressText(InputStream targetImg, OutputStream os, String PRESS_TXT, String formatName)
        throws Exception {
        byte[] data = pressText(targetImg, PRESS_TXT, formatName);
        os.write(data);
    }

    public static byte[] pressText(InputStream targetImg, String PRESS_TXT, String formatName) throws Exception {
        BufferedImage bufferedImage = null;
        ByteArrayOutputStream baos = null;
        try {
            Image image = ImageIO.read(targetImg);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            double radian = Math.atan(height / width);
            radian *= 0.8D;
            double sinRadian = Math.sin(radian);
            double cosRadian = Math.cos(radian);

            bufferedImage = new BufferedImage(width, height, 1);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);

            int halfX = width / 2;
            int halfY = height / 2;
            double xiexian = halfX / cosRadian;
            int fitPixelWidth = (int) Math.round(xiexian * 0.3D);
            Font font = getFitFontPointSizeFont(g, PRESS_TXT, fitPixelWidth, radian);
            g.setFont(font);
            g.setColor(FONT_COLOR);
            g.setComposite(AlphaComposite.getInstance(10, 0.3F));

            Rectangle2D r = g.getFontMetrics().getStringBounds(PRESS_TXT, g);
            g.translate(halfX / 3, halfY / 3);
            g.rotate(-radian);
            g.translate(-r.getCenterX(), -r.getCenterY());
            g.drawString(PRESS_TXT, 0, 0);

            double xSin = width / 3 * sinRadian;
            double xCos = width / 3 * cosRadian;
            double ySin = height / 3 * sinRadian;
            double yCos = height / 3 * cosRadian;

            g.translate(xCos, xSin);
            g.drawString(PRESS_TXT, 0, 0);

            g.translate(xCos, xSin);
            g.drawString(PRESS_TXT, 0, 0);

            g.translate(-ySin, yCos);
            g.drawString(PRESS_TXT, 0, 0);

            g.translate(-xCos, -xSin);
            g.drawString(PRESS_TXT, 0, 0);

            g.translate(-xCos, -xSin);
            g.drawString(PRESS_TXT, 0, 0);

            g.translate(-ySin, yCos);
            g.drawString(PRESS_TXT, 0, 0);

            g.translate(xCos, xSin);
            g.drawString(PRESS_TXT, 0, 0);

            g.translate(xCos, xSin);
            g.drawString(PRESS_TXT, 0, 0);

            g.dispose();
            baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, formatName, baos);
            byte[] arrayOfByte = baos.toByteArray();

            return arrayOfByte;
        } catch (Exception e) {
        } finally {
            if (targetImg != null) {
                targetImg.close();
            }
            if (baos != null)
                baos.close();
        }
        return null;
    }

    private static Font getFitFontPointSizeFont(Graphics2D g, String pressText, int fitPixelWidth, double radian) {
        int pointSize = 0;
        Font font;
        int pixelWidthOfPressTxt;
        do {
            pointSize += 2;
            font = new Font("宋体", 1, pointSize);
            FontMetrics fm = g.getFontMetrics(font);
            pixelWidthOfPressTxt = fm.stringWidth(pressText);
        } while (pixelWidthOfPressTxt < fitPixelWidth);
        Font preFont = new Font("宋体", 1, pointSize - 1);

        FontMetrics fm = g.getFontMetrics(preFont);
        int prePixelWidthOfPressTxt = fm.stringWidth(pressText);
        if (Math.abs(prePixelWidthOfPressTxt - fitPixelWidth) < Math.abs(pixelWidthOfPressTxt - fitPixelWidth)) {
            return preFont;
        }
        return font;
    }

    public static byte[] getCropPhotoBytes(int cropX, int cropY, int photoW, int photoH, ByteArrayInputStream bis) {
        ImageInputStream iis = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            iis = ImageIO.createImageInputStream(bis);

            Iterator it = ImageIO.getImageReadersByFormatName("jpg");

            ImageReader reader = (ImageReader) it.next();

            reader.setInput(iis, true);

            ImageReadParam param = reader.getDefaultReadParam();

            Rectangle rect = new Rectangle(cropX, cropY, photoW, photoH);

            param.setSourceRegion(rect);

            BufferedImage bi = reader.read(0, param);

            ImageIO.write(bi, "jpg", bos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (iis != null)
                try {
                    iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return bos.toByteArray();
    }

    public static ByteArrayInputStream equimultipleConvert(Integer width, Integer height, File InputPhoto) {
        ByteArrayInputStream bis = null;
        try {
            BufferedImage inputImage = ImageIO.read(new FileInputStream(InputPhoto));
            if (inputImage != null) {
                if (width == null) {
                    width = Integer.valueOf(inputImage.getWidth());
                }
                if (height == null) {
                    height = Integer.valueOf(inputImage.getHeight());
                }
                byte[] imgBytes = equimultipleConvertToByte(width.intValue(), height.intValue(), inputImage.getWidth(),
                    inputImage.getHeight(), inputImage);

                bis = new ByteArrayInputStream(imgBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bis;
    }

    public static byte[] equimultipleConvertToByte(int width, int height, int pwidth, int pheight, byte[] InputPhoto) {
        if ((width <= 0) || (height <= 0) || (pwidth <= 0) || (pheight <= 0) || (InputPhoto == null)) {
            return new byte[0];
        }
        BufferedImage inputImage = null;
        try {
            inputImage = ImageIO.read(new ByteArrayInputStream(InputPhoto));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return equimultipleConvertToByte(width, height, pwidth, pheight, inputImage);
    }

    public static byte[] equimultipleConvertToByte(int width, int height, int pwidth, int pheight,
        BufferedImage inputImage) {
        if ((width <= 0) || (height <= 0) || (pwidth <= 0) || (pheight <= 0) || (inputImage == null)) {
            return new byte[0];
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            if ((pwidth > 0) && (pheight > 0)) {
                if (pwidth / pheight >= width / height) {
                    if (pwidth > width) {
                        height = pheight * width / pwidth;
                    } else {
                        width = pwidth;
                        height = pheight;
                    }
                } else if (pheight > height) {
                    width = pwidth * height / pheight;
                } else {
                    width = pwidth;
                    height = pheight;
                }

            }

            BufferedImage outputImg = new BufferedImage(width, height, 1);

            outputImg.getGraphics().drawImage(inputImage.getScaledInstance(width, height, 4), 0, 0, null);
            ImageIO.write(outputImg, "jpg", bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    public static byte[] compressJpg(byte[] inbis) throws IOException {
        if ((inbis == null) || (inbis.length == 0)) {
            throw new IllegalArgumentException("inbis is null");
        }
        long defaultSize = 102400L;
        long imgSize = inbis.length;

        if (imgSize < defaultSize) {
            return inbis;
        }
        float quality = 0.5F;

        ByteArrayInputStream is = new ByteArrayInputStream(inbis);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        BufferedImage image = ImageIO.read(is);
        if (image == null) {
            return inbis;
        }

        Iterator writers = ImageIO.getImageWritersByFormatName("jpg");

        if (!(writers.hasNext())) {
            throw new IllegalStateException("No writers found");
        }
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);

        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(2);
        param.setCompressionQuality(quality);

        writer.write(null, new IIOImage(image, null, null), param);

        is.close();
        os.close();
        ios.close();
        writer.dispose();
        return os.toByteArray();
    }

    public static byte[] smallImage(File oriFile, String suffix, long targetSize)
        throws ImageReadException, IOException {
        BufferedImage bi = null;
        JpegReader jr = new JpegReader();
        if (("jpg".equalsIgnoreCase(suffix)) || ("jpeg".equalsIgnoreCase(suffix)) || ("png".equalsIgnoreCase(suffix)))
            bi = jr.readImage(oriFile);
        else {
            throw new ImageReadException("非jpg或png格式照片");
        }
        long tempSize = oriFile.length();
        byte[] result = null;
        if (tempSize <= targetSize) {
            result = FileUtils.readFileToByteArray(oriFile);
        } else {
            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
            bi = resizeImg(bi, suffix, bi.getWidth(), bi.getHeight());
            ImageIO.write(bi, suffix, tmp);
            tmp.close();
            tempSize = tmp.size();
            while (tempSize > targetSize) {
                bi = resizeImg(bi, suffix, (int) (bi.getWidth() * 0.5F), (int) (bi.getHeight() * 0.5F));
                tmp.reset();
                ImageIO.write(bi, suffix, tmp);
                tmp.close();
                tempSize = tmp.size();
            }
            result = tmp.toByteArray();
        }
        return result;
    }

    public static BufferedImage resizeImg(BufferedImage bi, String suffix, int targetWidth, int targetHeight) {
        double scaleX = targetWidth / bi.getWidth();
        double scaleY = targetHeight / bi.getHeight();

        Image Itemp = bi.getScaledInstance(targetWidth, targetHeight, 1);
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(scaleX, scaleY), null);
        Itemp = op.filter(bi, null);
        return ((BufferedImage) Itemp);
    }

    public static ImageVO cropImg(File file, int x, int y, int width, int height) {
        ImageVO vo = new ImageVO(x, y, width, height, 0);
        return cropImg(file, vo);
    }

    public static ImageVO cropImg(File file, ImageVO vo) {
        ImageVO doneImageVO = new ImageVO();
        InputStream fis = null;
        try {
            if ((vo.getX() >= 0) && (vo.getY() >= 0) && (vo.getWidth() != 0) && (vo.getHeight() != 0)) {
                String fileName = file.getName();
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                Iterator readers = ImageIO.getImageReadersByFormatName(suffix);
                ImageReader reader = (ImageReader) readers.next();
                fis = new FileInputStream(file);
                ImageInputStream iis = ImageIO.createImageInputStream(fis);
                reader.setInput(iis, true);
                ImageReadParam param = reader.getDefaultReadParam();
                Rectangle rect = new Rectangle(vo.getX(), vo.getY(), vo.getWidth(), vo.getHeight());
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                ImageIO.write(bi, suffix, file);
                doneImageVO.setWidth(bi.getWidth());
                doneImageVO.setHeight(bi.getHeight());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return doneImageVO;
    }

    public static ImageVO cropImg(String suffix, InputStream is, OutputStream os, ImageVO vo) {
        ImageVO doneImageVO = new ImageVO();
        try {
            if ((vo.getX() >= 0) && (vo.getY() >= 0) && (vo.getWidth() != 0) && (vo.getHeight() != 0)) {
                Iterator readers = ImageIO.getImageReadersByFormatName(suffix);
                ImageReader reader = (ImageReader) readers.next();
                ImageInputStream iis = ImageIO.createImageInputStream(is);
                reader.setInput(iis, true);
                ImageReadParam param = reader.getDefaultReadParam();
                Rectangle rect = new Rectangle(vo.getX(), vo.getY(), vo.getWidth(), vo.getHeight());
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                ImageIO.write(bi, suffix, os);
                doneImageVO.setWidth(bi.getWidth());
                doneImageVO.setHeight(bi.getHeight());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return doneImageVO;
    }

    public static byte[] rotateImg2(InputStream is, ImageVO vo) throws IOException {
        byte[] array = null;
        ByteArrayOutputStream bos = null;
        ImageOutputStream out = null;
        try {
            String suffix = vo.getSuffix().toLowerCase();
            BufferedImage bufferedImage = ImageIO.read(is);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            BufferedImage dstImage = null;
            AffineTransform affineTransform = new AffineTransform();
            int result = vo.getRadian() % 360;
            if (vo.getRadian() % 180 == 0) {
                affineTransform.translate(width, height);
                dstImage = new BufferedImage(width, height, bufferedImage.getType());
            } else if ((result == 90) || (result == -270)) {
                affineTransform.translate(height, 0.0D);
                dstImage = new BufferedImage(height, width, bufferedImage.getType());
            } else if ((result == -90) || (result == 270)) {
                affineTransform.translate(0.0D, width);
                dstImage = new BufferedImage(height, width, bufferedImage.getType());
            } else {
                bos = new ByteArrayOutputStream();
                out = ImageIO.createImageOutputStream(bos);
                ImageIO.write(bufferedImage, suffix, out);
                array = bos.toByteArray();
                byte[] arrayOfByte1 = array;
                return arrayOfByte1;
            }
            affineTransform.rotate(Math.toRadians(vo.getRadian()));
            AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, 2);
            dstImage = affineTransformOp.filter(bufferedImage, dstImage);
            bos = new ByteArrayOutputStream();
            out = ImageIO.createImageOutputStream(bos);
            ImageIO.write(dstImage, suffix, out);
            array = bos.toByteArray();
        } catch (IOException ioe) {
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return array;
    }

    public static byte[] rotateImg2(File file, ImageVO vo) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        return rotateImg2(fis, vo);
    }

    public static ImageVO rotateImg(File file, ImageVO vo) {
        return rotateImg(file, vo.getRadian());
    }

    public static ImageVO rotateImg(File file, int radian) {
        ImageVO doneImageVO = new ImageVO();
        try {
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            BufferedImage bufferedImage = ImageIO.read(file);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            BufferedImage dstImage = null;
            AffineTransform affineTransform = new AffineTransform();
            if (radian == 180) {
                affineTransform.translate(width, height);
                dstImage = new BufferedImage(width, height, bufferedImage.getType());
            } else if (radian == 90) {
                affineTransform.translate(height, 0.0D);
                dstImage = new BufferedImage(height, width, bufferedImage.getType());
            } else if (radian == 270) {
                affineTransform.translate(0.0D, width);
                dstImage = new BufferedImage(height, width, bufferedImage.getType());
            } else {
                doneImageVO.setWidth(bufferedImage.getWidth());
                doneImageVO.setHeight(bufferedImage.getHeight());
                doneImageVO.setRadian(radian);
                ImageVO localImageVO1 = doneImageVO;
                return localImageVO1;
            }

            affineTransform.rotate(Math.toRadians(radian));
            AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, 2);
            dstImage = affineTransformOp.filter(bufferedImage, dstImage);
            ImageIO.write(dstImage, suffix, file);
            doneImageVO.setWidth(dstImage.getWidth());
            doneImageVO.setHeight(dstImage.getHeight());
            doneImageVO.setRadian(radian);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
        return doneImageVO;
    }

    public static void resizeImg(BufferedImage bi, File destFile, String suffix, int width, int height)
        throws IOException {
        try {
            double scaleX = width / bi.getWidth();
            double scaleY = height / bi.getHeight();

            Image Itemp = bi.getScaledInstance(width, height, 1);
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(scaleX, scaleY), null);
            Itemp = op.filter(bi, null);
            ImageIO.write((BufferedImage) Itemp, suffix.toLowerCase(), destFile);
        } catch (IOException ex) {
        } finally {
            if (bi != null)
                bi.flush();
        }
    }

    public static byte[] resizeImg(InputStream is, String suffix, int width, int height) throws Exception {
        ByteArrayOutputStream bos = null;
        byte[] result = null;
        try {
            BufferedImage Bi = ImageIO.read(is);
            double scaleX = width / Bi.getWidth();
            double scaleY = height / Bi.getHeight();

            Image Itemp = Bi.getScaledInstance(width, height, 1);
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(scaleX, scaleY), null);
            Itemp = op.filter(Bi, null);
            bos = new ByteArrayOutputStream();
            ImageIO.write((BufferedImage) Itemp, suffix.toLowerCase(), bos);
            result = bos.toByteArray();
        } catch (Exception ex) {
        } finally {
            try {
                if (bos != null)
                    bos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                if (is != null)
                    is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static byte[] resizeImg(File oriFile, String suffix, int width, int height) throws Exception {
        FileInputStream fis = new FileInputStream(oriFile);
        return resizeImg(fis, suffix, width, height);
    }

    public static void saveAsSmall(File oriFile, String suffix, File targetFile) {
        try {
            BufferedImage bi = ImageIO.read(oriFile);
            ImageIO.write(bi, suffix.toLowerCase(), targetFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static final void pressImage(InputStream targetImg, OutputStream os, String waterImg, double angdeg,
        double tx, double ty, float alpha) throws Exception {
        try {
            Image image = ImageIO.read(targetImg);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, 1);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);

            Image waterImage = ImageIO.read(new File(waterImg));

            g.setComposite(AlphaComposite.getInstance(10, alpha));

            AffineTransform atf = AffineTransform.getTranslateInstance(tx, ty);
            atf.rotate(-Math.toRadians(angdeg));
            g.drawImage(waterImage, atf, null);
            AffineTransform atf2 = AffineTransform.getTranslateInstance(tx + 200.0D, ty);
            atf2.rotate(-Math.toRadians(angdeg));
            g.drawImage(waterImage, atf2, null);
            g.dispose();
            ImageIO.write(bufferedImage, "jpg", os);
            os.flush();
        } catch (IOException e) {
        } finally {
            if (targetImg != null) {
                targetImg.close();
            }
            if (os != null)
                os.close();
        }
    }

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
                if (!(ValidatorUtil.isNull(str))) {
                    int fitPixelWidth;
                    String fontName;
                    if (str.matches("^[a-zA-Z]*")) {
                        fontName = "Times New Roman";
                        fitPixelWidth = (int) Math.round(xiexian * 0.0428D * str.length());
                    } else {
                        fontName = "方正小标宋简体";
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

    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; ++i) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                ++length;
            }
        }
        return ((length % 2 == 0) ? length / 2 : length / 2 + 1);
    }

    public static void resize(String filePath, int height, int width, boolean bb) {
        try {
            double ratio = 0.0D;
            File f = new File(filePath);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, 4);

            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth())
                    ratio = new Integer(height).doubleValue() / bi.getHeight();
                else {
                    ratio = new Integer(width).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                BufferedImage image = new BufferedImage(width, height, 1);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null),
                        itemp.getHeight(null), Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
                        itemp.getHeight(null), Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "jpg", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reEncodeJPG(File src, File dest) throws Exception {
        FileInputStream inFile = null;
        BufferedImage image = null;
        try {
            JpegReader jr = new JpegReader();
            image = jr.readImage(src);
            ImageIO.write(image, "jpg", dest);
        } catch (Exception e) {
        } finally {
            if (inFile != null) {
                inFile.close();
            }
            if (image != null)
                image.flush();
        }
    }

    public static BufferedImage reEncodeJPG(String filePathName) throws Exception {
        FileInputStream inFile = null;
        FileOutputStream outFile = null;
        BufferedImage image = null;
        try {
            File doc = new File(filePathName);
            JpegReader jr = new JpegReader();
            image = jr.readImage(doc);
            outFile = new FileOutputStream(doc);
            ImageIO.write(image, "jpg", outFile);
            BufferedImage localBufferedImage1 = image;

            return localBufferedImage1;
        } catch (Exception ex) {
        } finally {
            if (inFile != null) {
                inFile.close();
            }
            if (outFile != null)
                outFile.close();
        }
        return null;
    }

    private static Font getFitFontPointSizeFont(Graphics2D g, String fontName, int fontStyle, String pressText,
        int fitPixelWidth) {
        int pointSize = 0;
        Font font;
        int pixelWidthOfPressTxt;
        do {
            pointSize += 2;
            font = new Font(fontName, fontStyle, pointSize);
            FontMetrics fm = g.getFontMetrics(font);
            pixelWidthOfPressTxt = fm.stringWidth(pressText);
        } while (pixelWidthOfPressTxt < fitPixelWidth);
        Font preFont = new Font(fontName, fontStyle, pointSize - 1);

        FontMetrics fm = g.getFontMetrics(preFont);
        int prePixelWidthOfPressTxt = fm.stringWidth(pressText);
        if (Math.abs(prePixelWidthOfPressTxt - fitPixelWidth) < Math.abs(pixelWidthOfPressTxt - fitPixelWidth)) {
            return preFont;
        }
        return font;
    }

    public static void saveAsGoodImage(File input, String format, File output)
        throws IOException, ImageReadException, Exception {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(input);
            if (!(input.getAbsolutePath().equals(output.getAbsolutePath())))
                FileUtil.copyTo(input, output);
        } catch (IIOException e) {
            e.printStackTrace();
            JpegReader jr = new JpegReader();
            bufferedImage = jr.readImage(input);
            ImageIO.write(bufferedImage, format, output);
        } catch (IllegalArgumentException e) {
            if ("Numbers of source Raster bands and source color space components do not match"
                .equals(e.getMessage())) {
                bufferedImage = readGrayScaleJPG(input);
                ImageIO.write(bufferedImage, format, output);
            } else {
                throw e;
            }
        } finally {
            if (bufferedImage != null)
                bufferedImage.flush();
        }
    }

    public static BufferedImage readGrayScaleJPG(File file) throws Exception {
        byte[] bytes = IOUtils.getFileBytes(file);
        return readGrayScaleJPG(bytes);
    }

    public static BufferedImage readGrayScaleJPG(byte[] bytes) throws Exception {
        BufferedImage bufferedImage = null;

        ImageInputStream iis = ImageIO.createImageInputStream(bytes);
        Iterator iter = ImageIO.getImageReaders(iis);
        Exception lastException = null;
        while (iter.hasNext()) {
            ImageReader reader = null;
            try {
                reader = (ImageReader)iter.next();
                ImageReadParam param = reader.getDefaultReadParam();
                reader.setInput(iis, true, true);
                Iterator imageTypes = reader.getImageTypes(0);
                while (imageTypes.hasNext()) {
                    ImageTypeSpecifier imageTypeSpecifier = (ImageTypeSpecifier)imageTypes.next();
                    int bufferedImageType = imageTypeSpecifier.getBufferedImageType();
                    if (bufferedImageType == 10) {
                        param.setDestinationType(imageTypeSpecifier);
                        break;
                    }
                }
                bufferedImage = reader.read(0, param);
                if (null != bufferedImage)
                {
                    if (null == reader)
                        break ;
                    reader.dispose();
                    break ;
                }
            }
            catch (Exception e)
            {
                lastException = e;
            } finally {
                if (null != reader) reader.dispose();
            }
        }

        if ((null == bufferedImage) &&
            (null != lastException)) {
            label191: throw lastException;
        }

        iis.close();
        return bufferedImage;
    }

    /*获取图片格式:gif png JPEG*/
    public static String getImageFormatName(File file)throws IOException{
        String formatName = null;
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> imageReader =  ImageIO.getImageReaders(iis);
        if(imageReader.hasNext()){
            ImageReader reader = imageReader.next();
            formatName = reader.getFormatName();
        }

        return formatName;
    }

}
