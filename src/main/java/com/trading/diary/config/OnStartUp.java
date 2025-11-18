package com.trading.diary.config;

import com.trading.diary.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(0)
@Service
@RequiredArgsConstructor
public class OnStartUp implements CommandLineRunner {

    private final PersonService personService;

    @Override
    public void run(String... args) throws Exception {
        addSelf();
    }

    private void addSelf(){
        personService.getOrCreatePerson("Self");
    }
}
