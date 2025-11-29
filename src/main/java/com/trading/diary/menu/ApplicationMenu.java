package com.trading.diary.menu;

import com.trading.diary.configs.ApplicationShutdownManager;
import com.trading.diary.helpers.Explainer;
import com.trading.diary.helpers.Target;
import com.trading.diary.menu.formation_menu.support_reversal.paginationMenus.SimplePaginationMenu;
import com.trading.diary.menu.tradeMenus.*;
import com.trading.diary.pojo.dao.CloseTradeDTO;
import com.trading.diary.pojo.dao.PlannedTradeConfirmationDTO;
import com.trading.diary.services.PersonService;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.services.TradeService;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.trade.impls.Trade;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class ApplicationMenu extends AbstractMenu<Void> implements CommandLineRunner {

    private final PlanTradeMenu<PlannedTrade, PlannedTrade.PlannedTradeBuilder> planTradeMenu;

    private final TradeMenu tradeMenu;

    private final PlannedTradeService plannedTradeService;

    private final TradeService tradeService;

    private final ConfirmPlannedTradeMenu confirmPlannedTradeMenu;

    private final ApplicationShutdownManager applicationShutdownManager;

    private final CloseTradeMenu closeTradeMenu;

    private final UpdateTradeMenu updateTradeMenu;

    private final SimplePaginationMenu<Void, Trade> tradePaginationMenu;

    public ApplicationMenu(PlanTradeMenu<PlannedTrade, PlannedTrade.PlannedTradeBuilder> planTradeMenu,
                           TradeMenu tradeMenu, PlannedTradeService plannedTradeService, PersonMenu personMenu,
                           TradeService tradeService, PersonService personService,
                           ConfirmPlannedTradeMenu confirmPlannedTradeMenu,
                           ApplicationShutdownManager applicationShutdownManager, CloseTradeMenu closeTradeMenu,
                           UpdateTradeMenu updateTradeMenu, Explainer explainer) {
        this.planTradeMenu = planTradeMenu;
        this.tradeMenu = tradeMenu;
        this.plannedTradeService = plannedTradeService;
        this.tradeService = tradeService;
        this.confirmPlannedTradeMenu = confirmPlannedTradeMenu;
        this.applicationShutdownManager = applicationShutdownManager;
        this.closeTradeMenu = closeTradeMenu;
        this.updateTradeMenu = updateTradeMenu;
        tradePaginationMenu = new SimplePaginationMenu<>(
                tradeService::countAllActiveTrade,
                (attr, page) -> tradeService.getAllOpenTrades(page),
                () -> null,
                explainer
        );
    }

    @Override
    public void run(String... args) {
        Class<?> clazz = Target.class;
        print(clazz.getSimpleName());
        showMenu();
    }

    @Override
    public Void showMenu() {
        System.out.println(menu());
        try {
            select();
        } catch (Exception e) {
            print("Some error occurred. Message : " + e.getMessage());
        }
        return showMenu();
    }

    private void select() {
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
                PlannedTradeConfirmationDTO plannedTradeConfirmationDTO = confirmPlannedTradeMenu.showMenu();
                plannedTradeService.confirmPlannedTrade(plannedTradeConfirmationDTO);
            }
            case 4 -> {
                CloseTradeDTO closeTradeDTO = closeTradeMenu.showMenu();
                tradeService.closeTrade(closeTradeDTO);
            }
            case 5 -> {
                tradeService.addTrade(updateTradeMenu.showMenu());
            }
            case 6 -> {
                tradePaginationMenu.showMenu();
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
        sb.append("5. Update trade\n");
        sb.append("0. Exit\n");
        sb.append("==========================\n");
        sb.append("Select an option: ");
        return sb.toString();
    }
}

