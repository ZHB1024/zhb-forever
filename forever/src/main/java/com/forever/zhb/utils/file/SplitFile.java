package com.forever.zhb.utils.file;

import com.google.common.base.Preconditions;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

public class SplitFile {

    private File file;
    private int partSize;

    public SplitFile(File file) {
        this.partSize = 52428800;

        Preconditions.checkArgument(file.exists());
        this.file = file;
    }

    public SplitFile(File file, int partSize) {
        this(file);
        Preconditions.checkArgument(partSize <= 2147483647);
        this.partSize = partSize;
    }

    public void split(SplitPartHandler handler) {
        InputStream is = null;
        try {
            is = FileUtils.openInputStream(this.file);
            while (true) {
                byte[] buffer = new byte[this.partSize];
                int len = IOUtils.read(is, buffer, 0, this.partSize);
                if (len <= 0)
                    break;
                if (len < this.partSize) {
                    handler.handleBytes(Arrays.copyOf(buffer, len));
                    break;
                }
                handler.handleBytes(buffer);
            }

        } catch (IOException e) {
        } finally {
            if (is != null)
                IOUtils.closeQuietly(is);
        }
    }

    public void setPartSize(int partSize) {
        this.partSize = partSize;
    }

    public int getPartSize() {
        return this.partSize;
    }

    public static abstract interface SplitPartHandler {
        public abstract void handleBytes(byte[] paramArrayOfByte);
    }

}
