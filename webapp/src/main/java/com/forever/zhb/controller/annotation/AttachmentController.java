package com.forever.zhb.controller.annotation;

import com.forever.zhb.Constants;
import com.forever.zhb.Constants_Web;
import com.forever.zhb.basic.BasicController;
import com.forever.zhb.criteria.ForeverCriteria;
import com.forever.zhb.dic.DeleteFlagEnum;
import com.forever.zhb.dic.FileTypeEnum;
import com.forever.zhb.page.Page;
import com.forever.zhb.pojo.FileInfoData;
import com.forever.zhb.pojo.UserInfoData;
import com.forever.zhb.service.AttachmentManager;
import com.forever.zhb.service.IForeverManager;
import com.forever.zhb.service.UserManager;
import com.forever.zhb.util.WebAppUtil;
import com.forever.zhb.utils.DownloadUtil;
import com.forever.zhb.utils.ImageUtil;
import com.forever.zhb.utils.ImageUtils;
import com.forever.zhb.utils.PropertyUtil;
import com.forever.zhb.utils.StringUtil;
import com.forever.zhb.utils.attachment.ExcelUtil;
import com.forever.zhb.utils.attachment.video.FFmpegEXEUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Decoder;


@Controller
@RequestMapping("/htgl/attachmentController")
public class AttachmentController extends BasicController {

	private Logger logger = LoggerFactory.getLogger(AttachmentController.class);

	@Resource(name = "foreverManager")
	private IForeverManager foreverManager;

	@Resource(name = "attachmentManager")
	private AttachmentManager attachmentManager;

	@Resource(name = "userManager")
	private UserManager userManager;

	@RequestMapping("/test")
	public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
		/*String sourceFile = "C:\\Users\\ZHB\\Videos\\w3school.gif";
		String zipFile ="C:\\Users\\ZHB\\Videos\\test.zip";
		List regExp = new ArrayList();
        regExp.add(".+\\.dbf$");
        String password = RandomUtil.getRandomString(6);
        ZipUtils.createEncryptedFile(sourceFile, zipFile, regExp, request, response, password);*/
		
		/*String url= "https://v.qq.com/iframe/txp/player.html?vid=u0627uagpoy";
		Document document = Jsoup.connect(url).get();
		if (null != document) {
			Element el = document.getElementById("video_container");
			logger.info(el.html());
			logger.info(el.outerHtml());
			Attributes attributes = document.attributes();
			if (null != attributes) {
				for (Attribute attr : attributes) {
					logger.info(attr.html());
					logger.info(attr.getKey());
					logger.info(attr.getValue());
					logger.info("-------------------------------------------------------------------");
				}
			}
			
			Elements elements = document.getElementsByAttribute("body");
			if (null != elements) {
				for (Element element : elements) {
					logger.info(element.outerHtml());
					logger.info("-------------------------------------------------------------------");
				}
			}
		}*/
		
		
		return "htgl.upload.index";
	}

    @RequestMapping("/layerContent")
    public void layerContent(HttpServletRequest request, HttpServletResponse response,String image,String id) {
        JSONObject jsonObject = new JSONObject();
        PrintWriter pw = null;

        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"box\">");
        sb.append(      image);
        sb.append(     "<input id=\"userId\" type=\"hidden\" value='" + id + "' >");
        sb.append("</div>");
        sb.append("<div align=\"center\" style=\"margin-top:20px;\" > ");
        sb.append(     "<button id=\"cut_upload\" type=\"button\" class=\"layui-btn\" >");
        sb.append(     "上传</button>");
        sb.append(     "<button id=\"cut_cancle\" type=\"button\" class=\"layui-btn\" >");
        sb.append(     "取消</button>");
        sb.append("</div>");
        sb.append("<script type=\"text/javascript\">");
        sb.append("     $('#new_me_image').cropper({");
        sb.append("             aspectRatio: 1 / 1,");
        sb.append("             viewMode:1,");
        sb.append("             crop: function (e) {");
        sb.append("                 ");
        sb.append("             }");
        sb.append("     });");
        sb.append("     $(\"#cut_upload\").on(\"click\", function () {");
        sb.append("          var image_target = $('#new_me_image').cropper('getData', true); ");
        sb.append("          var image_content = $('#new_me_image').attr('src');");
        sb.append("          var userId = $('#userId').val();");
        sb.append("          var data = {");
        sb.append("                         'id':userId,");
        sb.append("                         'image_content':image_content,");
        sb.append("                         'x':image_target.x,");
        sb.append("                         'y':image_target.y,");
        sb.append("                         'width':image_target.width,");
        sb.append("                         'height':image_target.height");
        sb.append("          };");

        sb.append("          $.ajax({");
        sb.append("             url: '/htgl/attachmentController/uploadHeadPhoto',");
        sb.append("             type: 'POST',");
        sb.append("             data:data,");
        sb.append("             success: function (result) { ");
        sb.append("                 var resultCode = result.code; ");
        sb.append("                 layer.closeAll(); ");
        sb.append("                 window.location.reload() ; ");
        sb.append("             }, ");
        sb.append("             error: function (result) {");
        sb.append("                 layer.closeAll(); ");
        sb.append("             }");
        sb.append("          });");

        sb.append("     });");
        sb.append("     $(\"#cut_cancle\").on(\"click\", function () {");
        sb.append("          layer.closeAll(); ");
        sb.append("          window.location.reload() ; ");
        sb.append("     });");
        sb.append("</script>");

        jsonObject.put("msg", sb.toString());
        try {
            pw = response.getWriter();
            pw.write(jsonObject.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

    @RequestMapping("/uploadHeadPhoto")
    public void uploadHeadPhoto(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        UserInfoData userInfo = WebAppUtil.getUserInfoData(request);
        if (StringUtil.isBlank(id) || null == userInfo || !id.equals(userInfo.getId())){

            return;
        }
	    String x = request.getParameter("x");
	    String y = request.getParameter("y");
	    String width = request.getParameter("width");
	    String height = request.getParameter("height");
	    String image_content = request.getParameter("image_content");


        /*Base64.Decoder decoder = Base64.getDecoder();
        String image_value = image_content.replaceAll("data:image/jpeg;base64,","");
        byte[] decodedBytes = null;
        // 将字符串格式的imagedata转为二进制流（biye[])的decodedBytes
        decodedBytes = decoder.decode(image_value);
        for(int i=0;i<decodedBytes.length;++i){
            if(decodedBytes[i]<0) {
                //调整异常数据
                decodedBytes[i]+=256;
            }
        }*/

        BASE64Decoder decoder = new BASE64Decoder();
        String image_value = image_content.replaceAll("data:image/jpeg;base64,","");
        byte[] decodedBytes = null;
        try {
            // 将字符串格式的imagedata转为二进制流（biye[])的decodedBytes
            decodedBytes = decoder.decodeBuffer(image_value);
            for(int i=0;i<decodedBytes.length;++i){
                if(decodedBytes[i]<0) {
                    //调整异常数据
                    decodedBytes[i]+=256;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        decodedBytes = ImageUtil
            .equimultipleConvertToByte(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(width),Integer.parseInt(height),decodedBytes );

        /*int faceCount = FaceUtil.getPersonNum(decodedBytes);
        if (faceCount <= 0){
            return;
        }*/


        JSONObject jsonObject = new JSONObject();
        PrintWriter pw = null;
        String ctxPath = request.getContextPath();
        String filePath = PropertyUtil.getUploadPath() + File.separator + Constants.TARGET_NAME + File.separator
            + Constants.IMAGE_PATH;
        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }
        OutputStream licOutput = null;
        String fileName = userInfo.getName() + ".jpg";

        try {
            String uploadPathFile = filePath + File.separator + fileName;
            File licName = new File(uploadPathFile);
            licOutput = new FileOutputStream(licName);
            licOutput.write(decodedBytes);
            licOutput.flush();

            FileInfoData fileInfoData = new FileInfoData();
            fileInfoData.setCreateTime(Calendar.getInstance());
            fileInfoData.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
            fileInfoData.setFilePath(filePath);
            fileInfoData.setFileSize(Long.parseLong(String.valueOf(decodedBytes.length)));
            fileInfoData.setFileType("image/jpeg");
            fileInfoData.setFileName(fileName);
            fileInfoData.setType(FileTypeEnum.HEAD.getIndex());
            attachmentManager.saveOrUpdate(fileInfoData);

            UserInfoData data = userManager.getUserInfoById(id);
            data.setHeadPhoto(fileInfoData.getId());
            userManager.saveOrUpdate(data);
            WebAppUtil.setUserInfoData(request,data);

        } catch (Exception e) {
            logger.error("上传头像失败....");
            e.printStackTrace();
        } finally {
            try {
                if (null != licOutput) {
                    licOutput.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", fileName);
        try {
            pw = response.getWriter();
            pw.write(jsonObject.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

    /* download */
    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response, String id) {
        FileInputStream fis = null;
        ServletOutputStream sos = null;
        if (StringUtils.isBlank(id)) {
            String rootPath = WebAppUtil.getRootPath(request);
            String imagePath = rootPath + "images" + File.separator + "loading.gif";
            response.setContentType("image/jpeg");
            File defaultImage = new File(imagePath);
            try {
                fis = new FileInputStream(defaultImage);
                sos = response.getOutputStream();
                int lenght;
                byte[] buf = new byte[1024];
                while((lenght = fis.read(buf, 0, 1024)) != -1){
                    sos.write(buf,0,lenght);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != sos) {
                    try {
                        sos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != fis) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return;
        }

        // 查询
        FileInfoData fileInfo = attachmentManager.getFileById(id);
        if (null == fileInfo){
            return;
        }
        // will return -1 if no header...(没缓存的照片时no header)
        long clientLastModified = request.getDateHeader("If-Modified-Since");
        if (clientLastModified != -1) {//有缓存照片
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }

        String filePath = fileInfo.getFilePath() + File.separator;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        filePath += fileInfo.getFileName();
        response.setContentType(fileInfo.getFileType());
        long currentTimeMillis = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", currentTimeMillis);
        response.setDateHeader("Expires", currentTimeMillis + 1000*Constants_Web.TIME_OUTS);//缓存30天
        File image = new File(filePath);
        try {
            fis = new FileInputStream(image);
            sos = response.getOutputStream();
            if (fileInfo.getType() == 0 ){
                ImageUtils.pressText(fis, sos, 0.3f, 3, 3, new String[] { "zhb_forever" });
            }else{
                int lenght;
                byte[] buf = new byte[1024];
                while((lenght = fis.read(buf, 0, 1024)) != -1){
                    sos.write(buf,0,lenght);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != sos) {
                try {
                    sos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

/*-----------------------------------------照片-----------------------------------------------------------*/

	@RequestMapping("/toUploadPicture")
	public String toUploadPicture(HttpServletRequest request, HttpServletResponse response) {
		return "htgl.picture.toUpload";
	}

	@RequestMapping("/uploadPicture")
	public void uploadPicture(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        PrintWriter pw = null;
		String ctxPath = request.getContextPath();
		String filePath = PropertyUtil.getUploadPath() + File.separator + Constants.TARGET_NAME + File.separator
				+ Constants.IMAGE_PATH;
		File fileUpload = new File(filePath);
		if (!fileUpload.exists()) {
			fileUpload.mkdirs();
		}
		InputStream licInput = null;
		OutputStream licOutput = null;
		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得文件：
		MultipartFile license = multipartRequest.getFile("file");
		Long fileSize = license.getSize();
		String fileType = license.getContentType();
		String fileName = "";
		if (license.getSize() > Constants.IMAGE_MAX_SIZE) {
		}
		try {
			// 获得文件名：
			fileName = license.getOriginalFilename();
			String uploadPathFile = filePath + File.separator + fileName;
			// 获得输入流：
			licInput = license.getInputStream();
			File licName = new File(uploadPathFile);
			/*
			 * if (licName.exists()) { licName.delete(); }
			 */
			licOutput = new FileOutputStream(licName);
			byte[] b = new byte[1024];
			int len;
			while ((len = licInput.read(b)) != -1) {
				licOutput.write(b, 0, len);
			}
			licOutput.flush();
			FileInfoData fileInfoData = new FileInfoData();
			fileInfoData.setCreateTime(Calendar.getInstance());
			fileInfoData.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
			fileInfoData.setFilePath(filePath);
			fileInfoData.setFileSize(fileSize);
			fileInfoData.setFileType(fileType);
			fileInfoData.setFileName(fileName);
			fileInfoData.setType(FileTypeEnum.IMAGE.getIndex());
            attachmentManager.saveOrUpdate(fileInfoData);
		} catch (Exception e) {
		    logger.error("上传图片失败....");
			e.printStackTrace();
            jsonObject.put("code", 1);
            jsonObject.put("msg", "");
            jsonObject.put("data", fileName);
            try {
                pw = response.getWriter();
                pw.write(jsonObject.toString());
                pw.flush();
                pw.close();
            } catch (IOException io) {
                io.printStackTrace();
            }
			return;
		} finally {
			try {
				if (null != licOutput) {
					licOutput.close();
				}
				if (null != licInput) {
					licInput.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", fileName);
        try {
            pw = response.getWriter();
            pw.write(jsonObject.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		return;
	}

    /* query picture */
    @RequestMapping("/pictureIndex")
    public String pictureIndex(HttpServletRequest request, HttpServletResponse response,String fileName) {
        String start = request.getParameter("start");
        if (StringUtils.isBlank(start)) {
            start = "0";
        }
        Page filePage = attachmentManager.queryFiles(FileTypeEnum.IMAGE.getIndex(),fileName,Integer.valueOf(start),Constants.PAGE_SIZE);
        request.setAttribute("page", filePage);
        request.setAttribute("fileName", fileName);
        return "htgl.picture.index";
    }


/*-----------------------------------------------video----------------------------------------------------*/

    /* query picture */
    @RequestMapping("/videoIndex")
    public String videoIndex(HttpServletRequest request, HttpServletResponse response,String fileName) {
        String start = request.getParameter("start");
        if (StringUtils.isBlank(start)) {
            start = "0";
        }
        Page filePage = attachmentManager.queryFiles(FileTypeEnum.VIDEO.getIndex(),fileName,Integer.valueOf(start),Constants.PAGE_SIZE);
        request.setAttribute("page", filePage);
        request.setAttribute("fileName", fileName);
        return "htgl.video.index";
    }

	@RequestMapping("/toUploadVideo")
	public String toUploadVideo(HttpServletRequest request, HttpServletResponse response) {
		return "htgl.video.toUpload";
	}

	@RequestMapping("/uploadVideo")
	public void uploadVideo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        PrintWriter pw = null;
		String ctxPath = request.getContextPath();
		String filePath = PropertyUtil.getUploadPath() + File.separator + Constants.TARGET_NAME + File.separator
				+ Constants.VIDEO_PATH;
		File fileUpload = new File(filePath);
		if (!fileUpload.exists()) {
			fileUpload.mkdirs();
		}
		InputStream licInput = null;
		OutputStream licOutput = null;
		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得文件：
		MultipartFile license = multipartRequest.getFile("file");
		Long fileSize = license.getSize();
		String fileType = license.getContentType();
		String fileName = "";
		if (license.getSize() > Constants.VIDEO_MAX_SIZE) {
		}
		try {
			// 获得文件名：
			fileName = license.getOriginalFilename();
			String uploadPathFile = filePath + File.separator + fileName;
			// 获得输入流：
			licInput = license.getInputStream();
			File licName = new File(uploadPathFile);
			/*
			 * if (licName.exists()) { licName.delete(); }
			 */
			licOutput = new FileOutputStream(licName);
			byte[] b = new byte[1024];
			int len;
			while ((len = licInput.read(b)) != -1) {
				licOutput.write(b, 0, len);
			}
			licOutput.flush();
			FileInfoData fileInfoData = new FileInfoData();
			fileInfoData.setCreateTime(Calendar.getInstance());
			fileInfoData.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
			fileInfoData.setFilePath(filePath);
			fileInfoData.setFileSize(fileSize);
			fileInfoData.setFileType(fileType);
			fileInfoData.setFileName(fileName);
			fileInfoData.setType(FileTypeEnum.VIDEO.getIndex());
			attachmentManager.saveOrUpdate(fileInfoData);

		} catch (Exception e) {
			e.printStackTrace();
            jsonObject.put("code", 1);
            jsonObject.put("msg", "");
            jsonObject.put("data", fileName);
            try {
                pw = response.getWriter();
                pw.write(jsonObject.toString());
                pw.flush();
                pw.close();
            } catch (IOException io) {
                io.printStackTrace();
            }
			return;
		} finally {
			try {
				if (null != licOutput) {
					licOutput.close();
				}
				if (null != licInput) {
					licInput.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", fileName);
        try {
            pw = response.getWriter();
            pw.write(jsonObject.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return;
	}
	
	@RequestMapping("/makeScreenCutVideo")
	public String makeScreenCutVideo(HttpServletRequest request, HttpServletResponse response) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得文件：
		MultipartFile license = multipartRequest.getFile("firstFile");
		
		CommonsMultipartFile cf = (CommonsMultipartFile) license;
		DiskFileItem fi = (DiskFileItem)cf.getFileItem();
		File f = fi.getStoreLocation();
		String filePath = f.getPath();
		
		System.out.println(FFmpegEXEUtil.getVideoSize(filePath));
		System.out.println(FFmpegEXEUtil.getVideoLength(filePath));
		System.out.println(FFmpegEXEUtil.getVideoHeight(filePath));
		System.out.println(FFmpegEXEUtil.getVideoWidth(filePath));
		
		//String videoType = VideoUtil.getVideoContentType(filePath);
		//String afterConvertPath = realPath + "/1243.jpg";
		String afterConvertPath = "C:\\Users\\ZHB\\Videos\\12345678";
		
		//VideoUtil.screenCutByLinux(filePath,afterConvertPath,2);
		//VideoUtil.screenCut(filePath,afterConvertPath);
		//JavacvUtil.transferCut(filePath,afterConvertPath);
		
		//JavacvUtil.transferCut2(filePath, afterConvertPath);
		
		boolean success = false;
		File file = new File(afterConvertPath);
		if (file.isFile()) {
			success = true;
			//file.delete();
		}
		System.out.println(success);
		
		/*String afterConvertPath = realPath + "/123.flv";
		VideoUtil.convertVideo(filePath, afterConvertPath);*/
		
		/*if (VideoUtil.checkVideoContentType(videoType)) {
			VideoUtil.makeScreenCut(filePath,realPath + "/1243.jpg",1);
		}else{
			String afterConvertPath = realPath + "123.avi";
			VideoUtil.convertVideo(filePath, afterConvertPath);
			VideoUtil.makeScreenCut(afterConvertPath,realPath + "/1243.jpg",1);
		}*/
		
		return "htgl.uploadVideo.index";
	}

	/* downloadVideo */
	@RequestMapping("/downloadVideo")
	public void downloadVideo(HttpServletRequest request, HttpServletResponse response, String id) throws IOException {
		String ctxPath = request.getContextPath();
		if (StringUtils.isBlank(id)) {
			try {
				request.setAttribute(Constants.REQUEST_ERROR, "id 不能为空");
				request.getRequestDispatcher(ctxPath + "/htgl/errorController/toError").forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
			return;
		}
		// 查询
		List<ForeverCriteria> conditions = new ArrayList<ForeverCriteria>();
		conditions.add(ForeverCriteria.eq("id", id));
		List<FileInfoData> fileInfoDatas = foreverManager.getFileInfo(conditions);

		if (null == fileInfoDatas || fileInfoDatas.size() == 0) {
			return;
		}
		FileInfoData fileInfo = fileInfoDatas.get(0);

		String filePath = fileInfo.getFilePath() + File.separator;
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
		filePath += fileInfo.getFileName();
		DownloadUtil.processBeforeDownload(request, response, fileInfo.getFileType(), fileInfo.getFileName());
		FileInputStream fis = null;
		BufferedInputStream bi = null;
		ServletOutputStream sos = null;
		BufferedOutputStream bo = null;
		File image = new File(filePath);
		try {
			fis = new FileInputStream(image);
			bi = new BufferedInputStream(fis);
			sos = response.getOutputStream();
			bo = new BufferedOutputStream(sos);
			int lenght;
			byte[] buf = new byte[8192];
			while ((lenght = bi.read(buf, 0, buf.length)) != -1) {
				bo.write(buf, 0, lenght);
			}
			bo.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bo) {
				try {
					bo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != sos) {
				try {
					sos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != bi) {
				try {
					bi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*---------------------------------- excel -----------------------------------------------------------------*/

	@RequestMapping(value = "/toUploadExcel", method = RequestMethod.GET)
	public String toUoloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "htgl.excel.index";
	}

	@RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
	public String uploadExcel(HttpServletRequest request, HttpServletResponse response)
			throws IOException, InvalidFormatException {

		StringBuilder keyWordsBuilder = new StringBuilder();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得文件：
		MultipartFile multipartFile = multipartRequest.getFile("file");
		Workbook wb = WorkbookFactory.create(multipartFile.getInputStream());
		int sheetCount = wb.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			Sheet sheet = wb.getSheetAt(i);
			if (null == sheet) {
				continue;
			}
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext()) {
				Row row = rows.next();
				Iterator<Cell> cells = row.cellIterator();
				while (cells.hasNext()) {
					Cell cell = cells.next();
					if (null == cell) {
						continue;
					}
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String content = cell.getStringCellValue();
					if (StringUtils.isNotBlank(content)) {
						keyWordsBuilder.append(content + "  ");
					}
				}
			}
		}

		request.setAttribute("content", keyWordsBuilder);

		return "htgl.excel.index";
	}

	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 写成execl并导出
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建sheet页
		HSSFSheet sheet = wb.createSheet();
		// 写入文件头
		ExcelUtil.createExcelHead(wb, sheet);
		ExcelUtil.addMark(wb, sheet, 10);
		OutputStream fos = null;
		try {
			fos = response.getOutputStream();

			response.setContentType("application/force-download");
			String agentName = request.getHeader("User-Agent").toLowerCase();
	        String fileName = null;
	        if (-1 != agentName.indexOf("firefox")) {
	            fileName = new String("zhb-forever-test.xls".getBytes("UTF-8"),"iso-8859-1");
	        }else {
	            fileName = URLEncoder.encode("zhb-forever-test.xls", "UTF-8");
	        }
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

			wb.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fos) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws IOException {

		// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
		
		//VideoUtil.transferCut("C:\\Users\\ZHB\\Videos\\Wildlife.wmv","C:\\Users\\ZHB\\Videos\\1234.flv");
		//VideoUtil.screenCut("C:\\Users\\ZHB\\Videos\\Wildlife.wmv","C:\\Users\\ZHB\\Videos\\1234.jpg");
	    //String filePath = "F:\\log\\upload\\zhb_forever\\video\\44.mp4";
	    //String afterPath = "F:\\log\\upload\\zhb_forever\\video\\1234.jpg";
		
		String filePath = "C:\\Users\\ZHB\\Videos\\w3school.gif";
		String afterCutPath = "C:\\Users\\ZHB\\Videos\\1234.jpg";
		String afterConvertPath = "C:\\Users\\ZHB\\Videos\\1234.flv";
	   
		/*String file = "C:\\Users\\ZHB\\Videos\\ffmpeg.exe";
		Path path = Paths.get(file);
        String contentType = Files.probeContentType(path);
        System.out.println(contentType);*/
	    //FFmpegEXEUtil.makeScreenCut(filePath,afterPath,2);
	    
	    //JavacvUtil.screenCut(filePath, afterCutPath);
	    //JavacvUtil.transferCut2(filePath, afterConvertPath);
		
		/*Collection<Object> collections = MimeUtil.getMimeTypes(filePath);
		if (null != collections) {
			for (Object string : collections) {
				System.out.println(string);
			}
		}*/
		
		String[] teStrings = {"12","23","34"};
		List<String> list = Arrays.asList(teStrings);
		System.out.println(list.size());
		for (String string : list) {
			System.out.println(string);
		}
	}

}
