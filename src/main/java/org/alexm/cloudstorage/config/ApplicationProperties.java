package org.alexm.cloudstorage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cloudstorage")
@Data
public class ApplicationProperties {
    private int uploadThreads = 5;
    private int maxUploadQueueSize = 1000;
}
