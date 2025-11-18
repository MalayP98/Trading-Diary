package com.trading.diary.menu.misc;

import com.trading.diary.helpers.SMA;
import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.utils.PricePosition;
import com.trading.diary.utils.TrendlineDirections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SMAMenu extends AbstractMenu<SMA> {

    private final TrendlineDirectionMenu trendlineDirectionMenu;

    private final PricePositionMenu pricePositionMenu;

    @Override
    public SMA showMenu() {
        TrendlineDirections dir = trendlineDirectionMenu.showMenu();
        PricePosition pricePos = pricePositionMenu.showMenu();
        return new SMA(dir, pricePos);
    }
}
