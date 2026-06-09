package com.trading.diary.terminalui.components;

import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.trading.diary.helpers.SMA;
import com.trading.diary.utils.emums.PricePosition;
import com.trading.diary.utils.emums.TrendlineDirections;

/**
 * Reusable UI fragment for capturing one moving average's direction and price relationship for breakout scoring.
 */
public class SMAPanel {

    private final String label;
    private final ComboBox<TrendlineDirections> directionCombo = new ComboBox<>(TrendlineDirections.values());
    private final ComboBox<PricePosition> positionCombo = new ComboBox<>(PricePosition.values());

    public SMAPanel(String label) {
        this.label = label;
    }

    /**
     * Adds the direction and price-position controls for this SMA to the supplied panel.
     */
    public void addTo(Panel panel) {
        panel.addComponent(new Label(label + " Direction"));
        panel.addComponent(directionCombo);
        panel.addComponent(new Label(label + " Price Position"));
        panel.addComponent(positionCombo);
    }

    /**
     * Builds an SMA snapshot from the current UI selections.
     */
    public SMA getSMA() {
        return new SMA(directionCombo.getSelectedItem(), positionCombo.getSelectedItem());
    }
}
