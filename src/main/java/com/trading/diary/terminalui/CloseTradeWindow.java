//package com.trading.diary.terminalui;
//
//import com.googlecode.lanterna.gui2.*;
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
//public class CloseTradeWindow {
//
//    private final UiNavigator navigator;
//    private final TradeService tradeService;
//
//    public void open() {
//
//        BasicWindow window =
//                new BasicWindow("Close Trade");
//
//        Panel root = new Panel();
//
//        root.setLayoutManager(
//                new LinearLayout(Direction.VERTICAL)
//        );
//
//        Table<String> table =
//                new Table<>(
//                        "ID",
//                        "Symbol",
//                        "Avg Price",
//                        "Quantity"
//                );
//
//        List<Trade> openTrades =
//                tradeService.getOpenTrades();
//
//        openTrades.forEach(t ->
//                table.getTableModel().addRow(
//                        t.getId(),
//                        t.getSymbol(),
//                        String.valueOf(
//                                t.getAverageBuyingPrice()
//                        ),
//                        String.valueOf(t.getQuantity())
//                )
//        );
//
//        root.addComponent(table);
//
//        Button closeSelectedButton =
//                new Button("Close Selected", () -> {
//
//                    int selectedRow =
//                            table.getSelectedRow();
//
//                    Trade selectedTrade =
//                            openTrades.get(selectedRow);
//
//                    openCloseForm(selectedTrade);
//
//                    window.close();
//                });
//
//        root.addComponent(closeSelectedButton);
//
//        window.setComponent(root);
//
//        navigator.show(window);
//    }
//
//    private void openCloseForm(Trade trade) {
//
//        BasicWindow window =
//                new BasicWindow("Close Trade");
//
//        Panel panel =
//                new Panel(new GridLayout(2));
//
//        TextBox closePriceBox = new TextBox();
//
//        TextBox closeDateBox =
//                new TextBox(
//                        LocalDate.now().toString()
//                );
//
//        panel.addComponent(new Label("Close Price"));
//        panel.addComponent(closePriceBox);
//
//        panel.addComponent(new Label("Close Date"));
//        panel.addComponent(closeDateBox);
//
//        Button closeButton =
//                new Button("Confirm Close", () -> {
//
//                    tradeService.closeTrade(
//                            trade.getId(),
//                            Double.parseDouble(
//                                    closePriceBox.getText()
//                            ),
//                            LocalDate.parse(
//                                    closeDateBox.getText()
//                            )
//                    );
//
//                    window.close();
//                });
//
//        panel.addComponent(
//                closeButton,
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
//        window.setComponent(panel);
//
//        navigator.show(window);
//    }
//}