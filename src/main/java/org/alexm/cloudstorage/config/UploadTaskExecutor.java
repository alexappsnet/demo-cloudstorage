package org.alexm.cloudstorage.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@RequiredArgsConstructor
public class UploadTaskExecutor {
    private final ApplicationProperties properties;
    private final AtomicInteger threadIdCounter = new AtomicInteger(0);

    @Bean("uploads")
    public TaskExecutor uploadTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getUploadThreads());
        executor.setMaxPoolSize(properties.getUploadThreads());
        executor.setThreadFactory(runnable -> {
            Thread thread = new Thread(runnable, "Upload File " + threadIdCounter.incrementAndGet());
            thread.setDaemon(true);
            return thread;
        });
        executor.setQueueCapacity(properties.getMaxUploadQueueSize());
        return executor;
    }
}
