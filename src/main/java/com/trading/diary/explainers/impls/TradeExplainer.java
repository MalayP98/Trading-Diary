package com.trading.diary.explainers.impls;

import com.trading.diary.explainers.Explainer;
import com.trading.diary.formations.Formation;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.TradeState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeExplainer implements Explainer<Trade> {

    private final FormationServiceFactory<Formation> formationServiceFactory;

    private final FormationExplainer formationExplainer;

    @Override
    public String explain(Trade content) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(content.getCompany().toString())
                .append(": \n")
                .append("  Status: ")
                .append(content.getState().toString())
                .append("\n");
        sb.append("On Timeframe: ")
                .append(content.getTimeFrame().name())
                .append("\n");
        sb.append("On formation: ")
                .append("\n ")
                .append(formationExplainer.explain(getFormation(content)))
                .append("\n");
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

    private Formation getFormation(Trade trade) {
        return formationServiceFactory
                .getFormationService(trade.getFormationType())
                .getFormation(trade.getFormationId());
    }
}
