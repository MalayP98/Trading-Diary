package com.trading.diary.explainers.impls;

import com.trading.diary.explainers.Explainer;
import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.TradeState;
import org.springframework.stereotype.Service;

@Service
public class TradeExplainer implements Explainer<Trade> {

    @Override
    public String explain(Trade content) {
        StringBuilder sb = new StringBuilder();
        sb.append(content.getCompany().toString())
                .append(": \n")
                .append("\t Status: ")
                .append(content.getState().toString())
                .append("\n");
        sb.append("\t On Timeframe: ")
                .append(content.getTimeFrame().name())
                .append("\n");
        sb.append("\t On formation: ")
                .append(content.getFormationType().name())
                .append("\n");
        sb.append("\t Market Cap: ")
                .append(content.getMarketCap())
                .append("\n");
        sb.append("\t Entered @")
                .append(content.getAverageBuyingPrice())
                .append(" on ")
                .append(content.getOpeningDate()).append("\n");
        if (TradeState.CLOSE.equals(content.getState())) {
            sb.append("\t Closed @")
                    .append(content.getAverageClosingPrice())
                    .append(" on ")
                    .append(content.getClosingDate())
                    .append("\n");
        }
        return sb.toString();
    }
}
