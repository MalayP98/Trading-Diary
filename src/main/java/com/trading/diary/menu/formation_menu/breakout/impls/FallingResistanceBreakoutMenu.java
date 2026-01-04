package com.trading.diary.menu.formation_menu.breakout.impls;

import com.trading.diary.formations.impls.resistance.extension.FallingResistanceBreakout;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.menu.formation_menu.breakout.AbstractResistanceBreakoutMenu;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class FallingResistanceBreakoutMenu extends AbstractResistanceBreakoutMenu<FallingResistanceBreakout, FallingResistanceBreakout.FallingResistanceBreakoutBuilder> {

    public FallingResistanceBreakoutMenu(MenuFactory menuFactory) {
        super(menuFactory);
    }

    @Override
    public FallingResistanceBreakout showMenu() {
        FallingResistanceBreakout.FallingResistanceBreakoutBuilder builder = FallingResistanceBreakout.builder();
        super.showMenu(builder);
        skipLines(2);

        System.out.println("Angle of the resistance line:");
        float angle = InputType.FLOAT.nextInput();
        builder.angle(angle);
        skipLines(2);

        System.out.println("Prior uptrend (y/n):");
        builder.priorUptrend(InputType.BOOLEAN.nextInput());
        skipLines(2);

        System.out.println("Price difference percentage:");
        float priceDiff = InputType.FLOAT.nextInput();
        builder.priceDiffPercentage(priceDiff);

        return builder.build();
    }

    @Override
    public MenuName menuName() {
        return MenuName.FALLING_RESISTANCE_BREAKOUT_MENU;
    }
}
