package com.trading.diary.terminalui;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.trading.diary.formations.FormationType;
import com.trading.diary.helpers.Target;
import com.trading.diary.pojo.MarketCap;
import com.trading.diary.pojo.Person;
import com.trading.diary.services.CompanyService;
import com.trading.diary.services.PlannedTradeService;
import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.utils.emums.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PlanTradeWindow {

    private final UiNavigator navigator;

    private final PlannedTradeService plannedTradeService;

    private final CompanyService companyService;

    public void open() {

        BasicWindow window =
                new BasicWindow("Plan Trade");

        Panel panel =
                new Panel(new GridLayout(2));

        TextBox symbolBox = new TextBox();
        TextBox targetBox = new TextBox();
        TextBox stoplossBox = new TextBox();
        TextBox notesBox = new TextBox();
        CheckBox nifty50CheckBox = new CheckBox("Nifty 50");
        CheckBox nifty200CheckBox = new CheckBox("Nifty 200");

        panel.addComponent(new Label("Symbol"));
        panel.addComponent(symbolBox);


        panel.addComponent(
                new Label("Targets (comma separated)")
        );
        panel.addComponent(targetBox);


        panel.addComponent(
                new Label("Stoploss (comma separated)")
        );
        panel.addComponent(stoplossBox);


        panel.addComponent(
                new Label("Notes (optional)")
        );
        panel.addComponent(notesBox);

        panel.addComponent(new Label("Nifty 50"));
        panel.addComponent(nifty50CheckBox);

        panel.addComponent(new Label("Nifty 200"));
        panel.addComponent(nifty200CheckBox);

        ComboBox<FormationType> formationTypeComboBox = new ComboBox<FormationType>(FormationType.values());
        panel.addComponent(formationTypeComboBox);

        Button saveButton =
                new Button("Save Plan", () -> {

                    try {
                        PlannedTrade.PlannedTradeBuilder x = PlannedTrade.builder()
                                .formationId(1)
                                .suggestedBy(Person.self());
                        x.formationType(formationTypeComboBox.getSelectedItem());
                        x.marketCap(new MarketCap(nifty50CheckBox.isChecked(), nifty200CheckBox.isChecked()));
                        x.company(companyService.getOrCreateCompany(symbolBox.getText()));
                        x.addTarget(getTargets(targetBox.getText(), TargetType.TARGET));
                        x.addStoploss(getTargets(stoplossBox.getText(), TargetType.STOPLOSS));
                        x.notes(notesBox.getText());
                        plannedTradeService.savePlannedTrade(x.build());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    MessageDialog.showMessageDialog(
                            navigator.getGui(),
                            "Success",
                            "Trade Planned"
                    );

                    window.close();
                });

        panel.addComponent(
                saveButton,
                GridLayout.createLayoutData(
                        GridLayout.Alignment.CENTER,
                        GridLayout.Alignment.CENTER,
                        true,
                        false,
                        2,
                        1
                )
        );

        window.setComponent(panel);

        navigator.show(window);
    }

    public List<Target> getTargets(String targetText, TargetType type) {
        String[] targetStrings = targetText.split(",");
        return Stream.of(targetStrings)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Float::parseFloat)
                .map(f -> TargetType.TARGET.equals(type) ? Target.getTarget(f) : Target.getStoploss(f))
                .toList();
    }
}