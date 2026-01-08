package com.trading.diary.menu.tradeMenus;

import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.Helper;
import org.springframework.stereotype.Service;

@Service
public class UpdateTradeMenu extends AbstractMenu<Trade> {

    private final MenuFactory menuFactory;

    public UpdateTradeMenu(MenuFactory menuFactory) {
        this.menuFactory = menuFactory;
    }

    @Override
    public Trade showMenu() {
        Trade trade = selectTrade();
        Helper.skipLines(2);

        print("Enter new quantity of shares.");
        int quantity = InputType.INT.nextInput();

        print("Enter new average buying price.");
        float averageBuyingPrice = InputType.FLOAT.nextInput();

        print("Any notes?");
        String notes = InputType.STRING.nextSkipableInput();

        trade.addShare(quantity, averageBuyingPrice);
        trade.addNotes(notes);
        return trade;
    }

    protected Trade selectTrade() {
        print("=== Select a Trade ===");
        Trade trade = (Trade) menuFactory.getMenu(MenuName.OPEN_TRADE_PAGINATION_MENU).showMenu();
        if (trade == null) {
            throw new RuntimeException("No open trades found!");
        }
        return trade;
    }

    @Override
    public MenuName menuName() {
        return MenuName.UPDATE_TRADE_MENU;
    }
}
