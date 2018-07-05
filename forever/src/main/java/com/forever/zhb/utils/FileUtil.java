package com.forever.zhb.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.sanselan.util.IOUtils;

public class FileUtil {

    public static final Map<String, String> FILE_TYPE_MAP = new HashMap();
    public static final Map<String, String> EXCEL_TYPE_MAP = new HashMap();
    private static final byte[] DBF_HEADER = { 3, 48 };

    public static int blockSize = 8192;
    public static int bufferSize = blockSize * 10;

    static {
        getAllFileType();
    }

    private static void getAllFileType() {
        FILE_TYPE_MAP.put("ffd8ff", "jpg");
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png");
        FILE_TYPE_MAP.put("47494638396126026f01", "gif");
        FILE_TYPE_MAP.put("49492a00227105008037", "tif");
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp");
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp");
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp");

        FILE_TYPE_MAP.put("504b0304", "zip");

        EXCEL_TYPE_MAP.put("d0cf11e0a1b11ae10000", "xls");
        EXCEL_TYPE_MAP.put("504b0304140006000800", "xlsx");
        EXCEL_TYPE_MAP.put("d0cf11e0a1b11ae10000", "xls");
        EXCEL_TYPE_MAP.put("504b03040a0000000000", "xlsx");
    }
	
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


    /**
     * 获取文件类型
     * @param filePath 文件路径
     * @return 文件类型
     */
    public static String getFileType(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] bytes = IOUtils.getFileBytes(file);
        return getFileType(bytes);
    }

    /**
     * 获取文件类型
     * @param bytes 文件字节数组
     * @return 文件类型
     */
    public static String getFileType(byte[] bytes) {
        String res = null;
        FileInputStream is = null;
        try {
            String fileCode = bytesToHexString(bytes);

            Iterator keyIter = FILE_TYPE_MAP.keySet().iterator();
            while (keyIter.hasNext()) {
                String key = (String) keyIter.next();
                if ((key.toLowerCase().startsWith(fileCode.toLowerCase()))
                    || (fileCode.toLowerCase().startsWith(key.toLowerCase()))) {
                    res = (String) FILE_TYPE_MAP.get(key);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if ((src == null) || (src.length <= 0)) {
            return null;
        }
        for (int i = 0; i < src.length; ++i) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static boolean isExcel(String filePath) {
        boolean flag = true;
        String res = null;
        FileInputStream is = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[10];
            is.read(b, 0, b.length);
            String fileCode = bytesToHexString(b);

            Iterator keyIter = EXCEL_TYPE_MAP.keySet().iterator();
            while (keyIter.hasNext()) {
                String key = (String) keyIter.next();
                if ((key.toLowerCase().startsWith(fileCode.toLowerCase()))
                    || (fileCode.toLowerCase().startsWith(key.toLowerCase()))) {
                    res = (String) EXCEL_TYPE_MAP.get(key);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if ((!(StringUtils.equals(res, "xls"))) && (!(StringUtils.equals(res, "xlsx")))) {
            flag = false;
        }
        return flag;
    }

    public static boolean isDBF(InputStream is) {
        boolean res = false;
        try {
            if (is != null) {
                byte[] b = new byte[1];
                is.read(b, 0, b.length);
                res = (b[0] == DBF_HEADER[0]) || (b[0] == DBF_HEADER[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }


}
