package com.trading.diary.menu.targetMenu;

import com.trading.diary.menu.AbstractTargetMenu;
import org.springframework.stereotype.Service;

@Service
public class StoplossMenu extends AbstractTargetMenu {

    @Override
    protected String targetType() {
        return "Stoploss";
    }
}
