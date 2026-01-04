package com.trading.diary.menu.formation_menu.support_reversal.impls;

import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.menu.formation_menu.support_reversal.AbstractSupportReversalMenu;
import com.trading.diary.utils.Strength;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class BullishEngulfingMenuSupportReversalMenu extends AbstractSupportReversalMenu<BullishEngulfing ,BullishEngulfing.BullishEngulfingBuilder> {

    public BullishEngulfingMenuSupportReversalMenu(MenuFactory menuFactory) {
        super(menuFactory);
    }

    @Override
    public BullishEngulfing showMenu() {
        BullishEngulfing.BullishEngulfingBuilder builder = BullishEngulfing.builder();
        super.showMenu(builder);
        skipLines(2);

        System.out.print("Is there partial bottom engulfing? (y/n): ");
        builder.partialBottomEngulfing(InputType.BOOLEAN.nextInput());
        skipLines(2);

        System.out.print("Is there partial top engulfing? (y/n): ");
        builder.partialTopEngulfing(InputType.BOOLEAN.nextInput());
        skipLines(2);

        builder.volume((Strength) menuFactory.getMenu(MenuName.STRENGTH_MENU).showMenu());
        skipLines(2);

        return builder.build();
    }

    @Override
    public MenuName menuName() {
        return MenuName.BULLISH_ENGULFING_MENU;
    }


}
