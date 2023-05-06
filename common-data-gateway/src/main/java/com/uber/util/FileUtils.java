package com.uber.util;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public class FileUtils {
    public static Blob getBlob(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            return new SerialBlob(fileInputStream.readAllBytes());
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (
                SerialException e) {
            throw new RuntimeException(e);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
