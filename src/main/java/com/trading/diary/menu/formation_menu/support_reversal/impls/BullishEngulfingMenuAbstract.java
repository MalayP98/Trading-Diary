package com.trading.diary.menu.formation_menu.support_reversal.impls;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.menu.misc.StrengthMenu;
import com.trading.diary.menu.formation_menu.support_reversal.SupportReversalMenuAbstract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
@RequiredArgsConstructor
public class BullishEngulfingMenuAbstract extends SupportReversalMenuAbstract<BullishEngulfing ,BullishEngulfing.BullishEngulfingBuilder> {

    private final StrengthMenu strengthMenu;

    @Override
    public BullishEngulfing showMenu() {
        BullishEngulfing.BullishEngulfingBuilder builder = BullishEngulfing.builder();
        super.showMenu(builder);
        skipLines(2);

        System.out.print("Is there partial bottom engulfing? (y/n): ");
        builder.partialBottomEngulfing(nextBoolean());
        skipLines(2);

        System.out.print("Is there partial top engulfing? (y/n): ");
        builder.partialTopEngulfing(nextBoolean());
        skipLines(2);

        builder.volume(strengthMenu.showMenu());
        skipLines(2);

        return builder.build();
    }

    @Override
    public FormationType getFormation() {
        return FormationType.BULLISH_ENGULFING;
    }
}
