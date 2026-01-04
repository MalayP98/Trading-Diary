package com.trading.diary.explainers.impls;

import com.trading.diary.explainers.Explainer;
import com.trading.diary.trade.impls.PlannedTrade;
import org.springframework.stereotype.Service;

@Service
public class PlannedTradeExplainer implements Explainer<PlannedTrade> {

    @Override
    public String explain(PlannedTrade content) {
        String sb = content.getCompany().toString() +
                ": \n" +
                "\t Timeframe: " +
                content.getTimeFrame().name() +
                "\n" +
                "\t Market cap: " +
                content.getMarketCap() +
                "\n" +
                "\t On formation: " +
                content.getFormationType().name() +
                "\n" +
                "\t Suggested by: " +
                content.getSuggestedBy().getName() +
                "\n";
        return sb;
    }
}
