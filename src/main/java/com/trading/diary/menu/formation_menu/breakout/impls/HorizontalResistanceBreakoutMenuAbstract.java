package com.trading.diary.menu.formation_menu.breakout.impls;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.extension.HorizontalResistanceBreakout;
import com.trading.diary.menu.formation_menu.breakout.ResistanceBreakoutMenuAbstract;
import com.trading.diary.menu.misc.SMAMenu;
import com.trading.diary.menu.misc.StrengthMenu;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class HorizontalResistanceBreakoutMenuAbstract extends ResistanceBreakoutMenuAbstract<HorizontalResistanceBreakout, HorizontalResistanceBreakout.HorizontalResistanceBreakoutBuilder> {

    public HorizontalResistanceBreakoutMenuAbstract(StrengthMenu strengthMenu, SMAMenu smaMenu) {
        super(strengthMenu, smaMenu);
    }

    @Override
    public HorizontalResistanceBreakout showMenu() {
        HorizontalResistanceBreakout.HorizontalResistanceBreakoutBuilder builder = HorizontalResistanceBreakout.builder();
        super.showMenu(builder);
        skipLines(2);

        return builder.build();
    }

    @Override
    public FormationType getFormation() {
        return FormationType.HORIZONTAL_RESISTANCE_BREAKOUT;
    }
}
