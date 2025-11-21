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
import com.trading.diary.trade.impls.LongTrade;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class LongTradeMenu extends PlanTradeMenu<LongTrade, LongTrade.SimpleTradeBuilder>{

    public LongTradeMenu(TimeFrameMenu timeFrameMenu, MarketCapMenu marketCapMenu, PersonMenu personMenu, TargetMenu targetMenu, StoplossMenu stoplossMenu, CompanyMenu companyMenu, FormationMenuFactory formationMenuFactory, FormationServiceFactory<? super Formation> formationServiceFactory) {
        super(timeFrameMenu, marketCapMenu, personMenu, targetMenu, stoplossMenu, companyMenu, formationMenuFactory, formationServiceFactory);
    }

    @Override
    public LongTrade showMenu() {
        LongTrade.SimpleTradeBuilder builder = LongTrade.builder();
        System.out.println("=== Plan a New Long Trade ===");
        buildPlannedTrade(builder);
        skipLines(2);

        System.out.println("Average buying price:");
        float avgBuyingPrice = InputType.FLOAT.nextInput();
        builder.averageBuyingPrice(avgBuyingPrice);
        skipLines(2);

        System.out.println("Quantity:");
        int quantity = InputType.INT.nextInput();
        builder.shares(quantity);
        skipLines(2);

        System.out.println("Opening date (DD/MM/YYYY):");
        builder.openingDate(InputType.DATE.nextInput());

        return builder.build();
    }
}
