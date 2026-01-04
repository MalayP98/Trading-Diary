package com.trading.diary.menu.targetMenu.impls;

import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.targetMenu.AbstractTargetMenu;
import org.springframework.stereotype.Service;

@Service
public class TargetMenu extends AbstractTargetMenu {

    @Override
    protected String targetType() {
        return "Target";
    }

    @Override
    public MenuName menuName() {
        return MenuName.TARGET_MENU;
    }
}
