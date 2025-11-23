package com.trading.diary.menu.trade;

import com.trading.diary.formations.Formation;
import com.trading.diary.menu.CompanyMenu;
import com.trading.diary.menu.MarketCapMenu;
import com.trading.diary.menu.PersonMenu;
import com.trading.diary.menu.TimeFrameMenu;
import com.trading.diary.menu.formation_menu.FormationMenuFactory;
import com.trading.diary.menu.targetMenu.StoplossMenu;
import com.trading.diary.menu.targetMenu.TargetMenu;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.impls.Trade;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class TradeMenu extends PlanTradeMenu<Trade, Trade.SimpleTradeBuilder>{

    public TradeMenu(TimeFrameMenu timeFrameMenu, MarketCapMenu marketCapMenu, PersonMenu personMenu, TargetMenu targetMenu, StoplossMenu stoplossMenu, CompanyMenu companyMenu, FormationMenuFactory formationMenuFactory, FormationServiceFactory<? super Formation> formationServiceFactory) {
        super(timeFrameMenu, marketCapMenu, personMenu, targetMenu, stoplossMenu, companyMenu, formationMenuFactory, formationServiceFactory);
    }

    @Override
    public Trade showMenu() {
        Trade.SimpleTradeBuilder builder = Trade.builder();
        print("=== Log a new Trade ===");
        buildPlannedTrade(builder);
        skipLines(2);

        showTradeSpecificMenu(builder);

        return builder.build();
    }

    protected void showTradeSpecificMenu(Trade.SimpleTradeBuilder builder){
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
}
