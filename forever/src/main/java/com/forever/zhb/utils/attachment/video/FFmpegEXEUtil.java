package com.forever.zhb.utils.attachment.video;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

public class FFmpegEXEUtil {
	
	/**
	 *  ffmpeg  相关源代码在 it.sauronsoftware.jave 包内
	 *  能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	 *  
	 */
	
	public static final String RESOURCE_PATH = FFmpegEXEUtil.class.getClassLoader().getResource("").getPath();
	
	public static final String FFMPEG_EXE_PATH = RESOURCE_PATH + "video/ffmpeg.exe";
	public static final String MENCODER_EXE_PATH = RESOURCE_PATH + "video/mencoder.exe";
	
	public static final int AV_TIME_BASE =  1000000;

	
	/*
	 * 视频时长 单位秒
	 * @param videoPath
	 * @return
	 */
	public static long getVideoLength(String videoPath) {
		File source = new File(videoPath);
		Encoder encoder = new Encoder();
		MultimediaInfo m;
		try {
			m = encoder.getInfo(source);
			long ls = m.getDuration();
			return ls/1000;
		} catch (EncoderException e) {
			e.printStackTrace();
		}
		return 0L;
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
	
	/*
	 * 视频格式
	 * @param videoPath
	 * @return
	 */
	public static String getVideoContentType(String videoPath) {
		File source = new File(videoPath);
		Encoder encoder = new Encoder();
		MultimediaInfo m;
		try {
			m = encoder.getInfo(source);
			return m.getFormat();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static boolean checkVideoContentType(String type){
		switch (type) {
		case "avi":
		case "mp4":
		case "wmv":
		case "mpg":
		case "mov":
		case "3gp":
		case "asf":
		case "asx":
		case "flv":
			return true;
		case "wmv9":
		case "rm":
		case "rmvb":
			return false;
		default:
			return false;
		}
	}
	
	/*
	 * 视频高度
	 * @param videoPath
	 * @return
	 */
	public static int getVideoHeight(String videoPath) {
		File source = new File(videoPath);
		Encoder encoder = new Encoder();
		MultimediaInfo m;
		try {
			m = encoder.getInfo(source);
			int height = m.getVideo().getSize().getHeight();
			return height;
		} catch (EncoderException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * 视频宽度
	 * @param videoPath
	 * @return
	 */
	public static int getVideoWidth(String videoPath) {
		File source = new File(videoPath);
		Encoder encoder = new Encoder();
		MultimediaInfo m;
		try {
			m = encoder.getInfo(source);
			int width = m.getVideo().getSize().getWidth();
			return width;
		} catch (EncoderException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	/*
	 * 截屏
	 * @param second
	 * 截取视频第second秒的图片
	 * @return
	 */
	public static boolean makeScreenCut(String videoPath,String picturePathName,int second) {
		File file = new File(videoPath);
		if (!file.exists()) {
			return false;
		}
		List<String> commands = new ArrayList<String>();
		//getPicture(commands, videoPath, picturePathName);
		getPictureBySecond(commands, videoPath, second, picturePathName);
		//getDynamicPicture(commands, videoPath, second, picturePathName);
		BufferedReader stdout = null;  
		try {

			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			Process p = builder.start();
			//截屏比较耗时，主线程sleep，尤其要截取的second比较大时
			doWaitFor(p);
			
			p.destroy();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (null != stdout) {
					stdout.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 转换视频
	 * @param videoPath  
	 * @param afterConvertPath  
	 * 
	 * @return
	 */
	public static String convertVideo(String videoPath,String afterConvertPath){
		List<String> commend = new ArrayList<String>();  
		getConvertVideo(commend,videoPath,afterConvertPath);
        try {  
            //调用线程命令启动转码
            ProcessBuilder builder = new ProcessBuilder();  
            builder.command(commend);  
            Process p = builder.start();
            //p.waitFor();
            doWaitFor(p);
            p.destroy();
            return afterConvertPath;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
	}
	
	public static int doWaitFor(Process p) {  
        int exitValue = -1; // returned to caller when p is finished  
        BufferedReader br = null;
        try {  
        	br = new BufferedReader(new InputStreamReader( p.getInputStream()));  
            while (StringUtils.isNotBlank(br.readLine())) {  
            }  
        } catch (Exception e) {  
        	e.printStackTrace();
        	try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}  
        } finally {  
            try {  
                if (null != br) {
					br.close();
				}
  
            } catch (IOException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
        
        return exitValue;  
    }  
	
	//截取图片
	public static void getPicture(List<String> commands,String videoPath,String picturePathName){
		commands.add(FFMPEG_EXE_PATH);

		commands.add("-i");
		commands.add(videoPath);

		commands.add("-y");

		commands.add("-f");

		commands.add("image2");

		commands.add("-t");
		commands.add("0.001");

		commands.add("-s");
		commands.add("1920x1080");// 宽X高

		commands.add(picturePathName);
	}
	
	// 获取second秒的图片
	public static void getPictureBySecond(List<String> commands, String videoPath, int second, String picturePathName) {
		commands.add(FFMPEG_EXE_PATH);

		commands.add("-i");
		commands.add(videoPath);

		commands.add("-y");

		commands.add("-f");

		commands.add("image2");

		commands.add("-ss");
		commands.add(second + "");// 这个参数是设置截取视频多少秒时的画面

		commands.add("-t");
		commands.add("0.001");

		commands.add("-s");
		commands.add("1920x1080");// 宽X高

		commands.add(picturePathName);
	}
	
	
	//获取前second帧的动态图片
	public static void getDynamicPicture(List<String> commands,String videoPath,int second,String picturePathName){
		commands.add(FFMPEG_EXE_PATH);

		commands.add("-i");
		commands.add(videoPath);
		
		commands.add("-vframes");
		commands.add(second+"");// 这个参数是设置截取视频多少秒时的画面

		commands.add("-y");

		commands.add("-f");
		commands.add("gif");

		commands.add(picturePathName);
	}
	
	
	
	public static void getConvertVideo(List<String> commend,String videoPath,String afterConvertPath){
		commend.add(MENCODER_EXE_PATH); 
        commend.add("-i");
        commend.add(videoPath); 
        
        commend.add("-acodec");
        commend.add("copy"); 
        
        commend.add("-vcodec");
        commend.add("copy"); 
        
        commend.add("-f");
        commend.add("flv"); 
        
        /*commend.add("-ab");
        commend.add(56+"");  
        
        commend.add("-ar");
        commend.add(22050 +"");  
        
        commend.add("-b");
        commend.add(500 +"");  
        
        commend.add("-r");
        commend.add(15 +"");  
        
        commend.add("-s");
        commend.add("320x240"); */ 
        
        /*commend.add("-oac");  
        commend.add("lavc");  
        commend.add("-lavcopts");  
        commend.add("acodec=mp3:abitrate=64");  
        commend.add("-ovc");  
        commend.add("xvid");  
        commend.add("-xvidencopts");  
        commend.add("bitrate=600");  
        commend.add("-of");  
        commend.add("avi");  
        commend.add("-o");  */
        commend.add(afterConvertPath);  //"【存放转码后视频的路径，记住一定是.avi后缀的文件名】"
	}
	
	public static void screenCutByLinux(String videoPath, String picturePathName, int second){
		String raw2flvCmd = FFMPEG_EXE_PATH + " -i " + videoPath + " -y -f image2  -ss " + second + " -t 0.001 -s 1920x1080 "  + picturePathName;  
	       try {
	        Runtime.getRuntime().exec(new String[]{"sh","-c",raw2flvCmd});
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    } 
	}
	
}
