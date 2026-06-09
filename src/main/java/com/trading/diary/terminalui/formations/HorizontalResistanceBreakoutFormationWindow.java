package com.trading.diary.terminalui.formations;

import com.googlecode.lanterna.gui2.*;
import com.trading.diary.formations.Formation;
import com.trading.diary.formations.impls.resistance.extension.HorizontalResistanceBreakout;
import com.trading.diary.terminalui.UiNavigator;
import com.trading.diary.terminalui.Window;
import com.trading.diary.terminalui.components.SMAPanel;
import com.trading.diary.utils.emums.Strength;
import com.trading.diary.utils.emums.TimeFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Lanterna form window that captures the inputs for a horizontal resistance breakout setup before the caller persists the resulting formation.
 */
@Component
@RequiredArgsConstructor
public class HorizontalResistanceBreakoutFormationWindow implements Window<Formation> {

    private final UiNavigator navigator;

    /**
     * Collects the shared breakout inputs for a horizontal resistance breakout and returns the resulting formation instance.
     */
    @Override
    public Formation open() {
        BasicWindow window = new BasicWindow("Horizontal Resistance Breakout - Formation Details");
        Panel panel = new Panel(new GridLayout(2));

        ComboBox<TimeFrame> timeFrameCombo = new ComboBox<>(TimeFrame.values());
        CheckBox confirmBreakoutBox = new CheckBox();
        ComboBox<Strength> breakoutVolumeCombo = new ComboBox<>(Strength.values());
        CheckBox allTimeHighBox = new CheckBox();
        TextBox touchesBox = new TextBox();
        TextBox resistanceLengthBox = new TextBox();
        CheckBox higherLowsBox = new CheckBox();
        CheckBox priorUptrendBox = new CheckBox();
        TextBox rsiBox = new TextBox();
        TextBox breakoutPercentageBox = new TextBox();
        SMAPanel sma20Panel = new SMAPanel("SMA 20");
        SMAPanel sma50Panel = new SMAPanel("SMA 50");
        SMAPanel sma200Panel = new SMAPanel("SMA 200");

        panel.addComponent(new Label("Time Frame"));
        panel.addComponent(timeFrameCombo);
        panel.addComponent(new Label("Confirm Breakout"));
        panel.addComponent(confirmBreakoutBox);
        panel.addComponent(new Label("Breakout Volume"));
        panel.addComponent(breakoutVolumeCombo);
        panel.addComponent(new Label("All Time High"));
        panel.addComponent(allTimeHighBox);
        panel.addComponent(new Label("Touches (min 2)"));
        panel.addComponent(touchesBox);
        panel.addComponent(new Label("Resistance Length (days)"));
        panel.addComponent(resistanceLengthBox);
        panel.addComponent(new Label("Higher Lows"));
        panel.addComponent(higherLowsBox);
        panel.addComponent(new Label("Prior Uptrend"));
        panel.addComponent(priorUptrendBox);
        panel.addComponent(new Label("RSI"));
        panel.addComponent(rsiBox);
        panel.addComponent(new Label("Breakout Percentage"));
        panel.addComponent(breakoutPercentageBox);
        sma20Panel.addTo(panel);
        sma50Panel.addTo(panel);
        sma200Panel.addTo(panel);

        AtomicReference<Formation> result = new AtomicReference<>();

        panel.addComponent(
                new Button("Confirm", () -> {
                    result.set(HorizontalResistanceBreakout.builder()
                            .timeFrame(timeFrameCombo.getSelectedItem())
                            .confirmBreakout(confirmBreakoutBox.isChecked())
                            .breakoutVolume(breakoutVolumeCombo.getSelectedItem())
                            .allTimeHigh(allTimeHighBox.isChecked())
                            .touches(Integer.parseInt(touchesBox.getText().trim()))
                            .resistanceLength(Long.parseLong(resistanceLengthBox.getText().trim()))
                            .higherLows(higherLowsBox.isChecked())
                            .priorUptrend(priorUptrendBox.isChecked())
                            .rsi(Float.parseFloat(rsiBox.getText().trim()))
                            .breakoutPercentage(Float.parseFloat(breakoutPercentageBox.getText().trim()))
                            .sma20(sma20Panel.getSMA())
                            .sma50(sma50Panel.getSMA())
                            .sma200(sma200Panel.getSMA())
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
