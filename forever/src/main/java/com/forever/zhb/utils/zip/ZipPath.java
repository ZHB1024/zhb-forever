package com.forever.zhb.utils.zip;

import java.io.File;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.lang3.StringUtils;

public class ZipPath {
	
	private final String path;

	ZipPath() {
		this("");
	}

	ZipPath(String path) {
		this.path = StringUtils.removeStart(StringUtils.removeEnd(path, "/"), "/");
	}

	ZipPath(ZipPath parent, File file) {
		String prefix = parent.path.equals("") ? "" : parent.path + "/";
		this.path = prefix + file.getName();
	}

	public ZipPath with(File file) {
		return new ZipPath(this, file);
	}

	public ZipArchiveEntry asZipEntry() {
		return new ZipArchiveEntry(this.path);
	}

	public ZipArchiveEntry asDirectoryZipEntry() {
		return new ZipArchiveEntry(this.path + "/");
	}

	public String getPath() {
		return this.path;
	}

}
