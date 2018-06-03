package com.forever.zhb.thread.jsoup.spider;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.forever.zhb.utils.DateUtil;
import com.forever.zhb.utils.JsoupUtil;
import com.forever.zhb.utils.attachment.DownloadURlUtil;

public class ChildrenRunnable implements Runnable {
    
    private Logger logger = LoggerFactory.getLogger(ChildrenRunnable.class);
    
    private String name;
    private AtomicInteger count = null;
    private List<String> imagePaths;
    
    public ChildrenRunnable(String name, List<String> imagePaths,AtomicInteger count) {
        this.name = name;
        this.imagePaths = imagePaths;
        this.count = count;
    }

    @Override
    public void run() {
        if (null != imagePaths) {
            for (String urlPath : imagePaths) {
                try {
                    int imageName = count.incrementAndGet();
                    String fileName = DateUtil.formatTime("yyyyMMddHHmmss") + "_" + imageName + ".jpg";
                    String filePath = JsoupUtil.getBaseSavePath() + File.separator + DateUtil.TODAY_FORMAT;
                    DownloadURlUtil.downLoadFromUrl(urlPath, fileName, filePath);
                    logger.info(name + ": " + imageName);
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.info(name + " 异常");
                }
            }
        }
    }
}
