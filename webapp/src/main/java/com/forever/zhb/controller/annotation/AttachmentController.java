package com.forever.zhb.controller.annotation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.forever.zhb.Constants;
import com.forever.zhb.basic.BasicController;
import com.forever.zhb.criteria.ForeverCriteria;
import com.forever.zhb.criteria.ForeverCriteriaUtil;
import com.forever.zhb.criteria.ForeverPage;
import com.forever.zhb.dic.DeleteFlagEnum;
import com.forever.zhb.dic.FileTypeEnum;
import com.forever.zhb.page.Page;
import com.forever.zhb.pojo.FileInfoData;
import com.forever.zhb.service.IForeverManager;
import com.forever.zhb.utils.DownloadUtil;
import com.forever.zhb.utils.ImageUtils;
import com.forever.zhb.utils.PropertyUtil;
import com.forever.zhb.utils.attachment.ExcelUtil;
import com.forever.zhb.utils.attachment.video.FFmpegEXEUtil;
import com.forever.zhb.utils.attachment.video.JavacvUtil;

@Controller
@RequestMapping("/htgl/attachmentController")
public class AttachmentController extends BasicController {

	private Logger logger = LoggerFactory.getLogger(AttachmentController.class);

	@Resource(name = "foreverManager")
	private IForeverManager foreverManager;

	@RequestMapping("/toUpload")
	public String toUpload(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("active5", true);
		return "htgl.upload.index";
	}

	/* toDownload */
	@RequestMapping("/toDownload")
	public String toDownload(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("active6", true);
		String start = request.getParameter("start");
		if (StringUtils.isBlank(start)) {
			start = "0";
		}
		// 查询
		List<ForeverCriteria> conditions = new ArrayList<ForeverCriteria>();
		conditions.add(ForeverCriteria.eq("type", FileTypeEnum.IMAGE.getIndex()));
		ForeverCriteriaUtil<FileInfoData> util = ForeverCriteriaUtil.getInstance(FileInfoData.class);
		util.setPageProperties(null, Integer.parseInt(start), Constants.PAGE_SIZE, conditions, null);
		Page filePage = util.getPage(foreverManager.getFileInfoPage(conditions));
		request.setAttribute("page", filePage);
		return "htgl.download.index";
	}

	@RequestMapping("/upload")
	public void upload(HttpServletRequest request, HttpServletResponse response) {
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
		MultipartFile license = multipartRequest.getFile("firstFile");
		Long fileSize = license.getSize();
		String fileType = license.getContentType();
		String fileName = "";
		if (license.getSize() > Constants.IMAGE_MAX_SIZE) {
			/*
			 * try { request.getRequestDispatcher(ctxPath +
			 * "/htgl/errorController/toError").forward(request, response); }
			 * catch (ServletException | IOException e2) { e2.printStackTrace();
			 * } return;
			 */
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
			foreverManager.addFileInfoData(fileInfoData);
			// 查询
			/*
			 * List<ForeverCriteria> conditions = new
			 * ArrayList<ForeverCriteria>();
			 * conditions.add(ForeverCriteria.eq("fileName", ""));
			 * List<FileInfoData> fileInfoDatas =
			 * foreverManager.getFileInfoDataByIdOrName(conditions);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Constants.REQUEST_ERROR, "上传出错");
			try {
				request.getRequestDispatcher(ctxPath + "/htgl/errorController/toError").forward(request, response);
			} catch (ServletException | IOException e2) {
				e2.printStackTrace();
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
		try {
			response.sendRedirect(ctxPath + "/htgl/fileDownloadController/toDownload");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	/* download */
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response, String id) {
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
		response.setContentType(fileInfo.getFileType());
		FileInputStream fis = null;
		ServletOutputStream sos = null;
		File image = new File(filePath);
		try {
			fis = new FileInputStream(image);
			sos = response.getOutputStream();
			/*
			 * int lenght; byte[] buf = new byte[1024]; while((lenght =
			 * fis.read(buf, 0, 1024)) != -1){ sos.write(buf,0,lenght); }
			 * sos.flush();
			 */
			ImageUtils.pressText(fis, sos, 0.3f, 3, 3, new String[] { "zhb_forever" });
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

	/*-----------------------------------------------video----------------------------------------------------*/

	@RequestMapping("/toUploadVideo")
	public String toUploadVideo(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("active5", true);
		return "htgl.uploadVideo.index";
	}

	/* toDownload */
	@RequestMapping("/toDownloadVideo")
	public String toDownloadVideo(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("active6", true);
		String start = request.getParameter("start");
		if (StringUtils.isBlank(start)) {
			start = "0";
		}
		// 查询
		List<ForeverCriteria> conditions = new ArrayList<ForeverCriteria>();
		conditions.add(ForeverCriteria.eq("type", FileTypeEnum.VIDEO.getIndex()));
		// conditions.add(ForeverCriteria.eq("fileType", "audio/ogg"));
		// conditions.add(ForeverCriteria.eq("fileType", "audio/ogg"));
		ForeverCriteriaUtil<FileInfoData> util = ForeverCriteriaUtil.getInstance(FileInfoData.class);
		util.setPageProperties(null, Integer.parseInt(start), Constants.PAGE_SIZE, conditions, null);
		ForeverPage<FileInfoData> files = foreverManager.getFileInfoPage(conditions);
		if (null != files) {

		}
		Page filePage = util.getPage(foreverManager.getFileInfoPage(conditions));
		request.setAttribute("page", filePage);
		return "htgl.downloadVideo.index";
	}

	@RequestMapping("/uploadVideo")
	public void uploadVideo(HttpServletRequest request, HttpServletResponse response) {
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
		MultipartFile license = multipartRequest.getFile("firstFile");
		Long fileSize = license.getSize();
		String fileType = license.getContentType();
		String fileName = "";
		if (license.getSize() > Constants.VIDEO_MAX_SIZE) {
			/*
			 * try { request.getRequestDispatcher(ctxPath +
			 * "/htgl/errorController/toError").forward(request, response); }
			 * catch (ServletException | IOException e2) { e2.printStackTrace();
			 * } return;
			 */
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
			foreverManager.addFileInfoData(fileInfoData);
			// 查询
			/*
			 * List<ForeverCriteria> conditions = new
			 * ArrayList<ForeverCriteria>();
			 * conditions.add(ForeverCriteria.eq("fileName", ""));
			 * List<FileInfoData> fileInfoDatas =
			 * foreverManager.getFileInfoDataByIdOrName(conditions);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Constants.REQUEST_ERROR, "上传出错");
			try {
				request.getRequestDispatcher(ctxPath + "/htgl/errorController/toError").forward(request, response);
			} catch (ServletException | IOException e2) {
				e2.printStackTrace();
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
		try {
			response.sendRedirect(ctxPath + "/htgl/attachmentController/toDownloadVideo");
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
		
		JavacvUtil.transferCut2(filePath, afterConvertPath);
		
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
		
		String filePath = "C:\\Users\\ZHB\\Videos\\音乐之声-njluyou.rm";
		String afterCutPath = "C:\\Users\\ZHB\\Videos\\1234.jpg";
		String afterConvertPath = "C:\\Users\\ZHB\\Videos\\1234.flv";
	   
		String file = "C:\\Users\\ZHB\\Videos\\ffmpeg.exe";
		Path path = Paths.get(file);
        String contentType = Files.probeContentType(path);
        System.out.println(contentType);
	    //FFmpegEXEUtil.makeScreenCut(filePath,afterPath,2);
	    
	    //JavacvUtil.screenCut(filePath, afterCutPath);
	    //JavacvUtil.transferCut2(filePath, afterConvertPath);
		
	}

}
