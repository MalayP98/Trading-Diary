package com.trading.diary.explainers.impls;

import com.trading.diary.explainers.Explainer;
import com.trading.diary.formations.Formation;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.impls.PlannedTrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlannedTradeExplainer implements Explainer<PlannedTrade> {

    private final FormationServiceFactory<Formation> formationServiceFactory;

    private final FormationExplainer formationExplainer;

    @Override
    public String explain(PlannedTrade content) {
        Formation formation = getFormation(content);
        String formationInfo = formation != null
                ? formationExplainer.explain(formation)
                : "(formation not found)";
        return "\nCompany : " + content.getCompany().toString() +
                "\n" +
                "On formation: " +
                "\n" +
                formationInfo +
                "\n";
    }

    private Formation getFormation(PlannedTrade plannedTrade) {
        return formationServiceFactory
                .getFormationService(plannedTrade.getFormationType())
                .getFormation(plannedTrade.getFormationId());
    }
}
