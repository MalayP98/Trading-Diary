package com.trading.diary.terminalui;

import com.trading.diary.explainers.impls.TradeExplainer;
import com.trading.diary.services.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ViewOpenTradesWindow {

    private final TradeService tradeService;
    private final TradeExplainer tradeExplainer;
    private final TradeListWindow tradeListWindow;

    public void open() {
        tradeListWindow.open(
                "All Open Trades",
                tradeService::countAllActiveTrade,
                tradeService::getAllOpenTrades,
                tradeExplainer,
                trade -> trade.getId(),
                false
        );
    }
}
