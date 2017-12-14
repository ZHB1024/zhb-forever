package com.forever.zhb.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyUtil {
    
    private static final Log log = LogFactory.getLog(PropertyUtil.class);
    
    private static String name;
    private static String jdbcUrl;
    private static String driverClassName;
    private static String userName;
    private static String password;
    private static String uploadPath ;
    private static String downloadPath ;
    
    static{
        String propertyPath = System.getenv("propertyPath");
        if (null == propertyPath) {
            log.info("环境变量未配置PropertyPath");
        }else{
            FileInputStream in = null;
            try {
                in = new FileInputStream(propertyPath);
                Properties properties = new Properties();
                properties.load(in);
                jdbcUrl = properties.getProperty("sys.jdbc.datasourse.forever.url");
                driverClassName = properties.getProperty("sys.jdbc.datasourse.forever.driverClassName");
                userName = properties.getProperty("sys.jdbc.datasourse.forever.username");
                password = properties.getProperty("sys.jdbc.datasourse.forever.password");
                uploadPath = properties.getProperty("sys.upload.path");
                downloadPath = properties.getProperty("sys.download.path");
            } catch (IOException e) {
            }
        }
            
    }
    
    public static String getName(){
        return name;
    }

    public static String getJdbcUrl() {
        return jdbcUrl;
    }

    public static void setJdbcUrl(String jdbcUrl) {
        PropertyUtil.jdbcUrl = jdbcUrl;
    }

    public static String getDriverClassName() {
        return driverClassName;
    }

    public static void setDriverClassName(String driverClassName) {
        PropertyUtil.driverClassName = driverClassName;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        PropertyUtil.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        PropertyUtil.password = password;
    }

    public static String getUploadPath() {
        return uploadPath;
    }

    public static String getDownloadPath() {
        return downloadPath;
    }

}
