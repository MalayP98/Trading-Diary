package com.trading.diary.terminalui.formations;

import com.googlecode.lanterna.gui2.*;
import com.trading.diary.formations.Formation;
import com.trading.diary.formations.impls.resistance.extension.FallingResistanceBreakout;
import com.trading.diary.terminalui.UiNavigator;
import com.trading.diary.terminalui.Window;
import com.trading.diary.terminalui.components.SMAPanel;
import com.trading.diary.utils.emums.Strength;
import com.trading.diary.utils.emums.TimeFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Lanterna form window that captures the inputs for a falling resistance breakout setup before the caller persists the resulting formation.
 */
@Component
@RequiredArgsConstructor
public class FallingResistanceBreakoutFormationWindow implements Window<Formation> {

    private final UiNavigator navigator;

    /**
     * Collects the shared breakout inputs plus the FATHER-specific angle and compression values before returning the formation.
     */
    @Override
    public Formation open() {
        BasicWindow window = new BasicWindow("Falling Resistance Breakout - Formation Details");
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
        TextBox angleBox = new TextBox();
        TextBox priceDiffPercentageBox = new TextBox();
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
        panel.addComponent(new Label("Angle (0-45)"));
        panel.addComponent(angleBox);
        panel.addComponent(new Label("Price Diff Percentage"));
        panel.addComponent(priceDiffPercentageBox);
        sma20Panel.addTo(panel);
        sma50Panel.addTo(panel);
        sma200Panel.addTo(panel);

        AtomicReference<Formation> result = new AtomicReference<>();

        panel.addComponent(
                new Button("Confirm", () -> {
                    result.set(FallingResistanceBreakout.builder()
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
                            .angle(Float.parseFloat(angleBox.getText().trim()))
                            .priceDiffPercentage(Float.parseFloat(priceDiffPercentageBox.getText().trim()))
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
