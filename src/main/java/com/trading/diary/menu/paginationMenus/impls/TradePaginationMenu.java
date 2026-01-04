package com.trading.diary.menu.paginationMenus.impls;

import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.paginationMenus.SimplePaginationMenu;
import com.trading.diary.services.TradeService;
import com.trading.diary.trade.impls.Trade;
import org.springframework.stereotype.Service;

@Service
public class TradePaginationMenu extends SimplePaginationMenu<Void, Trade> {

    public TradePaginationMenu(TradeService tradeService) {
        super(tradeService::countAllActiveTrade,
                (attr, page) -> tradeService.getAllOpenTrades(page),
                () -> null,
                null);
    }

    @Override
    public MenuName menuName() {
        return MenuName.TRADE_PAGINATION_MENU;
    }
}
