package com.trading.diary.menu.tradeMenus;

import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.menu.tradeMenus.core.TradeMenu;
import com.trading.diary.pojo.dto.PlannedTradeConfirmationDTO;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.trade.impls.Trade;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
@Transactional
public class ConfirmPlannedTradeMenu extends AbstractMenu<PlannedTradeConfirmationDTO> {

    private final MenuFactory menuFactory;

    private final TradeMenu tradeMenu;

    public ConfirmPlannedTradeMenu(MenuFactory menuFactory, TradeMenu tradeMenu) {
        this.menuFactory = menuFactory;
        this.tradeMenu = tradeMenu;
    }

    @Override
    public PlannedTradeConfirmationDTO showMenu() {
        print("=== Select planned trade to confirm ===");
        PlannedTrade plannedTrade = selectPlannedTrade();
        skipLines(2);

        Trade.SimpleTradeBuilder builder = Trade.builder();
        print("=== Log a new Trade ===");
        tradeMenu.showTradeSpecificMenu(builder);

        return new PlannedTradeConfirmationDTO(builder.buildWithPlannedTrade(plannedTrade), plannedTrade);
    }

    public PlannedTrade selectPlannedTrade() {
        print("1. Show planned trades by company.\n2. Show all planned trades.");
        int choice = InputType.INT.nextInput();
        PlannedTrade plannedTrade = (PlannedTrade) switch (choice) {
            case 1 : yield menuFactory.getMenu(MenuName.PLANNED_TRADE_BY_COMPANY_PAGINATION_MENU).showMenu();
            case 2 : yield  menuFactory.getMenu(MenuName.PLANNED_TRADE_PAGINATION_MENU).showMenu();
            default : print("Invalid option selected. Please try again!"); yield selectPlannedTrade();
        };
        if(plannedTrade == null){
            throw new RuntimeException("No planned trade found.");
        }
        return plannedTrade;
    }

    @Override
    public MenuName menuName() {
        return MenuName.CONFIRM_PLANNED_TRADE_MENU;
    }
}
