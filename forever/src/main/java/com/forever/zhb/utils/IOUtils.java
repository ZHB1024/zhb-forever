package com.forever.zhb.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
    public static void closeQuietly(InputStream input) {
        try {
            if (input != null)
                input.close();
        } catch (IOException ioe) {
        }
    }

    public static void closeQuietly(OutputStream output) {
        try {
            if (output != null)
                output.close();
        } catch (IOException ioe) {
        }
    }

}
