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
    
    //mail
    private static String mailHost;
    private static String mailUserName;
    private static String mailPassword;
    private static String mailPort;
    private static String mailProtocol;
    private static String mailSmtpAuth;
    private static String mailSmtpTimeOut;
    
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
                
                //mail
                mailHost = properties.getProperty("sys.mail.forever.host");
                mailUserName = properties.getProperty("sys.mail.forever.username");
                mailPassword = properties.getProperty("sys.mail.forever.password");
                mailPort = properties.getProperty("sys.mail.forever.port");
                mailProtocol = properties.getProperty("sys.mail.forever.protocol");
                mailSmtpAuth = properties.getProperty("sys.mail.forever.smtp.auth");
                mailSmtpTimeOut = properties.getProperty("sys.mail.forever.smtp.timeout");
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

    public static String getDriverClassName() {
        return driverClassName;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUploadPath() {
        return uploadPath;
    }

    public static String getDownloadPath() {
        return downloadPath;
    }

	public static String getMailHost() {
		return mailHost;
	}

	public static String getMailUserName() {
		return mailUserName;
	}

	public static String getMailPassword() {
		return mailPassword;
	}

	public static String getMailPort() {
		return mailPort;
	}

	public static String getMailProtocol() {
		return mailProtocol;
	}

	public static String getMailSmtpAuth() {
		return mailSmtpAuth;
	}

	public static String getMailSmtpTimeOut() {
		return mailSmtpTimeOut;
	}

}
