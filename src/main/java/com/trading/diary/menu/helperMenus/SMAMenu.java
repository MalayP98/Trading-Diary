package com.trading.diary.menu.helperMenus;

import com.trading.diary.helpers.SMA;
import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.utils.PricePosition;
import com.trading.diary.utils.TrendlineDirections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SMAMenu extends AbstractMenu<SMA> {

    private final MenuFactory menuFactory;

    @Override
    public SMA showMenu() {
        TrendlineDirections dir = (TrendlineDirections) menuFactory.getMenu(MenuName.TRENDLINE_MENU).showMenu();
        PricePosition pricePos = (PricePosition) menuFactory.getMenu(MenuName.PRICE_POSITION_MENU).showMenu();
        return new SMA(dir, pricePos);
    }

    @Override
    public MenuName menuName() {
        return MenuName.SMA_MENU;
    }
}
