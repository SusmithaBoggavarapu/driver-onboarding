package com.uber.service;

import com.uber.common.exception.ApplicationException;
import com.uber.common.exception.BadRequestException;
import com.uber.common.exception.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${files.tmp_location:/tmp/uploads}")
    private String tmpLocation = "/tmp/uploads/";

    private Path tmpPath;

    @Autowired
    public FileService() {
        this.tmpPath = Paths.get(tmpLocation);
    }

    public File saveFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new BadRequestException(Errors.EMPTY_FILE, String.format("Failed to store empty file %s", file.getOriginalFilename()));
        }
        try {
            Files.copy(file.getInputStream(), tmpPath.resolve(file.getOriginalFilename()));
            return tmpPath.resolve(file.getOriginalFilename()).toFile();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }



}
