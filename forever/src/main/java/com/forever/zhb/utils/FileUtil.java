package com.forever.zhb.utils;

import  java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

public class FileUtil {

    public static int blockSize = 8192;
    public static int bufferSize = blockSize * 10;
	
	/**
	    * 读取txt文件的内容
	    * @param file 想要读取的文件对象
	    * @return 返回文件内容
	    */
	   public static String txtString(File file){
	       String result = "";
	       try{
	           BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	           String s = null;
	           while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	               result = result + "\n" +s;
	           }
	           br.close();    
	       }catch(Exception e){
	           e.printStackTrace();
	       }
	       return result;
	   }
	   
	   /**
	    * 读取doc文件内容
	    * @param file 想要读取的文件对象
	    * @return 返回文件内容
	    */
	   public static String docString(File file){
	       String result = "";
	       try{
	           FileInputStream fis = new FileInputStream(file);
	           HWPFDocument doc = new HWPFDocument(fis);
	           Range rang = doc.getRange();
	           result += rang.text();
	           fis.close();
	       }catch(Exception e){
	           e.printStackTrace();
	       }
	       return result;
	   }
	   
	   /**
	    * 过滤目录下的文件
	    * @param dirPath 想要获取文件的目录
	    * @return 返回文件list
	    */
	   public static List<File> getFileList(String dirPath) {
	       File[] files = new File(dirPath).listFiles();
	       List<File> fileList = new ArrayList<File>();
	       for (File file : files) {
	           if (isTxtFile(file.getName())) {
	               fileList.add(file);
	           }
	       }
	       return fileList;
	   }
	   
	   /**
	    * 判断是否为目标文件，目前支持txt xls doc格式
	    * @param fileName 文件名称
	    * @return 如果是文件类型满足过滤条件，返回true；否则返回false
	    */
	   public static boolean isTxtFile(String fileName) {
	       if (fileName.lastIndexOf(".txt") > 0) {
	           return true;
	       }else if (fileName.lastIndexOf(".xls") > 0) {
	           return true;
	       }else if (fileName.lastIndexOf(".doc") > 0) {
	           return true;
	       }
	       return false;
	   }
	   
	   /**
	    * 删除文件目录下的所有文件
	    * @param file 要删除的文件目录
	    * @return 如果成功，返回true.
	    */
	   public static boolean deleteDir(File file){
	       if(file.isDirectory()){
	           File[] files = file.listFiles();
	           for(int i = 0; i < files.length; i++){
	               deleteDir(files[i]);
	           }
	       }
	       file.delete();
	       return true;
	   }

    public static void copyTo(File src, File dest) {
        try {
            FileInputStream fis = new FileInputStream(src);
            copyTo(fis, dest);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("文件不存在,file:%s", new Object[] { src.getAbsolutePath() }), e);
        }
    }

    public static void copyTo(InputStream is, File dest) {
        BufferedInputStream bis = new BufferedInputStream(is, bufferSize);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(dest);
            bos = new BufferedOutputStream(fos, bufferSize);
            int readBytes = 0;
            byte[] block = new byte[blockSize];
            while ((readBytes = bis.read(block)) != -1) {
                bos.write(block, 0, readBytes);
            }
            bos.flush();
            fos.flush();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (null != bos){
                try{
                    bos.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }

            }
            if (null != fos){
                try{
                    fos.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }

            }
            if (null != bis){
                try{
                    bis.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }

            }
            if (null != is){
                try{
                    is.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }

            }
        }
    }
}
