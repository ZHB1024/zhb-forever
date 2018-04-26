package com.forever.zhb.utils.attachment.video;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.forever.zhb.utils.ImageUtils;

public class JavacvUtil {
	
	public static Logger logger = LoggerFactory.getLogger(JavacvUtil.class);
	
	public static final int AV_TIME_BASE =  1000000;
	
	public static void test(String videoPath){
		
	}
	
	
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
	
	public static Map<String, Object> getVideoParameters(String videoFile){
		Map<String, Object> parameters = new HashMap<String, Object>();
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFile);
		try {
			grabber.start();
			parameters.put("frameLength", grabber.getLengthInFrames());
			parameters.put("timeLength", grabber.getLengthInTime()/AV_TIME_BASE);
			parameters.put("format", grabber.getFormat());
			parameters.put("frameRate", grabber.getFrameRate());
			parameters.put("videoSize", getVideoSize(videoFile));
			parameters.put("frameHeight", grabber.getImageHeight());
			parameters.put("frameWidth", grabber.getImageWidth());
			grabber.stop();
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			e.printStackTrace();
		}
		return parameters;
	}
	
	
	/**
	 * 获取指定视频的帧并保存为图片至指定目录
	 * @param videofile  源视频文件路径
	 * @param framefile  截取帧的图片存放路径
	 * @throws Exception
	 */
	public static List<String> screenCut(String videofile, String framefile) {
		long start = System.currentTimeMillis();
		
		List<String> parameters = new ArrayList<String>();
		
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
		parameters.add("共： " + lenght + " 帧");
        parameters.add("时长:" + longInIime + " s");
        parameters.add("帧率： " + frameRate);
        parameters.add("format： " + format);
        parameters.add("videoSize： " + videoSize);
        parameters.add("imageHeight： " + imageHeight);
        parameters.add("imageWidth： " + imageWidth);
		
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
		return parameters;
	}
	
	//转换视频格式
	public static void transferCut(String videofile, String afterConvertPath) {
		FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(videofile);
		Frame captured_frame = null;
		FFmpegFrameRecorder recorder = null;
		try {
			frameGrabber.start();
			String format = getVideoFormat(frameGrabber.getFormat());
			recorder = new FFmpegFrameRecorder(afterConvertPath+"."+format, frameGrabber.getImageWidth(), frameGrabber.getImageHeight(),
					frameGrabber.getAudioChannels());
			// avcodec.AV_CODEC_ID_H264
			// avcodec.AV_CODEC_ID_MPEG4
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); 
			recorder.setFormat(format);
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
	
	//转换视频格式，并加水印
	public static void transferCut2(String videofile, String afterConvertPath) {
		FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(videofile);
		Frame captured_frame = null;
		FFmpegFrameRecorder recorder = null;
		Java2DFrameConverter converter1 = new Java2DFrameConverter(); 
		try {
			logger.info("转换视频开始。。。。");
			frameGrabber.start();
			String format = getVideoFormat(frameGrabber.getFormat());
			recorder = new FFmpegFrameRecorder(afterConvertPath + "." + format, frameGrabber.getImageWidth(), frameGrabber.getImageHeight(),
					frameGrabber.getAudioChannels());
			// avcodec.AV_CODEC_ID_H264
			// avcodec.AV_CODEC_ID_MPEG4
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); 
			recorder.setFormat(format);
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
				BufferedImage img = converter1.convert(captured_frame);
				if (null != img) {
					BufferedImage tempImage = ImageUtils.pressText(img, 0.3f, 3, 3, new String[]{"chsi"});
					captured_frame  = converter1.convert(tempImage);
				}
				
				recorder.record(captured_frame);
			}
			logger.info("跳出while循环了");
        } catch (org.bytedeco.javacv.FrameGrabber.Exception | org.bytedeco.javacv.FrameRecorder.Exception e ) {
            logger.info("转换视频时报异常。。。。");
            e.printStackTrace();
            logger.info(e.getMessage());
        }catch (Exception e){
            logger.info("图片加水印时报异常。。。。");
            e.printStackTrace();
            logger.info(e.getMessage());
        }finally{
            logger.info("关闭各种流。。。。");
            if (null != recorder) {
                /*try {
                    logger.info("开始关闭recoder。。。。");
                    recorder.stop();
                    logger.info("关闭recoder结束。。。。");
                } catch (org.bytedeco.javacv.FrameRecorder.Exception e1) {
                    e1.printStackTrace();
                    logger.info("关闭recoder报异常。。。。");
                    logger.info(e1.getMessage());
                }*/
            }
            try {
                logger.info("开始关闭frameGrabber。。。。");
                frameGrabber.stop();
                logger.info("关闭frameGrabber结束。。。。");
            } catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {
                e1.printStackTrace();
                logger.info("关闭frameGrabber报异常。。。。");
                logger.info(e1.getMessage());
            }
            logger.info("转换视频结束。。。。");
		}
	}
	
	public static String getVideoFormat(String format){
        if (StringUtils.isBlank(format)) {
            return "flv";
        }
        
        if (format.contains("rm")) {
            return "flv";
        }
        
        if (format.contains("mp4")) {
            return "mp4";
        }
        
        if (format.contains("avi")) {
            return "avi";
        }
        
        if (format.contains("flv")) {
            return "flv";
        }
        
        if (format.contains("wmv")) {
            return "wmv";
        }
        if (format.contains("mpg")) {
            return "mpg";
        }
        if (format.contains("mov")) {
            return "mov";
        }
        if (format.contains("3gp")) {
            return "3gp";
        }
        if (format.contains("asf")) {
            return "asf";
        }
        if (format.contains("asx")) {
            return "asx";
        }
        if (format.contains("wmv9")) {
            return "flv";
        }
        if (format.contains("rmvb")) {
            return "flv";
        }
        if (format.contains("ogg")) {
            return "flv";
        }
        return "flv";
    }

}
