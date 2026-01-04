package com.trading.diary.menu.formation_menu.support_reversal.impls;

import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.menu.formation_menu.support_reversal.AbstractSupportReversalMenu;
import com.trading.diary.utils.CandleColor;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class HammerMenuSupportReversalMenu extends AbstractSupportReversalMenu<Hammer, Hammer.HammerBuilder> {

    public HammerMenuSupportReversalMenu(MenuFactory menuFactory) {
        super(menuFactory);
    }

    @Override
    public Hammer showMenu() {
        Hammer.HammerBuilder builder = Hammer.builder();
        super.showMenu(builder);
        skipLines(2);

        builder.hammerColor((CandleColor) menuFactory.getMenu(MenuName.CANDLE_COLOR_MENU).showMenu());
        skipLines(2);

        System.out.print("Is the lower wick less than 2x of upper wick? (y/n): ");
        builder.smallLowerWick(InputType.BOOLEAN.nextInput());
        skipLines(2);

        return builder.build();
    }

    @Override
    public MenuName menuName() {
        return MenuName.HAMMER_MENU;
    }
}
