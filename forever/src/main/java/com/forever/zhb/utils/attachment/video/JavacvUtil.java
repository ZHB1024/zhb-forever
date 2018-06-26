package com.forever.zhb.utils.attachment.video;

import com.forever.zhb.utils.ImageUtils;
import com.forever.zhb.utils.StringUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavacvUtil {

    private static Logger logger = LoggerFactory.getLogger(JavacvUtil.class);

    public static final int AV_TIME_BASE =  1000000;

    public static final int FRAME_NUMBER = 5;

    public static final String SCREEN_CUT_TEMP_PATH = "/log/upload/screenTemp.jpg";
    public static final String SCREEN_PICTURE_FORMAT = SCREEN_CUT_TEMP_PATH.split(".")[1];


    /*
     * 视频大小 MB
     * @param videoPath
     * @return
     */
    public static BigDecimal getVideoSize(String videoPath) {
        File source = new File(videoPath);
        FileChannel fc = null;
        BigDecimal size ;
        try {
            FileInputStream fis = new FileInputStream(source);
            fc = fis.getChannel();
            BigDecimal fileSize = new BigDecimal(fc.size());
            size = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP);
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
        return new BigDecimal(0);
    }

    private static Map<String, Object> getVideoParameters(String videofile){
        if (StringUtil.isBlank(videofile)) {
            return null;
        }
        Map<String, Object> parameters = new HashMap<String, Object>();
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
        try {
            ff.start();
            int lenght = ff.getLengthInFrames();
            long longInIime = ff.getLengthInTime()/AV_TIME_BASE;
            String videoSize = getVideoSize(videofile) + "MB";
            double frameRate = ff.getFrameRate();
            String format = ff.getFormat();
            int imageHeight = ff.getImageHeight();
            int imageWidth = ff.getImageWidth();
            parameters.put("frameLength", lenght);
            parameters.put("timeLength", longInIime);
            parameters.put("videoSize", videoSize);
            parameters.put("frameRate", frameRate);
            parameters.put("format", format);
            parameters.put("frameHeight", imageHeight);
            parameters.put("frameWidth", imageWidth);
            ff.stop();
            return parameters;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        if (targetFile.isFile()) {
            targetFile.delete();
        }
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
        Java2DFrameConverter converter = new Java2DFrameConverter();
        try {
            ff.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int lenght = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        try{
            while (i < lenght) {
                // 过滤前FRAME_NUMBER帧，避免出现全黑的图片，依自己情况而定
                f = ff.grabFrame();
                if ((i > FRAME_NUMBER) && (f.image != null)) {
                    break;
                }
                i++;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        BufferedImage bi = converter.getBufferedImage(f);
        try {
            ImageIO.write(bi, SCREEN_PICTURE_FORMAT, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ff.flush();
        if (ff != null) {
            try {
                ff.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long screentCutTime = System.currentTimeMillis() - start;
        logger.info("screentCutTime：" + screentCutTime);
    }

    public static void transferCut(String videofile, String afterConvertPath) {
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(videofile);
        Frame captured_frame = null;
        FFmpegFrameRecorder recorder = null;
        Java2DFrameConverter converter = new Java2DFrameConverter();

        try {
            logger.info("转换视频开始。。。。");

            frameGrabber.start();
            recorder = new FFmpegFrameRecorder(afterConvertPath + "." + getVideoFormat(frameGrabber.getFormat()), frameGrabber.getImageWidth(), frameGrabber.getImageHeight(),frameGrabber.getAudioChannels());
            //int videoCodec = frameGrabber.getVideoCodec();
            //avcodec.AV_CODEC_ID_H264
            //AV_CODEC_ID_MPEG4
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.setFormat(getVideoFormat(frameGrabber.getFormat()));
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
                BufferedImage img = converter.convert(captured_frame);
                if (null != img) {
                    BufferedImage tempImage = ImageUtils.pressText(img, 0.3f, 3, 3, new String[]{"chsi"});
                    captured_frame  = converter.convert(tempImage);
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
        if (StringUtil.isBlank(format)) {
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
