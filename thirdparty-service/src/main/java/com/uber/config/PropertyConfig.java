package com.uber.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class PropertyConfig {

    @Value("${azure.blob.connection.string}")
    private String connectionStr;

    @Value("${azure.blob.container.name}")
    private String containerName;

}