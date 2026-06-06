package com.trading.diary.terminalui;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.trading.diary.explainers.impls.PlannedTradeExplainer;
import com.trading.diary.pojo.dto.PlannedTradeConfirmationDTO;
import com.trading.diary.services.PlannedTradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ConfirmPlannedTradeWindow {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final UiNavigator navigator;
    private final PlannedTradeService plannedTradeService;
    private final PlannedTradeExplainer plannedTradeExplainer;
    private final TradeListWindow tradeListWindow;

    public void open() {
        Long selectedId = tradeListWindow.open(
                "Select Planned Trade to Confirm",
                plannedTradeService::getCount,
                plannedTradeService::getAllPlannedTrade,
                plannedTradeExplainer,
                pt -> pt.getId(),
                true
        );

        if (selectedId == null) return;

        BasicWindow window = new BasicWindow("Confirm Planned Trade");
        Panel panel = new Panel(new GridLayout(2));

        TextBox buyingPriceBox = new TextBox();
        TextBox quantityBox = new TextBox();
        TextBox openingDateBox = new TextBox("dd-MM-yyyy");

        panel.addComponent(new Label("Buying Price"));
        panel.addComponent(buyingPriceBox);
        panel.addComponent(new Label("Quantity"));
        panel.addComponent(quantityBox);
        panel.addComponent(new Label("Opening Date (dd-MM-yyyy)"));
        panel.addComponent(openingDateBox);

        panel.addComponent(
                new Button("Confirm", () -> {
                    try {
                        PlannedTradeConfirmationDTO dto = new PlannedTradeConfirmationDTO(
                                selectedId,
                                Float.parseFloat(buyingPriceBox.getText().trim()),
                                Integer.parseInt(quantityBox.getText().trim()),
                                LocalDate.parse(openingDateBox.getText().trim(), DATE_FORMATTER).atStartOfDay()
                        );
                        plannedTradeService.confirmPlannedTrade(dto);
                        MessageDialog.showMessageDialog(navigator.getGui(), "Success", "Planned trade confirmed.");
                        window.close();
                    } catch (Exception e) {
                        MessageDialog.showMessageDialog(navigator.getGui(), "Error", "Failed to confirm: " + e.getMessage());
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
