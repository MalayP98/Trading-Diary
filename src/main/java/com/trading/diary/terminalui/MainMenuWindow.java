package com.trading.diary.terminalui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainMenuWindow {

    private final UiNavigator navigator;

    private final PlanTradeWindow planTradeWindow;
//    private final ConfirmTradeWindow confirmTradeWindow;
//    private final CloseTradeWindow closeTradeWindow;

    public void open() {

        BasicWindow window =
                new BasicWindow("Trading Journal");

        Panel panel = new Panel();

        panel.setLayoutManager(
                new LinearLayout(Direction.VERTICAL)
        );

        panel.addComponent(
                new Label("Trading Journal")
        );

        panel.addComponent(
                new EmptySpace(new TerminalSize(0, 1))
        );

        panel.addComponent(
                new Button("1. Plan Trade", () -> {
                    window.close();
                    planTradeWindow.open();
                })
        );

        panel.addComponent(
                new Button("2. Confirm Planned Trade", () -> {
                    window.close();
//                    confirmTradeWindow.open();
                })
        );

        panel.addComponent(
                new Button("3. Close Trade", () -> {
                    window.close();
//                    closeTradeWindow.open();
                })
        );

        panel.addComponent(
                new Button("Exit", window::close)
        );

        window.setComponent(panel);

        while(true) {
            navigator.show(window);
        }
    }
}