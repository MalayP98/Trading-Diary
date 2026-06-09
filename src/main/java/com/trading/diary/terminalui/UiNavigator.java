package com.trading.diary.terminalui;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Thin wrapper around Lanterna window navigation that gives the rest of the application a single place to open modal and stacked windows.
 */
@Component
@RequiredArgsConstructor
public class UiNavigator {

    private final MultiWindowTextGUI gui;

    /**
     * Opens a window modally and blocks until the user closes it.
     */
    public void show(Window window) {
        gui.addWindowAndWait(window);
    }

    /**
     */
    public void showOnTop(Window window) {
        gui.addWindow(window);
    }

    public MultiWindowTextGUI getGui() {
        return gui;
    }
}
