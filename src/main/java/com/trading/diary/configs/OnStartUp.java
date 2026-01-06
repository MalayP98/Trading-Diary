package com.trading.diary.configs;

import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.services.CompanyService;
import com.trading.diary.services.PersonService;
import com.trading.diary.services.PlannedTradeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(2)
public class OnStartUp implements CommandLineRunner {

    private final MenuFactory menuFactory;

    private final PersonService personService;

    private final PlannedTradeService plannedTradeService;

    private final CompanyService companyService;

    public OnStartUp(MenuFactory menuFactory, PersonService personService, PlannedTradeService plannedTradeService, CompanyService companyService) {
        this.menuFactory = menuFactory;
        this.personService = personService;
        this.plannedTradeService = plannedTradeService;
        this.companyService = companyService;
    }

    @Override
    public void run(String... args) {
        createSelfPerson();
        showApplicationMenu();
    }

    private void showApplicationMenu(){
        menuFactory.getMenu(com.trading.diary.menu.MenuName.APPLICATION_MENU).showMenu();
    }

    private void createSelfPerson(){
        personService.getOrCreatePerson("Self");
    }
}
