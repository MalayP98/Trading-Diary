package com.trading.diary.menu.formation_menu.support_reversal.impls;

import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.menu.formation_menu.support_reversal.AbstractSupportReversalMenu;
import com.trading.diary.utils.CandleColor;
import com.trading.diary.utils.Strength;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class MorningStarMenuSupportReversalMenu extends AbstractSupportReversalMenu<MorningStar, MorningStar.MorningStarBuilder> {

    public MorningStarMenuSupportReversalMenu(MenuFactory menuFactory) {
        super(menuFactory);
    }

    @Override
    public MorningStar showMenu() {
        MorningStar.MorningStarBuilder builder = MorningStar.builder();
        super.showMenu(builder);
        skipLines(2);

        builder.dogiColor((CandleColor) menuFactory.getMenu(MenuName.CANDLE_COLOR_MENU).showMenu());
        skipLines(2);

        builder.volume((Strength) menuFactory.getMenu(MenuName.STRENGTH_MENU).showMenu());
        skipLines(2);

        return builder.build();
    }

    @Override
    public MenuName menuName() {
        return MenuName.MORNING_STAR_MENU;
    }
}
