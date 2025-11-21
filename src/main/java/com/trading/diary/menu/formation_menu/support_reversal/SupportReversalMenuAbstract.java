package com.trading.diary.menu.formation_menu.support_reversal;

import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.menu.TimeLengthMenu;
import com.trading.diary.menu.formation_menu.AbstractFormationMenu;
import com.trading.diary.menu.misc.PricePositionMenu;
import lombok.RequiredArgsConstructor;

import static com.trading.diary.utils.Helper.skipLines;

@RequiredArgsConstructor
public abstract class SupportReversalMenuAbstract<T extends SupportReversal, B extends SupportReversal.SupportReversalBuilder<B>> extends AbstractFormationMenu<T> {

    private final TimeLengthMenu timeLengthMenu;

    private final PricePositionMenu pricePositionMenu;

    protected void showMenu(B builder) {
        print("Price position on support: ");
        builder.pricePositionOnSupport(pricePositionMenu.showMenu());
        skipLines(2);

        print("Enter the length of the support length: ");
        builder.supportLength(timeLengthMenu.showMenu());
        skipLines(2);

        print("Has the price been sustained at top of the formation? (y/n): ");
        builder.priceSustained(InputType.BOOLEAN.nextInput());
        skipLines(2);

        print("Has there been a retest of the support level? (y/n): ");
        builder.retest(InputType.BOOLEAN.nextInput());
    }
}
