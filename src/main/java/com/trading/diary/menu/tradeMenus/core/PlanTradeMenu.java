package com.trading.diary.menu.tradeMenus.core;

import com.trading.diary.formations.Formation;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.impls.PlannedTrade;
import org.springframework.stereotype.Service;

@Service
public class PlanTradeMenu extends AbstractPlannedTradeMenu<PlannedTrade> {

    protected PlanTradeMenu(FormationServiceFactory<? super Formation> formationServiceFactory, MenuFactory menuFactory) {
        super(formationServiceFactory, menuFactory);
    }

    @Override
    public PlannedTrade buildWithPlannedTrade(PlannedTrade plannedTrade) {
        return plannedTrade;
    }

    @Override
    public MenuName menuName() {
        return MenuName.PLANNED_TRADE_MENU;
    }
}
