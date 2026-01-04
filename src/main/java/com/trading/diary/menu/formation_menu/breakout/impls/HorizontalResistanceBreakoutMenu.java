package com.trading.diary.menu.formation_menu.breakout.impls;

import com.trading.diary.formations.impls.resistance.extension.HorizontalResistanceBreakout;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.menu.formation_menu.breakout.AbstractResistanceBreakoutMenu;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class HorizontalResistanceBreakoutMenu extends AbstractResistanceBreakoutMenu<HorizontalResistanceBreakout, HorizontalResistanceBreakout.HorizontalResistanceBreakoutBuilder> {

    public HorizontalResistanceBreakoutMenu(MenuFactory menuFactory) {
        super(menuFactory);
    }

    @Override
    public HorizontalResistanceBreakout showMenu() {
        HorizontalResistanceBreakout.HorizontalResistanceBreakoutBuilder builder = HorizontalResistanceBreakout.builder();
        super.showMenu(builder);
        skipLines(2);

        return builder.build();
    }

    @Override
    public MenuName menuName() {
        return MenuName.HORIZONTAL_RESISTANCE_BREAKOUT_MENU;
    }
}
