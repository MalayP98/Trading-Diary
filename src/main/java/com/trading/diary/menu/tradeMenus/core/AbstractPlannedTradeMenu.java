package com.trading.diary.menu.tradeMenus.core;

import com.trading.diary.formations.Formation;
import com.trading.diary.helpers.Target;
import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.pojo.Audit;
import com.trading.diary.pojo.Company;
import com.trading.diary.pojo.MarketCap;
import com.trading.diary.pojo.Person;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.AbstractTrade;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.utils.TimeFrame;

import java.util.List;

import static com.trading.diary.utils.Helper.skipLines;

public abstract class AbstractPlannedTradeMenu<T extends AbstractTrade> extends AbstractMenu<T> {

    private final FormationServiceFactory<? super Formation> formationServiceFactory;

    private final MenuFactory menuFactory;

    protected AbstractPlannedTradeMenu(FormationServiceFactory<? super Formation> formationServiceFactory, MenuFactory menuFactory) {
        this.formationServiceFactory = formationServiceFactory;
        this.menuFactory = menuFactory;
    }

    @Override
    public T showMenu(){
        return buildWithPlannedTrade(buildPlannedTrade(PlannedTrade.builder()));
    }

    @SuppressWarnings("unchecked")
    protected PlannedTrade buildPlannedTrade(PlannedTrade.PlannedTradeBuilder builder){
        System.out.println("=== Plan a New Trade ===");
        builder.company((Company) menuFactory.getMenu(MenuName.COMPANY_MENU).showMenu());
        skipLines(2);

        builder.timeFrame((TimeFrame) menuFactory.getMenu(MenuName.TIMEFRAME_MENU).showMenu());
        skipLines(2);

        AbstractMenu<? extends Formation> formationMenu = (AbstractMenu<? extends Formation>) menuFactory.getMenu(MenuName.FORMATION_SELECTION_MENU).showMenu();
        skipLines(2);

        Formation formation = formationMenu.showMenu();
        skipLines(2);

        formation = formationServiceFactory
                .getFormationService(formation.getFormation())
                .save(formation);
        Audit audit = (Audit) formation;
        builder.formationId(audit.getId());

        builder.formationType(formation.getFormation());

        builder.marketCap((MarketCap) menuFactory.getMenu(MenuName.MARKET_CAP_MENU).showMenu());
        skipLines(2);

        builder.suggestedBy((Person) menuFactory.getMenu(MenuName.PERSON_MENU).showMenu());
        skipLines(2);

        print("Any notes? (press Enter to skip): ");
        String notes = InputType.STRING.nextSkipableInput();
        builder.notes(notes);
        skipLines(2);

        builder.addTarget((List<Target>) menuFactory.getMenu(MenuName.TARGET_MENU).showMenu());
        skipLines(2);

        builder.addStoploss((List<Target>) menuFactory.getMenu(MenuName.STOPLOSS_MENU).showMenu());

        return builder.build();
    }

    public abstract T buildWithPlannedTrade(PlannedTrade plannedTrade);
}
