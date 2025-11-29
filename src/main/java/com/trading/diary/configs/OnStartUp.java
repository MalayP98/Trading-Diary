package com.trading.diary.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1)
public class OnStartUp implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

    }

}
