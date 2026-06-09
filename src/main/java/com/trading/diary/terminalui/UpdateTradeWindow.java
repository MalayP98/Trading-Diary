package com.trading.diary.terminalui;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.trading.diary.explainers.impls.TradeExplainer;
import com.trading.diary.services.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Lanterna workflow for amending an open trade without closing it, including share changes and note appends.
 */
@Component
@RequiredArgsConstructor
public class UpdateTradeWindow {

    private final UiNavigator navigator;
    private final TradeService tradeService;
    private final TradeExplainer tradeExplainer;
    private final TradeListWindow tradeListWindow;

    /**
     * Lets the user pick an open trade and amend quantities, average price, or notes without closing the position.
     */
    public void open() {
        Long selectedId = tradeListWindow.open(
                "Select Open Trade to Update",
                tradeService::countAllActiveTrade,
                tradeService::getAllOpenTrades,
                tradeExplainer,
                trade -> trade.getId(),
                true
        );

        if (selectedId == null) return;

        BasicWindow window = new BasicWindow("Update Trade");
        Panel panel = new Panel(new GridLayout(2));

        TextBox sharesBox = new TextBox();
        TextBox avgBuyPriceBox = new TextBox();
        TextBox notesBox = new TextBox();

        panel.addComponent(new Label("New Shares (leave blank to keep)"));
        panel.addComponent(sharesBox);
        panel.addComponent(new Label("New Avg Buying Price (leave blank to keep)"));
        panel.addComponent(avgBuyPriceBox);
        panel.addComponent(new Label("Append Notes (optional)"));
        panel.addComponent(notesBox);

        panel.addComponent(
                new Button("Update", () -> {
                    try {
                        int shares = sharesBox.getText().isBlank() ? 0 : Integer.parseInt(sharesBox.getText().trim());
                        float avgPrice = avgBuyPriceBox.getText().isBlank() ? 0f : Float.parseFloat(avgBuyPriceBox.getText().trim());
                        tradeService.updateTrade(selectedId, shares, avgPrice, notesBox.getText());
                        MessageDialog.showMessageDialog(navigator.getGui(), "Success", "Trade updated.");
                        window.close();
                    } catch (Exception e) {
                        MessageDialog.showMessageDialog(navigator.getGui(), "Error", "Failed to update: " + e.getMessage());
                    }
                }),
                GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER, true, false, 2, 1)
        );

        panel.addComponent(
                new Button("Cancel", window::close),
                GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER, true, false, 2, 1)
        );

        window.setComponent(panel);
        navigator.show(window);
    }
}
