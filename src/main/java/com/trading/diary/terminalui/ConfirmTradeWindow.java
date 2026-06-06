//package com.trading.diary.terminalui;
//
//import com.googlecode.lanterna.TerminalSize;
//import com.googlecode.lanterna.gui2.*;
//import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
//import com.googlecode.lanterna.gui2.table.Table;
//import com.trading.diary.services.TradeService;
//import com.trading.diary.trade.impls.Trade;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class ConfirmTradeWindow {
//
//    private final UiNavigator navigator;
//    private final TradeService tradeService;
//
//    public void open() {
//
//        // =========================
//        // Main Window
//        // =========================
//        BasicWindow window =
//                new BasicWindow("Confirm Planned Trade");
//
//        Panel root = new Panel();
//
//        root.setLayoutManager(
//                new LinearLayout(Direction.VERTICAL)
//        );
//
//        // =========================
//        // Title
//        // =========================
//        root.addComponent(
//                new Label("Planned Trades")
//        );
//
//        root.addComponent(
//                new EmptySpace(new TerminalSize(0, 1))
//        );
//
//        // =========================
//        // Planned Trades Table
//        // =========================
//        Table<String> table =
//                new Table<>(
//                        "ID",
//                        "Symbol",
//                        "Entry"
//                );
//
//        List<Trade> plannedTrades =
//                tradeService.getPlannedTrades();
//
//        plannedTrades.forEach(trade ->
//
//                table.getTableModel().addRow(
//                        trade.getId(),
//                        trade.getSymbol(),
//                        String.valueOf(
//                                trade.getPlannedEntry()
//                        )
//                )
//        );
//
//        root.addComponent(table);
//
//        root.addComponent(
//                new EmptySpace(new TerminalSize(0, 1))
//        );
//
//        // =========================
//        // Confirm Button
//        // =========================
//        Button confirmButton =
//                new Button("Confirm Selected", () -> {
//
//                    int selectedRow =
//                            table.getSelectedRow();
//
//                    if (selectedRow < 0) {
//                        return;
//                    }
//
//                    Trade selectedTrade =
//                            plannedTrades.get(selectedRow);
//
//                    openConfirmForm(selectedTrade);
//
//                    window.close();
//                });
//
//        root.addComponent(confirmButton);
//
//        // =========================
//        // Final Setup
//        // =========================
//        window.setComponent(root);
//
//        navigator.show(window);
//    }
//
//    private void openConfirmForm(Trade trade) {
//
//        // =========================
//        // Confirm Form Window
//        // =========================
//        BasicWindow window =
//                new BasicWindow("Open Trade");
//
//        Panel panel =
//                new Panel(new GridLayout(2));
//
//        // =========================
//        // Readonly Planned Details
//        // =========================
//        panel.addComponent(
//                new Label("Symbol")
//        );
//
//        panel.addComponent(
//                new Label(trade.getSymbol())
//        );
//
//        panel.addComponent(
//                new Label("Planned Entry")
//        );
//
//        panel.addComponent(
//                new Label(
//                        String.valueOf(
//                                trade.getPlannedEntry()
//                        )
//                )
//        );
//
//        // =========================
//        // Editable Fields
//        // =========================
//        TextBox quantityBox =
//                new TextBox();
//
//        TextBox openPriceBox =
//                new TextBox(
//                        String.valueOf(
//                                trade.getPlannedEntry()
//                        )
//                );
//
//        TextBox openDateBox =
//                new TextBox(
//                        LocalDate.now().toString()
//                );
//
//        panel.addComponent(
//                new Label("Quantity")
//        );
//        panel.addComponent(quantityBox);
//
//        panel.addComponent(
//                new Label("Open Price")
//        );
//        panel.addComponent(openPriceBox);
//
//        panel.addComponent(
//                new Label("Open Date")
//        );
//        panel.addComponent(openDateBox);
//
//        // =========================
//        // Open Trade Button
//        // =========================
//        Button openTradeButton =
//                new Button("Open Trade", () -> {
//
//                    try {
//
//                        int quantity =
//                                Integer.parseInt(
//                                        quantityBox.getText()
//                                );
//
//                        double openPrice =
//                                Double.parseDouble(
//                                        openPriceBox.getText()
//                                );
//
//                        LocalDate openDate =
//                                LocalDate.parse(
//                                        openDateBox.getText()
//                                );
//
//                        tradeService.confirmTrade(
//                                trade.getId(),
//                                quantity,
//                                openPrice,
//                                openDate
//                        );
//
//                        MessageDialog.showMessageDialog(
//                                navigator.getGui(),
//                                "Success",
//                                "Trade Opened Successfully"
//                        );
//
//                        window.close();
//
//                    } catch (Exception ex) {
//
//                        MessageDialog.showMessageDialog(
//                                navigator.getGui(),
//                                "Error",
//                                "Invalid Input"
//                        );
//                    }
//                });
//
//        panel.addComponent(
//                openTradeButton,
//                GridLayout.createLayoutData(
//                        GridLayout.Alignment.CENTER,
//                        GridLayout.Alignment.CENTER,
//                        true,
//                        false,
//                        2,
//                        1
//                )
//        );
//
//        // =========================
//        // Final Setup
//        // =========================
//        window.setComponent(panel);
//
//        navigator.show(window);
//    }
//}