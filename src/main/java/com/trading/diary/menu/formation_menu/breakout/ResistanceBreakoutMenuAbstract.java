package com.trading.diary.menu.formation_menu.breakout;

import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.menu.formation_menu.AbstractFormationMenu;
import com.trading.diary.menu.misc.SMAMenu;
import com.trading.diary.menu.misc.StrengthMenu;
import lombok.RequiredArgsConstructor;

import static com.trading.diary.utils.Helper.skipLines;

@RequiredArgsConstructor
public abstract class ResistanceBreakoutMenuAbstract<T extends ResistanceBreakout, B extends ResistanceBreakout.ResistanceBreakoutBuilder<B>> extends AbstractFormationMenu<T> {

    private final StrengthMenu strengthMenu;

    private final SMAMenu smaMenu;

    protected void showMenu(B builder) {

        System.out.print("Confirm breakout (y/n): ");
        builder.confirmBreakout(nextBoolean());
        skipLines(2);

        builder.breakoutVolume(strengthMenu.showMenu());
        skipLines(2);

        System.out.print("All time high (y/n) ");
        builder.allTimeHigh(nextBoolean());
        skipLines(2);

        System.out.print("Touches ");
        builder.touches(nextInt());
        skipLines(2);

        System.out.print("Resistance length (int days) ");
        builder.resistanceLength(nextInt());
        skipLines(2);

        System.out.print("Higher lows (y/n) ");
        builder.higherLows(nextBoolean());
        skipLines(2);

        System.out.print("RSI (float) ");
        builder.rsi(nextFloat());
        skipLines(2);

        System.out.print("Breakout percentage (float) ");
        builder.breakoutPercentage(nextFloat());
        skipLines(2);

        System.out.print("SMA 20");
        builder.sma20(smaMenu.showMenu());
        skipLines(2);

        System.out.print("SMA 50");
        builder.sma50(smaMenu.showMenu());
        skipLines(2);

        System.out.print("SMA 200");
        builder.sma200(smaMenu.showMenu());
    }
}
