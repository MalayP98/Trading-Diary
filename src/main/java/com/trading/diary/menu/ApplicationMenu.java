package com.trading.diary.menu;

import com.trading.diary.configs.ApplicationShutdownManager;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.pojo.dto.CloseTradeDTO;
import com.trading.diary.pojo.dto.PlannedTradeConfirmationDTO;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.services.TradeService;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.trade.impls.Trade;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class ApplicationMenu extends AbstractMenu<Void> {

    private final PlannedTradeService plannedTradeService;

    private final TradeService tradeService;

    private final ApplicationShutdownManager applicationShutdownManager;

    private final MenuFactory menuFactory;

    public ApplicationMenu(PlannedTradeService plannedTradeService, TradeService tradeService,
                           ApplicationShutdownManager applicationShutdownManager, MenuFactory menuFactory) {
        this.plannedTradeService = plannedTradeService;
        this.tradeService = tradeService;
        this.applicationShutdownManager = applicationShutdownManager;
        this.menuFactory = menuFactory;
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

    @Override
    public MenuName menuName() {
        return MenuName.APPLICATION_MENU;
    }

    private void select() {
        int choice = InputType.INT.nextInput();
        switch (choice) {
            case 0 -> {
                print("Exiting Trading Diary. Goodbye!");
                applicationShutdownManager.initiateShutdown(0);
            }
            case 1 -> {
                plannedTradeService.savePlannedTrade((PlannedTrade) menuFactory.getMenu(MenuName.PLANNED_TRADE_MENU).showMenu());
                skipLines(2);
            }
            case 2 -> {
                tradeService.addTrade((Trade) menuFactory.getMenu(MenuName.TRADE_MENU).showMenu());
                skipLines(2);
            }
            case 3 -> {
                PlannedTradeConfirmationDTO plannedTradeConfirmationDTO =
                        (PlannedTradeConfirmationDTO) menuFactory.getMenu(MenuName.CONFIRM_PLANNED_TRADE_MENU)
                                .showMenu();
                plannedTradeService.confirmPlannedTrade(plannedTradeConfirmationDTO);
            }
            case 4 -> {
                CloseTradeDTO closeTradeDTO = (CloseTradeDTO) menuFactory.getMenu(MenuName.CLOSE_TRADE_MENU).showMenu();
                tradeService.closeTrade(closeTradeDTO);
            }
            case 5 -> {
                tradeService.addTrade((Trade) menuFactory.getMenu(MenuName.UPDATE_TRADE_MENU).showMenu());
            }
            case 6 -> {
                menuFactory.getMenu(MenuName.TRADE_PAGINATION_MENU).showMenu();
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
        sb.append("6. View Open Trades\n");
        sb.append("0. Exit\n");
        sb.append("==========================\n");
        sb.append("Select an option: ");
        return sb.toString();
    }
}
