package com.uber.service;


import com.uber.config.PropertyConfig;
import com.uber.exception.DocumentValidationException;
import com.uber.exception.codes.ErrorCode;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mock.azure.blobstorage", havingValue = "false", matchIfMissing = true)
@Slf4j
public class BlobStorageService{

    private CloudBlobContainer cloudBlobContainer;

    @Autowired
    private PropertyConfig propertyConfig;

    public URI downloadFile(File file, String containerName) {
        log.info("downloading file {} from blob", file);

        try {
            cloudBlobContainer = CloudStorageAccount.parse(propertyConfig.getConnectionStr()).createCloudBlobClient().getContainerReference(containerName);
            cloudBlobContainer.createIfNotExists();

            CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(file.getName());

            blob.download(new FileOutputStream(file));
            return blob.getUri();
        } catch (URISyntaxException | StorageException | IOException | InvalidKeyException e) {
            log.error("exception while download file from blob", e);
            throw new DocumentValidationException(new DocumentValidationException.Errors(ErrorCode.BLOB_DOWNLOAD_ERROR.name(), e.getMessage()));
        }

    }
}