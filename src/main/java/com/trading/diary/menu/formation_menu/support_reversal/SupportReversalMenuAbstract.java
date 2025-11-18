package com.trading.diary.menu.formation_menu.support_reversal;

import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.menu.formation_menu.AbstractFormationMenu;

import static com.trading.diary.utils.Helper.skipLines;

public abstract class SupportReversalMenuAbstract<T extends SupportReversal, B extends SupportReversal.SupportReversalBuilder<B>> extends AbstractFormationMenu<T> {

    protected void showMenu(B builder) {
        System.out.print("Is the price above the support level? (y/n): ");
        builder.aboveSupport(nextBoolean());
        skipLines(2);

        System.out.print("Is the price below the support level? (y/n): ");
        builder.belowSupport(nextBoolean());
        skipLines(2);

        System.out.print("Enter the length of the support level (in days): ");
        builder.supportLength(nextInt());
        skipLines(2);

        System.out.print("Has the price been sustained at the support level? (y/n): ");
        builder.priceSustained(nextBoolean());
        skipLines(2);

        System.out.print("Has there been a retest of the support level? (y/n): ");
        builder.retest(nextBoolean());
        skipLines(2);
    }
}
