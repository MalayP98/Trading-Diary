package com.trading.diary.menu.formation_menu.support_reversal.impls;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.menu.TimeLengthMenu;
import com.trading.diary.menu.formation_menu.support_reversal.SupportReversalMenuAbstract;
import com.trading.diary.menu.misc.CandleColorMenu;
import com.trading.diary.menu.misc.PricePositionMenu;
import com.trading.diary.menu.misc.StrengthMenu;
import org.springframework.stereotype.Service;

import static com.trading.diary.utils.Helper.skipLines;

@Service
public class MorningStarMenu extends SupportReversalMenuAbstract<MorningStar, MorningStar.MorningStarBuilder> {

    private final CandleColorMenu candleColorMenu;

    private final StrengthMenu strengthMenu;

    public MorningStarMenu(CandleColorMenu candleColorMenu, StrengthMenu strengthMenu, TimeLengthMenu timeLengthMenu, PricePositionMenu pricePositionMenu) {
        super(timeLengthMenu,pricePositionMenu);
        this.candleColorMenu = candleColorMenu;
        this.strengthMenu = strengthMenu;
    }

    @Override
    public MorningStar showMenu() {
        MorningStar.MorningStarBuilder builder = MorningStar.builder();
        super.showMenu(builder);
        skipLines(2);

        builder.dogiColor(candleColorMenu.showMenu());
        skipLines(2);

        builder.volume(strengthMenu.showMenu());
        skipLines(2);

        return builder.build();
    }

    @Override
    public FormationType getFormation() {
        return FormationType.MORNING_STAR;
    }
}
