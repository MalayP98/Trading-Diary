package com.trading.diary.menu.trade;

import com.trading.diary.menu.*;
import com.trading.diary.menu.formation_menu.support_reversal.paginationMenus.SimplePaginationMenu;
import com.trading.diary.pojo.Company;
import com.trading.diary.pojo.dao.PlannedTradeConversionDTO;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.trade.impls.Trade;

import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class PlannedTradeConversionMenu extends AbstractMenu<PlannedTradeConversionDTO> {

    private final SimplePaginationMenu<Company, PlannedTrade> plannedTradeByCompanyPaginationMenu;

    private final SimplePaginationMenu<Void, PlannedTrade> plannedTradePaginationMenu;

    private final TradeMenu tradeMenu;

    public PlannedTradeConversionMenu(TradeMenu tradeMenu, PlannedTradeService plannedTradeService) {
        this.tradeMenu = tradeMenu;
        this.plannedTradeByCompanyPaginationMenu = new SimplePaginationMenu<>(
                plannedTradeService::getCount,
                plannedTradeService::getPlannedTradeByCompany,
                () -> new Company(InputType.STRING.nextInput()),
                PlannedTrade::shortString
        );
        this.plannedTradePaginationMenu = new SimplePaginationMenu<>(
                plannedTradeService::getCount,
                (attr, pageable) -> plannedTradeService.getAllPlannedTrade(pageable),
                () -> null,
                PlannedTrade::shortString
        );
    }

    @Override
    public PlannedTradeConversionDTO showMenu() {
        Trade.SimpleTradeBuilder builder = Trade.builder();
        print("=== Log a new Trade ===");
        tradeMenu.showTradeSpecificMenu(builder);
        skipLines(2);

        print("=== Select planned to convert ===");
        return new PlannedTradeConversionDTO(builder.build(), selectPlannedTrade());
    }

    public PlannedTrade selectPlannedTrade(){
        print("1. Show planned trades by company.\n2. Show all planned trades.");
        int choice = InputType.INT.nextInput();
        PlannedTrade plannedTrade = switch (choice) {
            case 1 : yield plannedTradeByCompanyPaginationMenu.showMenu();
            case 2 : yield  plannedTradePaginationMenu.showMenu();
            default : print("Invalid option selected. Please try again!"); yield selectPlannedTrade();
        };
        if(plannedTrade == null){
            print("No trades planned.");
        }
        return plannedTrade;
    }
}
