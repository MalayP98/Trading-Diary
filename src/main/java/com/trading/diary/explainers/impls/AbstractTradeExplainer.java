package com.trading.diary.explainers.impls;

import com.trading.diary.explainers.Explainer;
import com.trading.diary.formations.Formation;
import com.trading.diary.formations.score.FormationEvaluationFacade;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.AbstractTrade;
import lombok.RequiredArgsConstructor;

/**
 * Base class for trade explainers. Centralises the shared dependencies and common helpers
 * (formation lookup and score formatting) so subclasses only implement their own explain/summarize logic.
 */
@RequiredArgsConstructor
public abstract class AbstractTradeExplainer<T extends AbstractTrade> implements Explainer<T> {

    protected final FormationServiceFactory<Formation> formationServiceFactory;
    protected final FormationExplainer formationExplainer;
    protected final FormationEvaluationFacade formationEvaluationFacade;

    protected Formation getFormation(AbstractTrade trade) {
        return formationServiceFactory
                .getFormationService(trade.getFormationType())
                .getFormation(trade.getFormationId());
    }

    protected String buildScore(Formation formation) {
        return formation != null
                ? String.format("%.1f", formationEvaluationFacade.evaluate(formation)) + "%"
                : "N/A";
    }
}
