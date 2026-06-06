package com.trading.diary.terminalui.formations;

import com.googlecode.lanterna.gui2.*;
import com.trading.diary.formations.Formation;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.terminalui.UiNavigator;
import com.trading.diary.terminalui.Window;
import com.trading.diary.terminalui.components.SMAPanel;
import com.trading.diary.utils.emums.PricePosition;
import com.trading.diary.utils.emums.Strength;
import com.trading.diary.utils.emums.TimeFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class BullishEngulfingFormationWindow implements Window<Formation> {

    private final UiNavigator navigator;

    @Override
    public Formation open() {
        BasicWindow window = new BasicWindow("Bullish Engulfing - Formation Details");
        Panel panel = new Panel(new GridLayout(2));

        ComboBox<TimeFrame> timeFrameCombo = new ComboBox<>(TimeFrame.values());
        ComboBox<PricePosition> pricePositionCombo = new ComboBox<>(PricePosition.values());
        TextBox supportLengthBox = new TextBox();
        CheckBox priceSustainedBox = new CheckBox();
        CheckBox retestBox = new CheckBox();
        CheckBox partialBottomBox = new CheckBox();
        CheckBox partialTopBox = new CheckBox();
        ComboBox<Strength> volumeCombo = new ComboBox<>(Strength.values());

        panel.addComponent(new Label("Time Frame"));
        panel.addComponent(timeFrameCombo);
        panel.addComponent(new Label("Price Position On Support"));
        panel.addComponent(pricePositionCombo);
        panel.addComponent(new Label("Support Length (days)"));
        panel.addComponent(supportLengthBox);
        panel.addComponent(new Label("Price Sustained"));
        panel.addComponent(priceSustainedBox);
        panel.addComponent(new Label("Retest"));
        panel.addComponent(retestBox);
        panel.addComponent(new Label("Partial Bottom Engulfing"));
        panel.addComponent(partialBottomBox);
        panel.addComponent(new Label("Partial Top Engulfing"));
        panel.addComponent(partialTopBox);
        panel.addComponent(new Label("Volume"));
        panel.addComponent(volumeCombo);

        AtomicReference<Formation> result = new AtomicReference<>();

        panel.addComponent(
                new Button("Confirm", () -> {
                    result.set(BullishEngulfing.builder()
                            .timeFrame(timeFrameCombo.getSelectedItem())
                            .pricePositionOnSupport(pricePositionCombo.getSelectedItem())
                            .supportLength(Long.parseLong(supportLengthBox.getText().trim()))
                            .priceSustained(priceSustainedBox.isChecked())
                            .retest(retestBox.isChecked())
                            .partialBottomEngulfing(partialBottomBox.isChecked())
                            .partialTopEngulfing(partialTopBox.isChecked())
                            .volume(volumeCombo.getSelectedItem())
                            .build());
                    window.close();
                }),
                GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER, true, false, 2, 1)
        );

        window.setComponent(panel);
        navigator.show(window);
        return result.get();
    }
}
