package com.trading.diary.menu.formation_menu.breakout;

import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.helpers.SMA;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.utils.Strength;
import lombok.RequiredArgsConstructor;

import static com.trading.diary.utils.Helper.skipLines;

@RequiredArgsConstructor
public abstract class AbstractResistanceBreakoutMenu<T extends ResistanceBreakout, B extends ResistanceBreakout.ResistanceBreakoutBuilder<B>> extends com.trading.diary.menu.AbstractMenu<T> {

    protected final MenuFactory menuFactory;

    protected void showMenu(B builder) {

        print("Confirm breakout (y/n): ");
        builder.confirmBreakout(InputType.BOOLEAN.nextInput());
        skipLines(2);

        builder.breakoutVolume((Strength) menuFactory.getMenu(MenuName.STRENGTH_MENU).showMenu());
        skipLines(2);

        print("All time high (y/n): ");
        builder.allTimeHigh(InputType.BOOLEAN.nextInput());
        skipLines(2);

        print("Touches: ");
        builder.touches(InputType.INT.nextInput());
        skipLines(2);

        print("Resistance length: ");
        builder.resistanceLength((long) menuFactory.getMenu(MenuName.TIME_LENGTH_MENU).showMenu());
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
        builder.sma20((SMA) menuFactory.getMenu(MenuName.SMA_MENU).showMenu());
        skipLines(2);

        print("SMA 50:");
        builder.sma50((SMA) menuFactory.getMenu(MenuName.SMA_MENU).showMenu());
        skipLines(2);

        print("SMA 200:");
        builder.sma200((SMA) menuFactory.getMenu(MenuName.SMA_MENU).showMenu());
    }
}
