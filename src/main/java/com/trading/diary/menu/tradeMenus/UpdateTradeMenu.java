package com.trading.diary.menu.tradeMenus;

import com.trading.diary.helpers.Explainer;
import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.formation_menu.support_reversal.paginationMenus.SimplePaginationMenu;
import com.trading.diary.services.TradeService;
import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.Helper;
import org.springframework.stereotype.Service;

@Service
public class UpdateTradeMenu extends AbstractMenu<Trade> {

    private final SimplePaginationMenu<Void, Trade> tradePaginationMenu;

    public UpdateTradeMenu(TradeService tradeService, Explainer explainer) {
        this.tradePaginationMenu = new SimplePaginationMenu<>(
                tradeService::countAllActiveTrade,
                (attr, page) -> tradeService.getAllOpenTrades(page),
                () -> null,
                explainer
        );
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
        Trade trade = tradePaginationMenu.showMenu();
        if (trade == null) {
            print("No open trades found!");
        }
        return trade;
    }
}
