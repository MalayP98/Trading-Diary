package com.trading.diary.menu;

import com.trading.diary.configs.ApplicationShutdownManager;
import com.trading.diary.helpers.Target;
import com.trading.diary.menu.formation_menu.support_reversal.paginationMenus.SimplePaginationMenu;
import com.trading.diary.menu.trade.PlannedTradeConversionMenu;
import com.trading.diary.menu.trade.TradeMenu;
import com.trading.diary.menu.trade.PlanTradeMenu;
import com.trading.diary.pojo.MarketCap;
import com.trading.diary.pojo.Person;
import com.trading.diary.pojo.dao.PlannedTradeConversionDTO;
import com.trading.diary.services.PersonService;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.services.TradeService;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.utils.TimeFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;

import static com.trading.diary.utils.Helper.skipLines;

@Service
@RequiredArgsConstructor
public class ApplicationMenu extends AbstractMenu<Void> implements CommandLineRunner {

    private final PlanTradeMenu<PlannedTrade, PlannedTrade.PlannedTradeBuilder> planTradeMenu;

    private final TradeMenu tradeMenu;

    private final PlannedTradeService plannedTradeService;

    private final PersonMenu personMenu;

    private final TradeService tradeService;

    private final PersonService personService;

    private final PlannedTradeConversionMenu plannedTradeConversionMenu;

    private final ApplicationShutdownManager applicationShutdownManager;

    @Override
    public void run(String... args) {
//        plannedTradeService.savePlannedTrade(
//                PlannedTrade.builder()
//                        .notes("No notes")
//                        .suggestedBy(personService.getOrCreatePerson("Self"))
//                        .marketCap(new MarketCap(true, true))
//                        .addStoploss(Arrays.asList(Target.getTarget(123.45f)))
//                        .addTarget(Arrays.asList(Target.getTarget(345.67f)))
//                        .timeFrame(TimeFrame.DAILY)
//                        .build()
//        );
        showMenu();
    }

    @Override
    public Void showMenu() {
        System.out.println(menu());
        try{
            select();
        } catch (Exception e){
            print("Some error occurred. Message : " + e.getMessage());
        }
        return showMenu();
    }

    private void select(){
        int choice = InputType.INT.nextInput();
        switch (choice) {
            case 0 -> {
                System.out.println("Exiting Trading Diary. Goodbye!");
                applicationShutdownManager.initiateShutdown(0);
            }
            case 1 -> {
                plannedTradeService.savePlannedTrade(planTradeMenu.showMenu());
                skipLines(2);
            }
            case 2 -> {
                tradeService.addTrade(tradeMenu.showMenu());
                skipLines(2);
            }
            case 3 -> {
                PlannedTradeConversionDTO plannedTradeConversionDTO = plannedTradeConversionMenu.showMenu();
                plannedTradeService.confirmPlannedTrade(plannedTradeConversionDTO);
            }
            default -> {
                System.out.println("Invalid choice. Please try again.");
                showMenu();
            }
        }
    }

    private String menu() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Trading Diary Menu ===\n");
        sb.append("1. Plan a Trade\n");
        sb.append("2. Log a Trade\n");
        sb.append("3. Open a planned trade\n");
        sb.append("0. Exit\n");
        sb.append("==========================\n");
        sb.append("Select an option: ");
        return sb.toString();
    }
}

