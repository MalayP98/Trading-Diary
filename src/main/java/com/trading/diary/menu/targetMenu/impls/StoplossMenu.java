package com.trading.diary.menu.targetMenu.impls;

import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.targetMenu.AbstractTargetMenu;
import org.springframework.stereotype.Service;

@Service
public class StoplossMenu extends AbstractTargetMenu {

    @Override
    protected String targetType() {
        return "Stoploss";
    }

    @Override
    public MenuName menuName() {
        return MenuName.STOPLOSS_MENU;
    }
}
