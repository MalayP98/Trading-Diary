package com.trading.diary.menu.formation_menu.breakout.impls;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.extension.FallingResistanceBreakout;
import com.trading.diary.menu.formation_menu.breakout.ResistanceBreakoutMenuAbstract;
import com.trading.diary.menu.misc.SMAMenu;
import com.trading.diary.menu.misc.StrengthMenu;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class FallingResistanceBreakoutMenuAbstract extends ResistanceBreakoutMenuAbstract<FallingResistanceBreakout, FallingResistanceBreakout.FallingResistanceBreakoutBuilder> {

    public FallingResistanceBreakoutMenuAbstract(StrengthMenu strengthMenu, SMAMenu smaMenu) {
        super(strengthMenu, smaMenu);
    }

    @Override
    public FallingResistanceBreakout showMenu() {
        FallingResistanceBreakout.FallingResistanceBreakoutBuilder builder = FallingResistanceBreakout.builder();
        super.showMenu(builder);
        skipLines(2);

        System.out.println("Angle of the resistance line:");
        float angle = nextFloat();
        builder.angle(angle);
        skipLines(2);

        System.out.println("Prior uptrend (y/n):");
        builder.priorUptrend(nextBoolean());
        skipLines(2);

        System.out.println("Price difference percentage:");
        float priceDiff = nextFloat();
        builder.priceDiffPercentage(priceDiff);
        skipLines(2);

        return builder.build();
    }

    @Override
    public FormationType getFormation() {
        return FormationType.FALLING_RESISTANCE_BREAKOUT;
    }
}
