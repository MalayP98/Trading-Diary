package com.trading.diary.configs;

import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1)
public class OnStartUp implements CommandLineRunner {

    private final MenuFactory menuFactory;

    public OnStartUp(MenuFactory menuFactory) {
        this.menuFactory = menuFactory;
    }

    @Override
    public void run(String... args) {
        menuFactory.getMenu(MenuName.APPLICATION_MENU).showMenu();
    }
}
