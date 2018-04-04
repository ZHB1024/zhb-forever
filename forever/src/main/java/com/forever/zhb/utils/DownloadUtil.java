package com.forever.zhb.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class DownloadUtil {

	private String name;
	private String in;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private static final int DEFAULT_BUFFER_SIZE = 10240;
	private static final long DEFAULT_EXPIRE_TIME = 604800000L;
	private static final String MULTIPART_BOUNDARY = "MULTIPART_BYTERANGES";

	public void down() throws IOException {
		File file = new File(this.in);
		this.response.reset();
		this.response.setHeader("Server", "chsi");

		if (!(file.exists())) {
			this.response.sendError(404);
			return;
		}

		String fileName = file.getName();
		long length = file.length();
		long lastModified = file.lastModified();
		String eTag = new StringBuilder().append(fileName).append("_").append(length).append("_").append(lastModified)
				.toString();
		long expires = System.currentTimeMillis() + 604800000L;

		String ifNoneMatch = this.request.getHeader("If-None-Match");
		if ((ifNoneMatch != null) && (matches(ifNoneMatch, eTag))) {
			this.response.setStatus(304);
			this.response.setHeader("ETag", eTag);
			this.response.setDateHeader("Expires", expires);
			return;
		}

		long ifModifiedSince = this.request.getDateHeader("If-Modified-Since");
		if ((ifNoneMatch == null) && (ifModifiedSince != -1L) && (ifModifiedSince + 1000L > lastModified)) {
			this.response.setStatus(304);
			this.response.setHeader("ETag", eTag);
			this.response.setDateHeader("Expires", expires);
			return;
		}

		String ifMatch = this.request.getHeader("If-Match");
		if ((ifMatch != null) && (!(matches(ifMatch, eTag)))) {
			this.response.sendError(412);
			return;
		}

		long ifUnmodifiedSince = this.request.getDateHeader("If-Unmodified-Since");
		if ((ifUnmodifiedSince != -1L) && (ifUnmodifiedSince + 1000L <= lastModified)) {
			this.response.sendError(412);
			return;
		}

		Range full = new Range(0L, length - 1L, length);
		List<Range> ranges = new ArrayList();

		String range = this.request.getHeader("Range");
		if (range != null) {
			if (!(range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$"))) {
				this.response.setHeader("Content-Range",
						new StringBuilder().append("bytes */").append(length).toString());
				this.response.sendError(416);
				return;
			}

			String ifRange = this.request.getHeader("If-Range");
			if ((ifRange != null) && (!(ifRange.equals(eTag)))) {
				try {
					long ifRangeTime = this.request.getDateHeader("If-Range");
					if ((ifRangeTime != -1L) && (ifRangeTime + 1000L < lastModified))
						ranges.add(full);
				} catch (IllegalArgumentException ignore) {
					ranges.add(full);
				}

			}

			if (ranges.isEmpty()) {
				for (String part : range.substring(6).split(",")) {
					long start = sublong(part, 0, part.indexOf("-"));
					long end = sublong(part, part.indexOf("-") + 1, part.length());

					if (start == -1L) {
						start = length - end;
						end = length - 1L;
					} else if ((end == -1L) || (end > length - 1L)) {
						end = length - 1L;
					}

					if (start > end) {
						this.response.setHeader("Content-Range",
								new StringBuilder().append("bytes */").append(length).toString());
						this.response.sendError(416);
						return;
					}

					ranges.add(new Range(start, end, length));
				}

			}

		}

		String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
		boolean acceptsGzip = false;
		String disposition = "attachment";

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		if (contentType.startsWith("text")) {
			String acceptEncoding = this.request.getHeader("Accept-Encoding");
			acceptsGzip = (acceptEncoding != null) && (accepts(acceptEncoding, "gzip"));
			contentType = new StringBuilder().append(contentType).append(";charset=UTF-8").toString();
		} else if (contentType.startsWith("image")) {
			disposition = "inline";
		}

		this.response.reset();
		this.response.setBufferSize(10240);
		this.response.setHeader("Content-Disposition",
				new StringBuilder().append(disposition).append(";filename=\"")
						.append((null == this.name) ? fileName : getCodedFileName(this.request, this.name)).append("\"")
						.toString());

		this.response.setHeader("Accept-Ranges", "bytes");
		this.response.setHeader("ETag", eTag);
		this.response.setDateHeader("Last-Modified", lastModified);
		this.response.setDateHeader("Expires", expires);

		RandomAccessFile input = null;
		OutputStream output = null;
		try {
			input = new RandomAccessFile(file, "r");
			output = this.response.getOutputStream();

			if ((ranges.isEmpty()) || (ranges.get(0) == full)) {
				Range r = full;
				this.response.setContentType(contentType);
				this.response.setHeader("Content-Range", new StringBuilder().append("bytes ").append(r.start)
						.append("-").append(r.end).append("/").append(r.total).toString());

				if (acceptsGzip) {
					this.response.setHeader("Content-Encoding", "gzip");
					output = new GZIPOutputStream(output, 10240);
				} else {
					this.response.setHeader("Content-Length", String.valueOf(r.length));
				}

				copy(input, output, r.start, r.length);
			} else if (ranges.size() == 1) {
				Range r = (Range) ranges.get(0);
				this.response.setContentType(contentType);
				this.response.setHeader("Content-Range", new StringBuilder().append("bytes ").append(r.start)
						.append("-").append(r.end).append("/").append(r.total).toString());
				this.response.setHeader("Content-Length", String.valueOf(r.length));
				this.response.setStatus(206);

				copy(input, output, r.start, r.length);
			} else {
				this.response.setContentType("multipart/byteranges; boundary=MULTIPART_BYTERANGES");
				this.response.setStatus(206);

				ServletOutputStream sos = (ServletOutputStream) output;

				for (Range r : ranges) {
					sos.println();
					sos.println("--MULTIPART_BYTERANGES");
					sos.println(new StringBuilder().append("Content-Type: ").append(contentType).toString());
					sos.println(new StringBuilder().append("Content-Range: bytes ").append(r.start).append("-")
							.append(r.end).append("/").append(r.total).toString());

					copy(input, output, r.start, r.length);
				}

				sos.println();
				sos.println("--MULTIPART_BYTERANGES--");
			}
		} finally {
			output.flush();
			close(output);
			close(input);
			this.response.flushBuffer();
		}
	}

	private boolean accepts(String acceptHeader, String toAccept) {
		String[] acceptValues = acceptHeader.split("\\s*(,|;)\\s*");
		Arrays.sort(acceptValues);
		return ((Arrays.binarySearch(acceptValues, toAccept) > -1)
				|| (Arrays.binarySearch(acceptValues, toAccept.replaceAll("/.*$", "*")) > -1));
	}

	private boolean matches(String matchHeader, String toMatch) {
		String[] matchValues = matchHeader.split("\\s*,\\s*");
		Arrays.sort(matchValues);
		return ((Arrays.binarySearch(matchValues, toMatch) > -1) || (Arrays.binarySearch(matchValues, "*") > -1));
	}

	private long sublong(String value, int beginIndex, int endIndex) {
		String substring = value.substring(beginIndex, endIndex);
		return ((substring.length() > 0) ? Long.parseLong(substring) : -1L);
	}

	private void copy(RandomAccessFile input, OutputStream output, long start, long length) throws IOException {
		byte[] buffer = new byte[10240];

		if (input.length() == length) {
			while (true) {
				int read;
				if ((read = input.read(buffer)) <= 0)
					return;
				output.write(buffer, 0, read);
			}
		}

		input.seek(start);
		long toRead = length;
		int read;
		while (true) {
			if ((read = input.read(buffer)) <= 0)
				return;
			if ((toRead -= read) <= 0L)
				break;
			output.write(buffer, 0, read);
		}
		output.write(buffer, 0, (int) toRead + read);
	}

	private void close(Closeable resource) {
		if (resource == null)
			return;
		try {
			resource.close();
			resource = null;
		} catch (IOException ignore) {
		}
	}

	public void down(String in, HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.in = in;
		this.request = request;
		this.response = response;
		down();
	}

	public void down(String in, HttpServletRequest request, HttpServletResponse response, String name)
			throws IOException {
		this.in = in;
		this.request = request;
		this.response = response;
		this.name = name;
		down();
	}

	public void setIn(String in) {
		this.in = in;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public static void processBeforeDownload(HttpServletRequest request, HttpServletResponse response,
			String contentType, String fileName) throws IOException {
		if (StringUtils.isEmpty(contentType)) {
			contentType = "APPLICATION/OCTET-STREAM";
		}
		response.setContentType(String.format("%s;charset=utf-8", new Object[] { contentType }));
		response.setHeader("Content-Disposition",
				String.format("attachment;filename=\"%s\"", new Object[] { getCodedFileName(request, fileName) }));
	}

	public static String getCodedFileName(HttpServletRequest request, String fileName)
			throws UnsupportedEncodingException {
		String codedfilename = null;
		String agent = request.getHeader("USER-AGENT");
		if (isIEBrowser(agent)) {
			int lastDot = fileName.lastIndexOf(".");
			String prefix = (lastDot != -1) ? fileName.substring(0, lastDot) : fileName;
			String extension = (lastDot != -1) ? fileName.substring(lastDot) : "";
			String name = URLEncoder.encode(fileName, "UTF8");
			if (name.lastIndexOf("%0A") != -1) {
				name = name.substring(0, name.length() - 3);
			}
			int limit = 500 - extension.length();
			if (name.length() > limit) {
				name = URLEncoder.encode(prefix.substring(0, Math.min(prefix.length(), limit / 9)), "UTF-8");
				if (name.lastIndexOf("%0A") != -1) {
					name = name.substring(0, name.length() - 3);
				}
				name = new StringBuilder().append(name).append(URLEncoder.encode(extension, "UTF8")).toString();
			}
			name = name.replace("+", "%20");
			codedfilename = name;
		} else if ((null != agent) && (-1 != agent.indexOf("Mozilla"))) {
			if (-1 != agent.indexOf("Firefox")) {
				codedfilename = String.format("=?UTF-8?B?%s?=",
						new Object[] { new String(Base64.encodeBase64(fileName.getBytes("UTF-8"))) });
			} else {
				codedfilename = URLEncoder.encode(fileName, "UTF-8");
				codedfilename = codedfilename.replace("+", "%20");
			}
		} else {
			codedfilename = fileName;
		}
		return codedfilename;
	}

	private static boolean isIEBrowser(String agent) {
		boolean result = false;
		if (StringUtils.isNotBlank(agent)) {
			result = (-1 != agent.indexOf("MSIE")) || (-1 != agent.indexOf("Trident"));
		}
		return result;
	}

	protected class Range {
		long start;
		long end;
		long length;
		long total;

		public Range(long paramLong1, long paramLong2, long paramLong3) {
			this.start = paramLong1;
			this.end = paramLong2;
			this.length = (paramLong2 - paramLong1 + 1L);
			this.total = paramLong3;
		}
	}

}
