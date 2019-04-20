package ru.spoddubnyak.testTask.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ru.spoddubnyak.utils.ConfigProperties;

import java.util.ResourceBundle;

@Configuration
public class ThreadPoolConfiguration {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(ConfigProperties.getProperty("threadpool.corepoolsize"));
        pool.setKeepAliveSeconds(ConfigProperties.getProperty("threadpool.keepAliveSeconds"));
        pool.setWaitForTasksToCompleteOnShutdown(false);
        return pool;
    }
}