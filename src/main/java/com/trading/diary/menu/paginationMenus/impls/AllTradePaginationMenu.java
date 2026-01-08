package com.trading.diary.menu.paginationMenus.impls;

import com.trading.diary.explainers.impls.TradeExplainer;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.paginationMenus.SimplePaginationMenu;
import com.trading.diary.services.TradeService;
import com.trading.diary.trade.impls.Trade;
import org.springframework.stereotype.Service;

@Service
public class AllTradePaginationMenu extends SimplePaginationMenu<Void, Trade> {

    public AllTradePaginationMenu(TradeService tradeService, TradeExplainer explainer) {
        super(attr -> tradeService.countAllTrade(),
                (attr, page) -> tradeService.getAllOpenTrades(page),
                () -> null,
                explainer);
    }

    @Override
    public MenuName menuName() {
        return MenuName.ALL_TRADE_PAGINATION_MENU;
    }
}
