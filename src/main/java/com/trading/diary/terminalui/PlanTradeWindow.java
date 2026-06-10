package com.trading.diary.terminalui;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import com.trading.diary.helpers.Target;
import com.trading.diary.pojo.Person;
import com.trading.diary.services.CompanyService;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.services.formation.FormationServiceFactory;
import com.trading.diary.terminalui.components.MarketCapPanel;
import com.trading.diary.terminalui.formations.FormationWindowFactory;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.utils.emums.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * Terminal form for recording a trade idea before execution. It mirrors the live-trade workflow but stores the result as a planned trade instead of a live position.
 */
@Component
@RequiredArgsConstructor
public class PlanTradeWindow {

    private final UiNavigator navigator;
    private final PlannedTradeService plannedTradeService;
    private final CompanyService companyService;
    private final FormationWindowFactory formationWindowFactory;
    private final FormationServiceFactory formationServiceFactory;

    /**
     * Builds the planned-trade workflow, collecting formation details and trade-planning metadata before persisting the plan.
     */
    public void open() {
        BasicWindow window = new BasicWindow("Plan Trade");
        Panel panel = new Panel(new GridLayout(2));

        TextBox symbolBox = new TextBox();
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
                new Button("Save Plan", () -> {
                    try {
                        long formationId = -1;
                        if (formationRef.get() != null) {

                            Formation savedFormation = (Formation) formationServiceFactory
                                    .getFormationService(formationTypeCombo.getSelectedItem())
                                    .save(formationRef.get());
                            formationId = ((com.trading.diary.pojo.Audit) savedFormation).getId();
                        }
                        PlannedTrade plannedTrade = PlannedTrade.builder()
                                .company(companyService.getOrCreateCompany(symbolBox.getText().trim()))
                                .suggestedBy(Person.self())
                                .formationType(formationTypeCombo.getSelectedItem())
                                .formationId(formationId)
                                .marketCap(marketCapPanel.getMarketCap())
                                .addTarget(parseTargets(targetBox.getText(), TargetType.TARGET))
                                .addStoploss(parseTargets(stoplossBox.getText(), TargetType.STOPLOSS))
                                .notes(notesBox.getText())
                                .suggestedBy(new Person(suggestedByBox.getText().trim()))
                                .build();

                        plannedTradeService.savePlannedTrade(plannedTrade);
                        MessageDialog.showMessageDialog(navigator.getGui(), "Success", "Trade Planned");
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
