package com.trading.diary.menu.tradeMenus.core;

import com.trading.diary.formations.Formation;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.trade.impls.Trade;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class TradeMenu extends AbstractPlannedTradeMenu<Trade> {

    protected TradeMenu(FormationServiceFactory<? super Formation> formationServiceFactory, MenuFactory menuFactory) {
        super(formationServiceFactory, menuFactory);
    }

    @Override
    public Trade buildWithPlannedTrade(PlannedTrade plannedTrade) {
        Trade.SimpleTradeBuilder builder = Trade.builder();
        showTradeSpecificMenu(builder);
        return builder.buildWithPlannedTrade(plannedTrade);
    }

    public void showTradeSpecificMenu(Trade.SimpleTradeBuilder builder){
        print("Average buying price:");
        float avgBuyingPrice = InputType.FLOAT.nextInput();
        builder.averageBuyingPrice(avgBuyingPrice);
        skipLines(2);

        print("Quantity:");
        int quantity = InputType.INT.nextInput();
        builder.shares(quantity);
        skipLines(2);

        print("Opening date (DD/MM/YYYY):");
        builder.openingDate(InputType.DATE.nextInput());
    }

    @Override
    public MenuName menuName() {
        return MenuName.TRADE_MENU;
    }
}
