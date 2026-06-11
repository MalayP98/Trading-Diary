package com.trading.diary.explainers.impls;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.score.FormationEvaluationFacade;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.impls.PlannedTrade;
import org.springframework.stereotype.Service;

/**
 * Builds summaries and detailed views for planned trades, including the resolved formation details and normalized formation score when the underlying formation is still available.
 */
@Service
public class PlannedTradeExplainer extends AbstractTradeExplainer<PlannedTrade> {

    public PlannedTradeExplainer(FormationServiceFactory<Formation> formationServiceFactory,
                                 FormationExplainer formationExplainer,
                                 FormationEvaluationFacade formationEvaluationFacade) {
        super(formationServiceFactory, formationExplainer, formationEvaluationFacade);
    }

    @Override
    public String summarize(PlannedTrade item) {
        Formation formation = getFormation(item);
        return item.getCompany() + " | " + item.getFormationType() + " | Score: " + buildScore(formation);
    }

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
}
