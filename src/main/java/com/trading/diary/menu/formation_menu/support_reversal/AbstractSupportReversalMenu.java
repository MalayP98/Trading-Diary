package com.trading.diary.menu.formation_menu.support_reversal;

import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.utils.PricePosition;
import lombok.RequiredArgsConstructor;

import static com.trading.diary.utils.Helper.skipLines;

@RequiredArgsConstructor
public abstract class AbstractSupportReversalMenu<T extends SupportReversal, B extends SupportReversal.SupportReversalBuilder<B>> extends com.trading.diary.menu.AbstractMenu<T> {

    protected final MenuFactory menuFactory;

    protected void showMenu(B builder) {
        print("Price position on support: ");
        builder.pricePositionOnSupport((PricePosition) menuFactory.getMenu(MenuName.PRICE_POSITION_MENU).showMenu());
        skipLines(2);

        print("Enter the length of the support length: ");
        builder.supportLength((long) menuFactory.getMenu(MenuName.TIME_LENGTH_MENU).showMenu());
        skipLines(2);

        print("Has the price been sustained at top of the formation? (y/n): ");
        builder.priceSustained(InputType.BOOLEAN.nextInput());
        skipLines(2);

        print("Has there been a retest of the support level? (y/n): ");
        builder.retest(InputType.BOOLEAN.nextInput());
    }
}
