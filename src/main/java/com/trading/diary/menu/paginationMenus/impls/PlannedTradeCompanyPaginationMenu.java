package com.trading.diary.menu.paginationMenus.impls;

import com.trading.diary.explainers.impls.PlannedTradeExplainer;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.menu.paginationMenus.SimplePaginationMenu;
import com.trading.diary.pojo.Company;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.trade.impls.PlannedTrade;
import org.springframework.stereotype.Service;

@Service
public class PlannedTradeCompanyPaginationMenu extends SimplePaginationMenu<Company, PlannedTrade> {

    public PlannedTradeCompanyPaginationMenu(PlannedTradeService plannedTradeService,
                                             MenuFactory menuFactory, PlannedTradeExplainer explainer) {
        super(plannedTradeService::getCount,
                (attr, pageable) -> plannedTradeService.getAllPlannedTrade(pageable),
                () -> (Company) menuFactory.getMenu(MenuName.COMPANY_MENU).showMenu(),
                explainer);
    }

    @Override
    public MenuName menuName() {
        return MenuName.PLANNED_TRADE_BY_COMPANY_PAGINATION_MENU;
    }
}
