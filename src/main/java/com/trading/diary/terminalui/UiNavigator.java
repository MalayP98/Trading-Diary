package com.trading.diary.terminalui;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UiNavigator {

    private final MultiWindowTextGUI gui;

    public void show(Window window) {
        gui.addWindowAndWait(window);
    }

    public MultiWindowTextGUI getGui() {
        return gui;
    }
}