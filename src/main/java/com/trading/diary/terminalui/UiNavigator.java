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

    /** Add a window on top of the current one without blocking the calling thread.
     *  Use this when opening a window from within a button/event handler. */
    public void showOnTop(Window window) {
        gui.addWindow(window);
    }

    public MultiWindowTextGUI getGui() {
        return gui;
    }
}