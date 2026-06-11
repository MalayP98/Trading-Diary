package com.trading.diary.explainers.impls;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.score.FormationEvaluationFacade;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.emums.TradeState;
import org.springframework.stereotype.Service;

/**
 * Builds terminal-friendly summaries for live and closed trades, combining trade state, pricing information, and the resolved formation explanation.
 */
@Service
public class TradeExplainer extends AbstractTradeExplainer<Trade> {

    public TradeExplainer(FormationServiceFactory<Formation> formationServiceFactory,
                          FormationExplainer formationExplainer,
                          FormationEvaluationFacade formationEvaluationFacade) {
        super(formationServiceFactory, formationExplainer, formationEvaluationFacade);
    }

    @Override
    public String summarize(Trade item) {
        Formation formation = getFormation(item);
        return item.getCompany() + " | " + item.getState() + " | " + item.getFormationType() + " | Score: " + buildScore(formation);
    }

    @Override
    public String explain(Trade content) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(content.getCompany().toString())
                .append(": \n")
                .append("  Status: ")
                .append(content.getState().toString())
                .append("\n");
        Formation formation = getFormation(content);
        if (formation != null) {
            sb.append("On formation: ")
                    .append("\n ")
                    .append(formationExplainer.explain(formation))
                    .append("\n");
        } else {
            sb.append("On formation: (not found)\n");
        }
        sb.append("Market Cap: ")
                .append(content.getMarketCap())
                .append("\n");
        sb.append("Entered @")
                .append(content.getAverageBuyingPrice())
                .append(" on ")
                .append(content.getOpeningDate()).append("\n");
        if (TradeState.CLOSE.equals(content.getState())) {
            sb.append("Closed @")
                    .append(content.getAverageClosingPrice())
                    .append(" on ")
                    .append(content.getClosingDate())
                    .append("\n");
        }
        return sb.toString();
    }
}
