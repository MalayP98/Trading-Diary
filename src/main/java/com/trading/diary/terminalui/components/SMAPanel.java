package com.trading.diary.terminalui.components;

import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.trading.diary.helpers.SMA;
import com.trading.diary.utils.emums.PricePosition;
import com.trading.diary.utils.emums.TrendlineDirections;

public class SMAPanel {

    private final String label;
    private final ComboBox<TrendlineDirections> directionCombo = new ComboBox<>(TrendlineDirections.values());
    private final ComboBox<PricePosition> positionCombo = new ComboBox<>(PricePosition.values());

    public SMAPanel(String label) {
        this.label = label;
    }

    public void addTo(Panel panel) {
        panel.addComponent(new Label(label + " Direction"));
        panel.addComponent(directionCombo);
        panel.addComponent(new Label(label + " Price Position"));
        panel.addComponent(positionCombo);
    }

    public SMA getSMA() {
        return new SMA(directionCombo.getSelectedItem(), positionCombo.getSelectedItem());
    }
}
