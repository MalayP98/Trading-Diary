package com.trading.diary.terminalui;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import com.trading.diary.helpers.Target;
import com.trading.diary.pojo.Person;
import com.trading.diary.services.CompanyService;
import com.trading.diary.services.TradeService;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.terminalui.components.MarketCapPanel;
import com.trading.diary.terminalui.formations.FormationWindowFactory;
import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.emums.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class LogTradeWindow {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final UiNavigator navigator;
    private final TradeService tradeService;
    private final CompanyService companyService;
    private final FormationWindowFactory formationWindowFactory;
    private final FormationServiceFactory formationServiceFactory;

    public void open() {
        BasicWindow window = new BasicWindow("Log Trade");
        Panel panel = new Panel(new GridLayout(2));

        TextBox symbolBox = new TextBox();
        TextBox sharesBox = new TextBox();
        TextBox avgBuyPriceBox = new TextBox();
        TextBox openingDateBox = new TextBox("dd-MM-yyyy");
        TextBox targetBox = new TextBox();
        TextBox stoplossBox = new TextBox();
        TextBox notesBox = new TextBox();
        TextBox suggestedByBox = new TextBox();
        MarketCapPanel marketCapPanel = new MarketCapPanel();
        ComboBox<FormationType> formationTypeCombo = new ComboBox<>(FormationType.values());

        AtomicReference<Formation> formationRef = new AtomicReference<>();
        Label formationStatusLabel = new Label("[ Not configured ]");

        panel.addComponent(new Label("Symbol"));
        panel.addComponent(symbolBox);
        panel.addComponent(new Label("Shares"));
        panel.addComponent(sharesBox);
        panel.addComponent(new Label("Avg Buying Price"));
        panel.addComponent(avgBuyPriceBox);
        panel.addComponent(new Label("Opening Date (dd-MM-yyyy)"));
        panel.addComponent(openingDateBox);
        panel.addComponent(new Label("Targets (comma separated)"));
        panel.addComponent(targetBox);
        panel.addComponent(new Label("Stoploss (comma separated)"));
        panel.addComponent(stoplossBox);
        panel.addComponent(new Label("Notes (optional)"));
        panel.addComponent(notesBox);
        marketCapPanel.addTo(panel);
        panel.addComponent(new Label("Formation Type"));
        panel.addComponent(formationTypeCombo);
        panel.addComponent(new Label("Formation Status"));
        panel.addComponent(formationStatusLabel);
        panel.addComponent(new Label("Suggested By"));
        panel.addComponent(suggestedByBox);

        panel.addComponent(
                new Button("Set Formation", () -> {
                    Formation formation = formationWindowFactory
                            .getWindow(formationTypeCombo.getSelectedItem())
                            .open();
                    if (formation != null) {
                        formationRef.set(formation);
                        formationStatusLabel.setText("[ " + formationTypeCombo.getSelectedItem() + " configured ]");
                    }
                }),
                GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER, true, false, 2, 1)
        );

        panel.addComponent(
                new Button("Save Trade", () -> {
                    try {
                        if (formationRef.get() == null) {
                            MessageDialog.showMessageDialog(navigator.getGui(), "Error", "Please configure the formation first.");
                            return;
                        }
                        Formation savedFormation = (Formation) formationServiceFactory
                                .getFormationService(formationTypeCombo.getSelectedItem())
                                .save(formationRef.get());
                        long formationId = ((com.trading.diary.pojo.Audit) savedFormation).getId();

                        Trade trade = Trade.builder()
                                .company(companyService.getOrCreateCompany(symbolBox.getText().trim()))
                                .suggestedBy(Person.self())
                                .shares(Integer.parseInt(sharesBox.getText().trim()))
                                .averageBuyingPrice(Float.parseFloat(avgBuyPriceBox.getText().trim()))
                                .openingDate(LocalDate.parse(openingDateBox.getText().trim(), DATE_FORMATTER).atStartOfDay())
                                .addTarget(parseTargets(targetBox.getText(), TargetType.TARGET))
                                .addStoploss(parseTargets(stoplossBox.getText(), TargetType.STOPLOSS))
                                .notes(notesBox.getText())
                                .marketCap(marketCapPanel.getMarketCap())
                                .formationType(formationTypeCombo.getSelectedItem())
                                .formationId(formationId)
                                .suggestedBy(new Person(suggestedByBox.getText().trim()))
                                .build();

                        tradeService.addTrade(trade);
                        MessageDialog.showMessageDialog(navigator.getGui(), "Success", "Trade logged successfully.");
                        window.close();
                    } catch (Exception e) {
                        MessageDialog.showMessageDialog(navigator.getGui(), "Error", "Failed to save: " + e.getMessage());
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

    private List<Target> parseTargets(String text, TargetType type) {
        return Stream.of(text.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Float::parseFloat)
                .map(f -> TargetType.TARGET.equals(type) ? Target.getTarget(f) : Target.getStoploss(f))
                .toList();
    }
}
