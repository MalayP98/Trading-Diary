package com.trading.diary.menu.paginationMenus.impls;

import com.trading.diary.explainers.impls.PlannedTradeExplainer;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.paginationMenus.SimplePaginationMenu;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.trade.impls.PlannedTrade;
import org.springframework.stereotype.Service;

@Service
public class PlannedTradePaginationMenu extends SimplePaginationMenu<Void, PlannedTrade> {

    public PlannedTradePaginationMenu(PlannedTradeService plannedTradeService, PlannedTradeExplainer explainer) {
        super(plannedTradeService::getCount,
                (attr, pageable) -> plannedTradeService.getAllPlannedTrade(pageable),
                () -> null,
                explainer);
    }

    @Override
    public MenuName menuName() {
        return MenuName.PLANNED_TRADE_PAGINATION_MENU;
    }
}
