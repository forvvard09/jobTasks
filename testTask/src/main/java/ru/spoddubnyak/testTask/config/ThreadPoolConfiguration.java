package ru.spoddubnyak.testTask.config;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ResourceBundle;

public class ThreadPoolConfiguration {

    ResourceBundle rb = ResourceBundle.getBundle("threads.properties");



    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(Integer.parseInt(rb.getString("threadpool.corepoolsize")));
        pool.setKeepAliveSeconds(Integer.parseInt(rb.getString("threadpool.keepAliveSeconds")));
        pool.setWaitForTasksToCompleteOnShutdown(false);
        return pool;
    }
}