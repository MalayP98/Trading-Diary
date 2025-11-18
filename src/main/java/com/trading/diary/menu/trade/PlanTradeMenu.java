package com.trading.diary.menu.trade;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.impls.UnexpectedMove;
import com.trading.diary.menu.*;
import com.trading.diary.menu.formation_menu.FormationMenuFactory;
import com.trading.diary.menu.targetMenu.StoplossMenu;
import com.trading.diary.menu.targetMenu.TargetMenu;
import com.trading.diary.pojo.Audit;
import com.trading.diary.services.formation.FormationService;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.AbstractTrade;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.trade.impls.PlannedTrade.PlannedTradeBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.trading.diary.utils.Helper.skipLines;

@Service
@RequiredArgsConstructor
public class PlanTradeMenu<T extends AbstractTrade, B extends AbstractTrade.AbstractTradeBuilder<B, T>> extends AbstractMenu<T> {

    private final TimeFrameMenu timeFrameMenu;

    private final MarketCapMenu marketCapMenu;

    private final PersonMenu personMenu;

    private final TargetMenu targetMenu;

    private final StoplossMenu stoplossMenu;

    private final CompanyMenu companyMenu;

    private final FormationMenuFactory formationMenuFactory;

    private final FormationServiceFactory<? super Formation> formationServiceFactory;

    @Override
    public T showMenu(){
        PlannedTradeBuilder builder = PlannedTrade.builder();
        System.out.println("=== Plan a New Trade ===");
        buildPlannedTrade((B) builder);
        return (T) builder.build();
    }

    protected void buildPlannedTrade(B builder){
        builder.company(companyMenu.showMenu());
        skipLines(2);

        builder.timeFrame(timeFrameMenu.showMenu());
        skipLines(2);

        Formation formation = formationMenuFactory
                .showMenu()
                .showMenu();
        formation = formationServiceFactory
                .getFormationService(formation.getFormation())
                        .save(formation);
        Audit audit = (Audit) formation;
        builder.formationId(audit.getId());

        builder.marketCap(marketCapMenu.showMenu());
        skipLines(2);

        builder.suggestedBy(personMenu.showMenu());
        skipLines(2);

        System.out.print("Any notes? (press Enter to skip): ");
        String notes = nextString();
        builder.notes(notes);
        skipLines(2);

        builder.addTarget(targetMenu.showMenu());
        skipLines(2);

        builder.addStoploss(stoplossMenu.showMenu());
    }
}
