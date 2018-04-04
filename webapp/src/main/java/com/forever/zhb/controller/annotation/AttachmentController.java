package com.forever.zhb.controller.annotation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

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

	/*---------------------------------------------------------------------------------------------------*/

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

	public static void main(String[] args) {

		File source = new File("C:\\Users\\ZHB\\Videos\\Wildlife.wmv");
		Encoder encoder = new Encoder();
		FileChannel fc = null;
		String size = "";
		try {
			MultimediaInfo m = encoder.getInfo(source);
			long ls = m.getDuration();
			System.out.println("此视频时长为:" + ls / 60000 + "分" + (ls) / 1000 + "秒！");
			// 视频帧宽高
			System.out.println("此视频高度为:" + m.getVideo().getSize().getHeight());
			System.out.println("此视频宽度为:" + m.getVideo().getSize().getWidth());
			System.out.println("此视频格式为:" + m.getFormat());

			FileInputStream fis = new FileInputStream(source);
			fc = fis.getChannel();

			BigDecimal fileSize = new BigDecimal(fc.size());

			size = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) + "MB";

			System.out.println("此视频大小为" + size);

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
	}

}
