package com.trading.diary.terminalui;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.trading.diary.explainers.impls.TradeExplainer;
import com.trading.diary.pojo.dto.CloseTradeDTO;
import com.trading.diary.services.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Lanterna workflow for selecting an open trade and capturing the information needed to close it.
 */
@Component
@RequiredArgsConstructor
public class CloseTradeWindow {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final UiNavigator navigator;
    private final TradeService tradeService;
    private final TradeExplainer tradeExplainer;
    private final TradeListWindow tradeListWindow;

    /**
     * Guides the user through selecting an open trade and entering the close details required to finalize it.
     */
    public void open() {
        Long selectedId = tradeListWindow.open(
                "Select Open Trade to Close",
                tradeService::countAllActiveTrade,
                tradeService::getAllOpenTrades,
                tradeExplainer,
                trade -> trade.getId(),
                true
        );

        if (selectedId == null) return;

        BasicWindow window = new BasicWindow("Close Trade");
        Panel panel = new Panel(new GridLayout(2));

        TextBox closingPriceBox = new TextBox();
        TextBox closingDateBox = new TextBox("dd-MM-yyyy");

        panel.addComponent(new Label("Closing Price"));
        panel.addComponent(closingPriceBox);
        panel.addComponent(new Label("Closing Date (dd-MM-yyyy)"));
        panel.addComponent(closingDateBox);

        panel.addComponent(
                new Button("Close Trade", () -> {
                    try {
                        CloseTradeDTO dto = new CloseTradeDTO(
                                selectedId,
                                Float.parseFloat(closingPriceBox.getText().trim()),
                                LocalDate.parse(closingDateBox.getText().trim(), DATE_FORMATTER).atStartOfDay()
                        );
                        tradeService.closeTrade(dto);
                        MessageDialog.showMessageDialog(navigator.getGui(), "Success", "Trade closed successfully.");
                        window.close();
                    } catch (Exception e) {
                        MessageDialog.showMessageDialog(navigator.getGui(), "Error", "Failed to close trade: " + e.getMessage());
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
