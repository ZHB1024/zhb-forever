package com.forever.zhb.utils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Geoip2Util {

    protected static final Logger logger = LoggerFactory.getLogger(Geoip2Util.class);

    public static DatabaseReader reader;

    static {
        String classPath = Geoip2Util.class.getClassLoader().getResource("").getPath();
        File database = new File(classPath + "/city/GeoLite2-City.mmdb");
        try{
            reader = new DatabaseReader.Builder(database).build();
        }catch(IOException e){
            logger.error("init Geoip2 DatabaseReader fail....");
            e.printStackTrace();
        }
    }

    public static void init(){
        String classPath = Geoip2Util.class.getClassLoader().getResource("").getPath();
        File database = new File(classPath + "/city/GeoLite2-City.mmdb");
        try{
            reader = new DatabaseReader.Builder(database).build();
        }catch(IOException e){
            logger.error("init Geoip2 DatabaseReader fail....");
            e.printStackTrace();
        }
    }

    public static String getCity(String ip){
        if (null == reader){
            init();
        }
        try{
            //111.202.226.100
            InetAddress ipAddress = InetAddress.getByName(ip);//171.108.233.157
            CityResponse cityResponse = reader.city(ipAddress);
            City city = cityResponse.getCity();

            /*System.out.println(city.getName()); // 'Nanning'
            Postal postal = cityResponse.getPostal();
            System.out.println(postal.getCode()); // 'null'
            System.out.println(city.getNames().get("zh-CN")); // '南宁'
            Location location = cityResponse.getLocation();
            System.out.println(location.getLatitude());  // 22.8167
            System.out.println(location.getLongitude()); // 108.3167*/

            String value = city.getNames().get("zh-CN");
            if (StringUtil.isBlank(value)){
                value = "未知";
            }
            return value;
        }catch(Exception e){
            logger.error("Geoip2 getCity fail...");
            e.printStackTrace();
        }
        return "未知";
    }

    public static String getProvince(String ip){
        if (null == reader){
            init();
        }
        try{
            //111.202.226.100
            InetAddress ipAddress = InetAddress.getByName(ip);//171.108.233.157
            CityResponse cityResponse = reader.city(ipAddress);
            Subdivision subdivision = cityResponse.getMostSpecificSubdivision();

            /*System.out.println(subdivision.getName());   // 'Guangxi Zhuangzu Zizhiqu'
            System.out.println(subdivision.getIsoCode()); // '45'
            System.out.println(subdivision.getNames().get("zh-CN")); // '广西壮族自治区'*/

            String value = subdivision.getNames().get("zh-CN");
            if (StringUtil.isBlank(value)){
                value = "未知";
            }
            return value;
        }catch(Exception e){
            logger.error("Geoip2 getProvince fail...");
            e.printStackTrace();
        }
        return "未知";
    }

    public static String getCountry(String ip){
        if (null == reader){
            init();
        }
        try{
            //111.202.226.100
            InetAddress ipAddress = InetAddress.getByName(ip);//171.108.233.157
            CityResponse cityResponse = reader.city(ipAddress);
            Country country = cityResponse.getCountry();

            /*System.out.println(country.getIsoCode());               // 'CN'
            System.out.println(country.getName());                  // 'China'
            System.out.println(country.getNames().get("zh-CN"));    // '中国'*/

            String value = country.getNames().get("zh-CN");
            if (StringUtil.isBlank(value)){
                value = "未知";
            }
            return value;
        }catch(Exception e){
            logger.error("Geoip2 getCountry fail...");
            e.printStackTrace();
        }
        return "未知";
    }


}
