package com.trading.diary.menu.formation_menu.support_reversal.impls;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.menu.TimeLengthMenu;
import com.trading.diary.menu.formation_menu.support_reversal.SupportReversalMenuAbstract;
import com.trading.diary.menu.misc.CandleColorMenu;
import com.trading.diary.menu.misc.PricePositionMenu;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class HammerMenu extends SupportReversalMenuAbstract<Hammer, Hammer.HammerBuilder> {

    private final CandleColorMenu candleColorMenu;

    public HammerMenu(CandleColorMenu candleColorMenu, TimeLengthMenu timeLengthMenu, PricePositionMenu pricePositionMenu) {
        super(timeLengthMenu, pricePositionMenu);
        this.candleColorMenu = candleColorMenu;
    }


    @Override
    public Hammer showMenu() {
        Hammer.HammerBuilder builder = Hammer.builder();
        super.showMenu(builder);
        skipLines(2);

        builder.hammerColor(candleColorMenu.showMenu());
        skipLines(2);

        System.out.print("Is the lower wick less than 2x of upper wick? (y/n): ");
        builder.smallLowerWick(InputType.BOOLEAN.nextInput());
        skipLines(2);

        return builder.build();
    }

    @Override
    public FormationType getFormation() {
        return FormationType.HAMMER;
    }
}
