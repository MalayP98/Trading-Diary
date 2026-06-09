package com.trading.diary.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

/**
 * Centralizes application shutdown so terminal windows can request a clean Spring Boot exit without knowing about the broader application lifecycle.
 */
@Component
@RequiredArgsConstructor
public class ApplicationShutdownManager {

    private final ApplicationContext appContext;

    /**
     * Requests a graceful Spring Boot shutdown with the supplied process exit code so UI code does not need to call SpringApplication directly.
     */
    public void initiateShutdown(int returnCode){
        SpringApplication.exit(appContext, () -> returnCode);
    }
}
