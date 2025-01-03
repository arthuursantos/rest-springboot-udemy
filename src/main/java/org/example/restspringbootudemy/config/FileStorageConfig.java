package org.example.restspringbootudemy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data

@Configuration
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {
    private String upload_dir;
}
