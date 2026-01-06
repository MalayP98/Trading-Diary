package com.trading.diary.explainers.impls;

import com.trading.diary.explainers.Explainer;
import com.trading.diary.trade.impls.PlannedTrade;
import org.springframework.stereotype.Service;

@Service
public class PlannedTradeExplainer implements Explainer<PlannedTrade> {

    @Override
    public String explain(PlannedTrade content) {
        StringBuilder sb = new StringBuilder();
        sb.append(content.getCompany().toString())
                .append(": \n");
        sb.append("\t On Timeframe: ")
                .append(content.getTimeFrame().name())
                .append("\n");
        sb.append("\t On formation: ")
                .append(content.getFormationType().name())
                .append("\n");
        return sb.toString();
    }
}
