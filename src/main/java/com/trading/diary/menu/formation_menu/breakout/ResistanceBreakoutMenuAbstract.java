package com.trading.diary.menu.formation_menu.breakout;

import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.menu.TimeLengthMenu;
import com.trading.diary.menu.formation_menu.AbstractFormationMenu;
import com.trading.diary.menu.misc.SMAMenu;
import com.trading.diary.menu.misc.StrengthMenu;
import lombok.RequiredArgsConstructor;

import static com.trading.diary.utils.Helper.skipLines;

@RequiredArgsConstructor
public abstract class ResistanceBreakoutMenuAbstract<T extends ResistanceBreakout, B extends ResistanceBreakout.ResistanceBreakoutBuilder<B>> extends AbstractFormationMenu<T> {

    private final StrengthMenu strengthMenu;

    private final SMAMenu smaMenu;
    
    private final TimeLengthMenu timeLengthMenu;

    protected void showMenu(B builder) {

        print("Confirm breakout (y/n): ");
        builder.confirmBreakout(InputType.BOOLEAN.nextInput());
        skipLines(2);

        builder.breakoutVolume(strengthMenu.showMenu());
        skipLines(2);

        print("All time high (y/n): ");
        builder.allTimeHigh(InputType.BOOLEAN.nextInput());
        skipLines(2);

        print("Touches: ");
        builder.touches(InputType.INT.nextInput());
        skipLines(2);

        print("Resistance length: ");
        builder.resistanceLength(timeLengthMenu.showMenu());
        skipLines(2);

        print("Higher lows (y/n): ");
        builder.higherLows(InputType.BOOLEAN.nextInput());
        skipLines(2);

        print("RSI (float): ");
        builder.rsi(InputType.FLOAT.nextInput());
        skipLines(2);

        print("Breakout percentage (float): ");
        builder.breakoutPercentage(InputType.FLOAT.nextInput());
        skipLines(2);

        print("SMA 20:");
        builder.sma20(smaMenu.showMenu());
        skipLines(2);

        print("SMA 50:");
        builder.sma50(smaMenu.showMenu());
        skipLines(2);

        print("SMA 200:");
        builder.sma200(smaMenu.showMenu());
    }
}
