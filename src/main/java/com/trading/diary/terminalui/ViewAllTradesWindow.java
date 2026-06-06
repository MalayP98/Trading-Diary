package com.trading.diary.terminalui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.trading.diary.explainers.impls.PlannedTradeExplainer;
import com.trading.diary.explainers.impls.TradeExplainer;
import com.trading.diary.pojo.Audit;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.services.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class ViewAllTradesWindow {

    private final UiNavigator navigator;
    private final TradeService tradeService;
    private final PlannedTradeService plannedTradeService;
    private final TradeExplainer tradeExplainer;
    private final PlannedTradeExplainer plannedTradeExplainer;
    private final TradeListWindow tradeListWindow;

    public void open() {
        AtomicBoolean running = new AtomicBoolean(true);

        while (running.get()) {
            BasicWindow window = new BasicWindow("View All Trades");
            Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

            panel.addComponent(new Label("View All Trades"));
            panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

            panel.addComponent(new Button("1. Regular Trades (Open + Closed)", () -> {
                window.close();
                tradeListWindow.open(
                        "All Regular Trades",
                        tradeService::countAllTrade,
                        tradeService::getAllTrades,
                        tradeExplainer,
                        Audit::getId,
                        false
                );
            }));

            panel.addComponent(new Button("2. Planned Trades", () -> {
                window.close();
                tradeListWindow.open(
                        "All Planned Trades",
                        plannedTradeService::getCount,
                        plannedTradeService::getAllPlannedTrade,
                        plannedTradeExplainer,
                        Audit::getId,
                        false
                );
            }));

            panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
            panel.addComponent(new Button("Back", () -> {
                running.set(false);
                window.close();
            }));

            window.setComponent(panel);
            navigator.show(window);
        }
    }
}

