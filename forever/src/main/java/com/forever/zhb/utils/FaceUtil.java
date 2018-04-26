package com.forever.zhb.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class FaceUtil {

	/* static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);} */
	static {
		System.load("C:/workFile/soft/openCV/install/opencv/build/java/x64/opencv_java320.dll");
	}
	/* static {System.load("openCV/x64/opencv_java320.dll");} */
	static Logger logger = LoggerFactory.getLogger(FaceUtil.class);
	public static CascadeClassifier faceDetector;

	/**
	 * 
	 * @param imageFile
	 *            被检测的图片
	 * @param tempPath
	 *            临时目录用于存放级联筛选器，路末尾一定要有分隔符
	 * @return
	 */
	public static void detectFace(File imageFile, String realPath) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HHmmss");
		String classifierFilePath = realPath + "WEB-INF/classes/openCV/haarcascade_frontalface_alt2.xml";

		faceDetector = new CascadeClassifier(classifierFilePath);
		if (faceDetector.empty()) {
			System.out.println("CascadeClassifier empty");
			return;
		}
		String filePath = imageFile.getPath();
		Mat image = Imgcodecs.imread(filePath);
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		Rect[] rects = faceDetections.toArray();
		for (Rect rect : rects) {
			Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 255, 0));
		}
		logger.info("face numbers:{}",rects.length);
		String resultPath = realPath + "WEB-INF/classes/openCV/result/" +  f.format(new Date()) +".png";
		Imgcodecs.imwrite(resultPath, image);
	}

	public static int getPersonNum(File imageFile, String realPath) {
		// String classifierFilePath = "haarcascade_frontalface_alt.xml";
		/* String classifierFilePath = "haarcascade_frontalface_alt2.xml"; */
		String classifierFilePath = "lbpcascade_frontalface.xml";

		// File tempFile = new File(tempPath+classifierFilePath);
		File tempFile = null;
		if (faceDetector == null || faceDetector.empty()) {
			InputStream is = FaceUtil.class.getResourceAsStream("/openCV/" + classifierFilePath);
			try {
				tempFile = File.createTempFile("haarcascade_frontalface_alt", ".xml");
				FileUtils.copyInputStreamToFile(is, tempFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (faceDetector == null) {
			System.out.println("classifierFilePath:" + tempFile.getAbsolutePath());
			logger.info("classifierFilePath:" + tempFile.getAbsolutePath());
			faceDetector = new CascadeClassifier(tempFile.getAbsolutePath());
		}
		if (faceDetector.empty()) {
			System.out.println("CascadeClassifier empty");
			return -1;
		}
		Mat image = Imgcodecs.imread(imageFile.getPath());
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		int faceCnt2 = 0;
		Rect[] rects = faceDetections.toArray();
		String info = null;
		int index = 0;
		JSONObject json = new JSONObject();
		json.put("filename", imageFile.getName());
		for (Rect rect : rects) {
			if (index == 0) {
				JSONObject area = new JSONObject();
				area.put("x", rect.x);
				area.put("y", rect.y);
				area.put("width", rect.width);
				area.put("height", rect.height);
				area.put("space", rect.width * rect.height);
				area.put("percentage", ((double) (rect.width * rect.height) * 100) / (image.cols() * image.rows()));
				json.put("area0", area);
				// info =
				// String.format("%s\tx:%d\ty:%d\twidth:%d\theight:%d\tarea:%d\tpercentage:%d%%
				// " ,rect.x,
				// rect.y,rect.width,rect.height,rect.width*rect.height,((rect.width*rect.height)*100)/(image.cols()*
				// image.rows()));
			} else {
				JSONObject area = new JSONObject();
				area.put("x", rect.x);
				area.put("y", rect.y);
				area.put("width", rect.width);
				area.put("height", rect.height);
				area.put("space", rect.width * rect.height);
				area.put("percentage", ((double) (rect.width * rect.height) * 100) / (image.cols() * image.rows()));
				json.put("area" + index, area);
				// info +=
				// String.format("\tx:%d\ty:%d\twidth:%d\theight:%d\tarea:%d\tpercentage:%d%%
				// ", rect.x,
				// rect.y,rect.width,rect.height,rect.width*rect.height,((rect.width*rect.height)*100)/(image.cols()*
				// image.rows()));
			}
			/*
			 * 增加了照片中面部在整个照片中所占百分比的限制
			 */
			if (((double) (rect.width * rect.height) * 100) / (image.cols() * image.rows()) > 10) {
				faceCnt2++;
			}
			index++;
			// logger.info("x:"+rect.x+";y:"+rect.y);
		}
		json.put("personNum", faceCnt2);
		logger.info(json.toString());
		// System.out.println(json.toString());
		return faceCnt2;
	}
	
	public static int getPersonNum2(File imageFile, String realPath) {
		// String classifierFilePath = "haarcascade_frontalface_alt.xml";
		/* String classifierFilePath = "haarcascade_frontalface_alt2.xml"; */
		String classifierFilePath = "lbpcascade_frontalface.xml";

		// File tempFile = new File(tempPath+classifierFilePath);
		File tempFile = null;
        if(faceDetector==null || faceDetector.empty()){
            InputStream is=FaceUtil.class.getResourceAsStream("/"+classifierFilePath);
            try {
                tempFile = File.createTempFile("haarcascade_frontalface_alt", ".xml");
                FileUtils.copyInputStreamToFile(is, tempFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                try {
                    if(is!=null){
                        is.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        if(faceDetector==null){
            System.out.println("classifierFilePath:"+tempFile.getAbsolutePath());
            logger.info("classifierFilePath:"+tempFile.getAbsolutePath());
            faceDetector = new CascadeClassifier(tempFile.getAbsolutePath());
        }
        if (faceDetector.empty()) {
            System.out.println("CascadeClassifier empty");
            return -1;
        }
        Mat image = Imgcodecs.imread(imageFile.getPath());
        if(image.empty()){
            logger.info("检测到,OpenCV不能够读取该图片！");
            return 1;
        }
        MatOfRect faceDetections = new MatOfRect();
        Mat grayFrame = new Mat(image.width(),image.height(), CvType.CV_8UC1);
        int faceCnt2 = 0;
        try {
            Imgproc.cvtColor(image, grayFrame, Imgproc.COLOR_BGR2GRAY);
            Imgproc.equalizeHist(grayFrame, grayFrame);
            faceDetector.detectMultiScale(grayFrame, faceDetections);
            
            Rect[] rects = faceDetections.toArray();
            String info = null;
            int index = 0;
            JSONObject json = new JSONObject();
            json.put("filename", imageFile.getName());
            for(Rect rect : rects){
                if(index==0){
                    JSONObject area = new JSONObject();
                    area.put("x", rect.x);
                    area.put("y", rect.y);
                    area.put("width", rect.width);
                    area.put("height", rect.height);
                    area.put("space", rect.width*rect.height);
                    area.put("percentage", ((double)(rect.width*rect.height)*100)/(grayFrame.width()* grayFrame.height()));
                    json.put("area0", area);
//                    info = String.format("%s\tx:%d\ty:%d\twidth:%d\theight:%d\tarea:%d\tpercentage:%d%% " ,rect.x, rect.y,rect.width,rect.height,rect.width*rect.height,((rect.width*rect.height)*100)/(image.cols()* image.rows()));
                }else{
                    JSONObject area = new JSONObject();
                    area.put("x", rect.x);
                    area.put("y", rect.y);
                    area.put("width", rect.width);
                    area.put("height", rect.height);
                    area.put("space", rect.width*rect.height);
                    area.put("percentage", ((double)(rect.width*rect.height)*100)/(grayFrame.width()* grayFrame.height()));
                    json.put("area"+index, area);
//                    info += String.format("\tx:%d\ty:%d\twidth:%d\theight:%d\tarea:%d\tpercentage:%d%% ", rect.x, rect.y,rect.width,rect.height,rect.width*rect.height,((rect.width*rect.height)*100)/(image.cols()* image.rows()));
                }
                /*
                 *增加了照片中面部在整个照片中所占百分比的限制 
                 */
                if(((double)(rect.width*rect.height)*100)/(image.width()* image.height())>10){
                    faceCnt2++;
                }
                index++;
//                logger.info("x:"+rect.x+";y:"+rect.y);
            }
            json.put("personNum", faceCnt2);
            logger.info(json.toString());
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            logger.info("检测到,图片未能识别！");
            return 1;
        }finally {
            if(image!=null){
                image.release();
            }
            if(grayFrame!=null){
                grayFrame.release();
            }
            if(faceDetections!=null){
                faceDetections.release();
            }
        }
        
//        System.out.println(json.toString());
        return faceCnt2;
	}

}
