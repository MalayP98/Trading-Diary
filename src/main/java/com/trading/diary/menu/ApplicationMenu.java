package com.trading.diary.menu;

import com.trading.diary.configs.ApplicationShutdownManager;
import com.trading.diary.menu.tradeMenus.CloseTradeMenu;
import com.trading.diary.menu.tradeMenus.ConfirmPlannedTradeMenu;
import com.trading.diary.menu.tradeMenus.TradeMenu;
import com.trading.diary.menu.tradeMenus.PlanTradeMenu;
import com.trading.diary.pojo.dao.CloseTradeDTO;
import com.trading.diary.pojo.dao.PlannedTradeConversionDTO;
import com.trading.diary.services.PersonService;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.services.TradeService;
import com.trading.diary.trade.impls.PlannedTrade;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

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

    private final ConfirmPlannedTradeMenu confirmPlannedTradeMenu;

    private final ApplicationShutdownManager applicationShutdownManager;

    private final CloseTradeMenu closeTradeMenu;

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
                PlannedTradeConversionDTO plannedTradeConversionDTO = confirmPlannedTradeMenu.showMenu();
                plannedTradeService.confirmPlannedTrade(plannedTradeConversionDTO);
            }
            case 4 -> {
                CloseTradeDTO closeTradeDTO = closeTradeMenu.showMenu();
                tradeService.closeTrade(closeTradeDTO);
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
        sb.append("4. Close a trade\n");
        sb.append("0. Exit\n");
        sb.append("==========================\n");
        sb.append("Select an option: ");
        return sb.toString();
    }
}

