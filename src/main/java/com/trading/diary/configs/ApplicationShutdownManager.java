package com.trading.diary.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationShutdownManager {

    private final ApplicationContext appContext;

    public void initiateShutdown(int returnCode){
        SpringApplication.exit(appContext, () -> returnCode);
    }
}