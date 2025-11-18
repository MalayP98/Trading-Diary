package com.trading.diary.menu;

import com.trading.diary.helpers.Target;
import com.trading.diary.menu.trade.LongTradeMenu;
import com.trading.diary.menu.trade.PlanTradeMenu;
import com.trading.diary.pojo.Company;
import com.trading.diary.pojo.MarketCap;
import com.trading.diary.pojo.Person;
import com.trading.diary.services.CompanyService;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.services.TradeService;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.utils.TargetStatus;
import com.trading.diary.utils.TimeFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
@RequiredArgsConstructor
public class ApplicationMenu extends AbstractMenu<Void> implements CommandLineRunner {

    private final PlanTradeMenu<PlannedTrade, PlannedTrade.PlannedTradeBuilder> planTradeMenu;

    private final LongTradeMenu longTradeMenu;

    private final PlannedTradeService plannedTradeService;

    private final TradeService tradeService;

    private final CompanyService companyService;

    @Override
    public void run(String... args) {
        showMenu();
    }


    @Override
    public Void showMenu() {
        System.out.println(menu());
        int choice = nextInt();
        switch (choice) {
            case 0 -> {
                System.out.println("Exiting Trading Diary. Goodbye!");
                return null;
            }
            case 1 -> {
                plannedTradeService.savePlannedTrade(planTradeMenu.showMenu());
                skipLines(2);
            }
            case 2 -> {
                tradeService.addTrade(longTradeMenu.showMenu());
                skipLines(2);
            }
            default -> {
                System.out.println("Invalid choice. Please try again.");
                return showMenu();
            }
        }
        return showMenu();
    }

    private String menu(){
        StringBuilder sb = new StringBuilder();
        sb.append("=== Trading Diary Menu ===\n");
        sb.append("1. Plan a Trade\n");
        sb.append("2. Log a Long Trade\n");
        sb.append("0. Exit\n");
        sb.append("==========================\n");
        sb.append("Select an option: ");
        return sb.toString();
    }
}

