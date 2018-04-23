package com.forever.zhb.utils.attachment.video;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavacvUtil {
	
	public static Logger logger = LoggerFactory.getLogger(JavacvUtil.class);
	
	public static final int AV_TIME_BASE =  1000000;
	
	
	/*
	 * 视频大小 MB
	 * @param videoPath
	 * @return
	 */
	public static String getVideoSize(String videoPath) {
		File source = new File(videoPath);
		FileChannel fc = null;
		String size = "";
		try {
			FileInputStream fis = new FileInputStream(source);
			fc = fis.getChannel();
			BigDecimal fileSize = new BigDecimal(fc.size());
			size = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) + "MB";
			return size;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != fc) {
				try {
					fc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "0MB";
	}
	
	
	/**
	 * 获取指定视频的帧并保存为图片至指定目录
	 * @param videofile  源视频文件路径
	 * @param framefile  截取帧的图片存放路径
	 * @throws Exception
	 */
	public static void screenCut(String videofile, String framefile) {
		long start = System.currentTimeMillis();
		
		File targetFile = new File(framefile);
		FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
		Java2DFrameConverter converter = new Java2DFrameConverter(); 
		try {
			ff.start();
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			e.printStackTrace();
		}
		int lenght = ff.getLengthInFrames();
		long longInIime = ff.getLengthInTime()/AV_TIME_BASE;
		double frameRate = ff.getFrameRate();
		String format = ff.getFormat();
		String videoSize = getVideoSize(videofile);
		int timeOut = ff.getTimeout();
		int imageHeight = ff.getImageHeight();
		int imageWidth = ff.getImageWidth();
		
		logger.info("时长:" + longInIime + " s");
		logger.info("format： " + format);
		logger.info("videoSize： " + videoSize);
		logger.info("共： " + lenght + " 帧");
		logger.info("帧率： " + frameRate);
		logger.info("timeOut： " + timeOut);
		logger.info("imageHeight： " + imageHeight);
		logger.info("imageWidth： " + imageWidth);
		
		int i = 0;
		Frame f = null;
		while (i < lenght) {
			// 过滤前5帧，避免出现全黑的图片，依自己情况而定
			try {
				f = ff.grabFrame();
			} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
				e.printStackTrace();
			}
			if ((i > 5) && (f.image != null)) {
				break;
			}
			i++;
		}
		BufferedImage bi = converter.getBufferedImage(f);
		try {
			ImageIO.write(bi, "jpg", targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ff.flush();
		try {
			ff.stop();
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			e.printStackTrace();
		}
		
		long screenCutTime = System.currentTimeMillis() - start;
		logger.info("screenCutTime:" + screenCutTime);
	}
	
	public static void transferCut(String videofile, String afterConvertPath) {
		FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(videofile);
		Frame captured_frame = null;
		FFmpegFrameRecorder recorder = null;
		try {
			frameGrabber.start();
			recorder = new FFmpegFrameRecorder(afterConvertPath, frameGrabber.getImageWidth(), frameGrabber.getImageHeight(),
					frameGrabber.getAudioChannels());
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264
																// //AV_CODEC_ID_MPEG4
			recorder.setFormat("flv");
			//recorder.setSampleFormat(frameGrabber.getSampleFormat()); 
			recorder.setSampleRate(frameGrabber.getSampleRate());
			// -----recorder.setAudioChannels(frameGrabber.getAudioChannels());
			recorder.setFrameRate(frameGrabber.getFrameRate());
			recorder.start();
			while (true) {
				captured_frame = frameGrabber.grabFrame();
				if (captured_frame == null) {
					break;
				}
				recorder.setTimestamp(frameGrabber.getTimestamp());
				recorder.record(captured_frame);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
                if (null != recorder) {
                    recorder.stop();
                    recorder.release();
                }
                if (null != frameGrabber) {
                    frameGrabber.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}
	
	
	public static void transferCut2(String videofile, String afterConvertPath) {
		FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(videofile);
		Frame captured_frame = null;
		FFmpegFrameRecorder recorder = null;
		Java2DFrameConverter converter1 = new Java2DFrameConverter(); 
		try {
			frameGrabber.start();
			recorder = new FFmpegFrameRecorder(afterConvertPath, frameGrabber.getImageWidth(), frameGrabber.getImageHeight(),
					frameGrabber.getAudioChannels());
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264
																// //AV_CODEC_ID_MPEG4
			recorder.setFormat("flv");
			//recorder.setSampleFormat(frameGrabber.getSampleFormat()); 
			recorder.setSampleRate(frameGrabber.getSampleRate());
			// -----recorder.setAudioChannels(frameGrabber.getAudioChannels());
			recorder.setFrameRate(frameGrabber.getFrameRate());
			recorder.start();
			while (true) {
				captured_frame = frameGrabber.grabFrame();
				if (captured_frame == null) {
					break;
				}
				recorder.setTimestamp(frameGrabber.getTimestamp());
				
				// 加水印  
				int imgWidth = captured_frame.imageWidth>0?captured_frame.imageWidth:10;
				int imgHeight = captured_frame.imageHeight>0?captured_frame.imageHeight:10;
				Image img = converter1.convert(captured_frame);
				Font font = new Font("宋体", Font.PLAIN, 5);  
	            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);  
	            mark(bufImg, img, "hello chsi", font, Color.ORANGE, 0, 0);  
	            captured_frame  = converter1.convert(bufImg);
				
				
				recorder.record(captured_frame);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
                if (null != recorder) {
                    recorder.stop();
                    recorder.release();
                }
                if (null != frameGrabber) {
                    frameGrabber.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}
	
	
	// 加文字水印  
    public static void mark(BufferedImage bufImg, Image img, String text, Font font, Color color, int x, int y) {  
        Graphics2D g = bufImg.createGraphics();  
        g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);  
        g.setColor(color);  
        g.setFont(font);  
        g.drawString(text, x, y);  
        g.dispose();  
    }  

}
