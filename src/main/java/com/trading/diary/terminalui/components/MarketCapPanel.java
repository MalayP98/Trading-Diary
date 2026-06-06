package com.trading.diary.terminalui.components;

import com.googlecode.lanterna.gui2.CheckBox;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.trading.diary.pojo.MarketCap;

public class MarketCapPanel {

    private final CheckBox nifty50CheckBox = new CheckBox();
    private final CheckBox nifty200CheckBox = new CheckBox();

    public void addTo(Panel panel) {
        panel.addComponent(new Label("Nifty 50"));
        panel.addComponent(nifty50CheckBox);
        panel.addComponent(new Label("Nifty 200"));
        panel.addComponent(nifty200CheckBox);
    }

    public MarketCap getMarketCap() {
        return new MarketCap(nifty50CheckBox.isChecked(), nifty200CheckBox.isChecked());
    }
}
