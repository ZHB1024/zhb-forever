package com.forever.zhb.utils.zip;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipBuilder {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private int level = 9;
	private boolean excludeRootDir = false;
	private OutputStream destinationStream;
	private File destinationFile;
	private File zip4jDestinationFile;
	private String encoding = "GBK";
	private Zip64Mode zip64Mode;
	private static final int SIZE_UNKNOWN = -1;
	private Multimap<String, File> files;
	private Multimap<String, ZipBuilder.SizedInputStream> inputStreams;
	private String password;
	private ZipBuilder.EncryptionMethod encryptionMethod;
	private ZipBuilder.AesStrength aesStrength;
	
	public ZipBuilder() {
		this.zip64Mode = Zip64Mode.AsNeeded;
		this.files = HashMultimap.create();
		this.inputStreams = HashMultimap.create();
		this.encryptionMethod = ZipBuilder.EncryptionMethod.STANDARD;
		this.aesStrength = ZipBuilder.AesStrength._256;
	}

	public ZipBuilder(File destFile) {
		this.zip64Mode = Zip64Mode.AsNeeded;
		this.files = HashMultimap.create();
		this.inputStreams = HashMultimap.create();
		this.encryptionMethod = ZipBuilder.EncryptionMethod.STANDARD;
		this.aesStrength = ZipBuilder.AesStrength._256;
		this.destinationFile = destFile;
	}

	public ZipBuilder(OutputStream destinationStream) {
		this.zip64Mode = Zip64Mode.AsNeeded;
		this.files = HashMultimap.create();
		this.inputStreams = HashMultimap.create();
		this.encryptionMethod = ZipBuilder.EncryptionMethod.STANDARD;
		this.aesStrength = ZipBuilder.AesStrength._256;
		this.destinationStream = destinationStream;
	}

	public ZipBuilder add(File sourceToZip) {
		this.files.put("", sourceToZip);
		return this;
	}

	public ZipBuilder add(String directoryNameInsideZip, File sourceToZip) {
		this.files.put(directoryNameInsideZip, sourceToZip);
		return this;
	}

	public ZipBuilder add(String fileNameInsideZip, InputStream input) {
		this.inputStreams.put(fileNameInsideZip, new ZipBuilder.SizedInputStream(input, -1L));
		return this;
	}

	public ZipBuilder add(String fileNameInsideZip, byte[] bytes) {
		this.inputStreams.put(fileNameInsideZip,
				new ZipBuilder.SizedInputStream(new ByteArrayInputStream(bytes), (long) bytes.length));
		return this;
	}

	public void build() {
		Preconditions.checkArgument(this.destinationFile != null || this.destinationStream != null, "未指定目标压缩文件！");
		if (StringUtils.isNotBlank(this.password)) {
			Preconditions.checkArgument(this.encryptionMethod != null);
			if (this.encryptionMethod == ZipBuilder.EncryptionMethod.AES) {
				Preconditions.checkArgument(this.aesStrength != null);
			}
		}

		boolean arg13 = false;

		try {
			arg13 = true;
			if (StringUtils.isBlank(this.password)) {
				this.useCommonsCompress();
				arg13 = false;
			} else {
				this.useZip4j();
				arg13 = false;
			}
		} catch (Throwable arg14) {
			throw new RuntimeException("生成压缩文件异常", arg14);
		} finally {
			if (arg13) {
				Iterator arg6 = this.inputStreams.keySet().iterator();

				while (arg6.hasNext()) {
					String key1 = (String) arg6.next();
					Collection is1 = this.inputStreams.get(key1);
					Iterator arg9 = is1.iterator();

					while (arg9.hasNext()) {
						ZipBuilder.SizedInputStream srcStream1 = (ZipBuilder.SizedInputStream) arg9.next();
						IOUtils.closeQuietly(srcStream1.inputStream);
					}
				}

				if (this.destinationStream != null) {
					IOUtils.closeQuietly(this.destinationStream);
				}

			}
		}

		Iterator t = this.inputStreams.keySet().iterator();

		while (t.hasNext()) {
			String key = (String) t.next();
			Collection is = this.inputStreams.get(key);
			Iterator arg3 = is.iterator();

			while (arg3.hasNext()) {
				ZipBuilder.SizedInputStream srcStream = (ZipBuilder.SizedInputStream) arg3.next();
				IOUtils.closeQuietly(srcStream.inputStream);
			}
		}

		if (this.destinationStream != null) {
			IOUtils.closeQuietly(this.destinationStream);
		}

	}

	public void buildForEncryptBigFile() {
		Preconditions.checkArgument(this.destinationFile != null || this.destinationStream != null, "未指定目标压缩文件！");
		boolean arg13 = false;

		try {
			arg13 = true;
			this.userNewZip4j();
			arg13 = false;
		} catch (Throwable arg14) {
			throw new RuntimeException("生成压缩文件异常", arg14);
		} finally {
			if (arg13) {
				Iterator arg6 = this.inputStreams.keySet().iterator();

				while (arg6.hasNext()) {
					String key1 = (String) arg6.next();
					Collection is1 = this.inputStreams.get(key1);
					Iterator arg9 = is1.iterator();

					while (arg9.hasNext()) {
						ZipBuilder.SizedInputStream srcStream1 = (ZipBuilder.SizedInputStream) arg9.next();
						IOUtils.closeQuietly(srcStream1.inputStream);
					}
				}

				if (this.destinationStream != null) {
					IOUtils.closeQuietly(this.destinationStream);
				}

			}
		}

		Iterator t = this.inputStreams.keySet().iterator();

		while (t.hasNext()) {
			String key = (String) t.next();
			Collection is = this.inputStreams.get(key);
			Iterator arg3 = is.iterator();

			while (arg3.hasNext()) {
				ZipBuilder.SizedInputStream srcStream = (ZipBuilder.SizedInputStream) arg3.next();
				IOUtils.closeQuietly(srcStream.inputStream);
			}
		}

		if (this.destinationStream != null) {
			IOUtils.closeQuietly(this.destinationStream);
		}

	}

	private void useCommonsCompress() {
		ZipArchiveOutputStream zip = null;

		try {
			if (this.destinationFile != null) {
				zip = new ZipArchiveOutputStream(this.destinationFile);
			} else if (this.destinationStream != null) {
				zip = new ZipArchiveOutputStream(new BufferedOutputStream(this.destinationStream));
			}

			zip.setLevel(this.level);
			zip.setEncoding(this.encoding);
			zip.setUseZip64(this.zip64Mode);
			Iterator e = this.files.keySet().iterator();

			String fileNameInsideZip;
			Collection srcInputStreams;
			Iterator arg4;
			while (e.hasNext()) {
				fileNameInsideZip = (String) e.next();
				srcInputStreams = this.files.get(fileNameInsideZip);
				arg4 = srcInputStreams.iterator();

				while (arg4.hasNext()) {
					File srcInputStream = (File) arg4.next();
					this.addFileToZip(new ZipPath(fileNameInsideZip), srcInputStream, zip, this.excludeRootDir);
				}
			}

			e = this.inputStreams.keySet().iterator();

			while (e.hasNext()) {
				fileNameInsideZip = (String) e.next();
				srcInputStreams = this.inputStreams.get(fileNameInsideZip);
				arg4 = srcInputStreams.iterator();

				while (arg4.hasNext()) {
					ZipBuilder.SizedInputStream srcInputStream1 = (ZipBuilder.SizedInputStream) arg4.next();
					this.addInputStreamToZip(new ZipPath(fileNameInsideZip), srcInputStream1, zip);
				}
			}

			zip.finish();
			zip.flush();
		} catch (Throwable arg9) {
			throw new RuntimeException("压缩文件异常", arg9);
		} finally {
			IOUtils.closeQuietly(zip);
		}

	}

	private void addFileToZip(ZipPath path, File srcFile, ZipArchiveOutputStream zip, boolean excludeRootDir)
			throws IOException {
		if (srcFile.isDirectory()) {
			ZipPath entry = null;
			if (excludeRootDir) {
				entry = path;
			} else {
				entry = path.with(srcFile);
				zip.putArchiveEntry(entry.asDirectoryZipEntry());
				zip.closeArchiveEntry();
			}

			File[] inputStream = srcFile.listFiles();
			int arg6 = inputStream.length;

			for (int arg7 = 0; arg7 < arg6; ++arg7) {
				File file = inputStream[arg7];
				if (this.destinationFile == null || !this.destinationFile.equals(file)) {
					this.addFileToZip(entry, file, zip, false);
				}
			}
		} else {
			ZipArchiveEntry arg12 = path.with(srcFile).asZipEntry();
			arg12.setSize(srcFile.length());
			zip.putArchiveEntry(arg12);
			FileInputStream arg13 = null;

			try {
				arg13 = new FileInputStream(srcFile);
				IOUtils.copy(arg13, zip);
				zip.closeArchiveEntry();
			} finally {
				IOUtils.closeQuietly(arg13);
			}
		}

	}

	private void addInputStreamToZip(ZipPath path, ZipBuilder.SizedInputStream srcInputStream,
			ZipArchiveOutputStream zip) throws IOException {
		ZipArchiveEntry entry = path.asZipEntry();
		if (srcInputStream.size != -1L) {
			entry.setSize(srcInputStream.size);
		}

		zip.putArchiveEntry(entry);

		try {
			IOUtils.copy(srcInputStream.inputStream, zip);
			zip.closeArchiveEntry();
		} finally {
			IOUtils.closeQuietly(srcInputStream.inputStream);
		}

	}

	private void userNewZip4j() throws Exception {
		this.initResultFile();
		this.useCommonsCompress();
		ZipOutputStream zip = new ZipOutputStream(this.getZip4jOS(), this.getDefaultZipModel());

		try {
			ZipParameters params = this.getUnCompressParams();
			params.setFileNameInZip(this.destinationFile.getName());
			zip.putNextEntry(this.destinationFile, params);
			FileInputStream inputStream = null;

			try {
				inputStream = new FileInputStream(this.destinationFile);
				IOUtils.copy(inputStream, zip);
				zip.closeEntry();
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		} finally {
			zip.finish();
			zip.close();
			FileUtils.forceDelete(this.destinationFile);
		}

	}

	private void initResultFile() {
		Preconditions.checkArgument(StringUtils.isNotBlank(this.password));
		Preconditions.checkArgument(this.encryptionMethod != null);
		if (this.encryptionMethod == ZipBuilder.EncryptionMethod.AES) {
			Preconditions.checkArgument(this.aesStrength != null);
		}

		String path = this.destinationFile.getPath();
		String fName = this.destinationFile.getName();
		this.zip4jDestinationFile = new File(path);
		this.destinationFile = new File(this.getPressFileName(path, fName));
	}

	private OutputStream getZip4jOS() throws Exception {
		if (this.destinationStream == null) {
			this.destinationStream = new FileOutputStream(this.zip4jDestinationFile);
		}

		return this.destinationStream;
	}

	private ZipModel getDefaultZipModel() {
		ZipModel zipModel = new ZipModel();
		zipModel.setFileNameCharset(this.encoding);
		return zipModel;
	}

	private String getPressFileName(String path, String fName) {
		return path.replace(fName, "") + "default_" + fName;
	}

	private ZipParameters getUnCompressParams() {
		ZipParameters params = new ZipParameters();
		params.setCompressionMethod(0);
		params.setCompressionLevel(0);
		params.setEncryptFiles(true);
		params.setEncryptionMethod(0);
		params.setPassword(this.password);
		return params;
	}

	private void useZip4j() throws Exception {
		if (this.destinationStream == null) {
			this.destinationStream = new FileOutputStream(this.destinationFile);
		}

		ZipModel zipModel = new ZipModel();
		zipModel.setFileNameCharset(this.encoding);
		ZipOutputStream zip = new ZipOutputStream(this.destinationStream, zipModel);

		try {
			Iterator arg2 = this.files.keySet().iterator();

			String fileNameInsideZip;
			Collection srcInputStreams;
			while (arg2.hasNext()) {
				fileNameInsideZip = (String) arg2.next();
				srcInputStreams = this.files.get(fileNameInsideZip);
				Iterator params = srcInputStreams.iterator();

				while (params.hasNext()) {
					File sourceFileToZip = (File) params.next();
					this.addFileToZip4j(new ZipPath(fileNameInsideZip), sourceFileToZip, zip, !this.excludeRootDir);
				}
			}

			arg2 = this.inputStreams.keySet().iterator();

			while (arg2.hasNext()) {
				fileNameInsideZip = (String) arg2.next();
				srcInputStreams = this.inputStreams.get(fileNameInsideZip);
				ZipParameters params1 = this.getDefaultZipParameters();
				params1.setFileNameInZip((new ZipPath(fileNameInsideZip)).getPath());
				params1.setSourceExternalStream(true);
				Iterator sourceFileToZip1 = srcInputStreams.iterator();

				while (sourceFileToZip1.hasNext()) {
					ZipBuilder.SizedInputStream srcInputStream = (ZipBuilder.SizedInputStream) sourceFileToZip1.next();
					zip.putNextEntry((File) null, params1);
					IOUtils.copy(srcInputStream.inputStream, zip);
					zip.closeEntry();
				}
			}
		} finally {
			zip.finish();
			zip.close();
		}

	}

	private void addFileToZip4j(ZipPath path, File srcFile, ZipOutputStream zip, boolean includeRootDir)
			throws Exception {
		ZipParameters params = this.getDefaultZipParameters();
		params.setIncludeRootFolder(includeRootDir);
		params.setRootFolderInZip(path.getPath());
		if (srcFile.isDirectory()) {
			ZipPath inputStream = null;
			if (includeRootDir) {
				inputStream = path.with(srcFile);
				zip.putNextEntry(srcFile, params);
				zip.closeEntry();
			} else {
				inputStream = path;
			}

			File[] arg6 = srcFile.listFiles();
			int arg7 = arg6.length;

			for (int arg8 = 0; arg8 < arg7; ++arg8) {
				File file = arg6[arg8];
				if (this.destinationFile == null || !this.destinationFile.equals(file)) {
					this.addFileToZip4j(inputStream, file, zip, true);
				}
			}
		} else {
			zip.putNextEntry(srcFile, params);
			FileInputStream arg13 = null;

			try {
				arg13 = new FileInputStream(srcFile);
				IOUtils.copy(arg13, zip);
				zip.closeEntry();
			} finally {
				IOUtils.closeQuietly(arg13);
			}
		}

	}

	private ZipParameters getDefaultZipParameters() {
		ZipParameters params = new ZipParameters();
		params.setCompressionMethod(8);
		params.setCompressionLevel(this.level);
		params.setEncryptFiles(true);
		params.setEncryptionMethod(this.encryptionMethod.zip4jEncryMethod);
		if (this.encryptionMethod == ZipBuilder.EncryptionMethod.AES) {
			params.setAesKeyStrength(this.aesStrength.strength);
		}

		params.setPassword(this.password);
		return params;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public OutputStream getDestinationStream() {
		return this.destinationStream;
	}

	public void setDestinationStream(OutputStream destinationStream) {
		this.destinationStream = destinationStream;
	}

	public boolean isExcludeRootDir() {
		return this.excludeRootDir;
	}

	public void setExcludeRootDir(boolean excludeRootDir) {
		this.excludeRootDir = excludeRootDir;
	}

	public Multimap<String, File> getFiles() {
		return this.files;
	}

	public void setFiles(Multimap<String, File> files) {
		this.files = files;
	}

	public String getEncoding() {
		return this.encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setZip64Mode(Zip64Mode zip64Mode) {
		this.zip64Mode = zip64Mode;
	}

	public Zip64Mode getZip64Mode() {
		return this.zip64Mode;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ZipBuilder.EncryptionMethod getEncryptionMethod() {
		return this.encryptionMethod;
	}

	public void setEncryptionMethod(ZipBuilder.EncryptionMethod encryptionMethod) {
		this.encryptionMethod = encryptionMethod;
	}

	public ZipBuilder.AesStrength getAesStrength() {
		return this.aesStrength;
	}

	public void setAesStrength(ZipBuilder.AesStrength aesStrength) {
		this.aesStrength = aesStrength;
	}

	private class SizedInputStream {
		long size = -1L;
		InputStream inputStream;

		public SizedInputStream(InputStream inputStream, long size) {
			this.size = size;
			this.inputStream = inputStream;
		}
	}

	public static enum AesStrength {
		_128(1), _192(2), _256(3);

		int strength;

		private AesStrength(int strength) {
			this.strength = strength;
		}
	}

	public static enum EncryptionMethod {
		STANDARD(0), AES(99);

		int zip4jEncryMethod;

		private EncryptionMethod(int value) {
			this.zip4jEncryMethod = value;
		}
	}

}
