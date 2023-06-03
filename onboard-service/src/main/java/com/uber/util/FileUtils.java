package com.uber.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class FileUtils {

    private static final String TMP_FOLDER = "/tmp/uploads/";

    public static File getFileFromBlob(Blob blob, String fileName) {

        File file = new File(TMP_FOLDER + fileName);
        try {
            InputStream in = blob.getBinaryStream();
            OutputStream out = new FileOutputStream(file);
            IOUtils.copy(in, out);
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;

    }
}
