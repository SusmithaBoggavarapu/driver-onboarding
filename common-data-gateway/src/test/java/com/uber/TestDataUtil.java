package com.uber;

import com.uber.entity.document.Document;
import com.uber.entity.user.Address;
import com.uber.entity.user.Driver;
import com.uber.util.FileUtils;

import java.io.File;
import java.sql.Blob;

public class TestDataUtil {
    private static String FILE_NAME = "src/test/resources/document/vehicle_insurance.pdf";
    public static Driver getDriver(){
        return Driver.builder().address(Address.builder().city("city").pincode(502300).state("telangana").build()).email("test@walmart.com").firstName("test").lastName("walmart").mobile("12345").build();
    }

    public static Document getDocument(){
        Blob blob = FileUtils.getBlob(new File(FILE_NAME));
        return  Document.builder().content(blob).build();
    }

}
