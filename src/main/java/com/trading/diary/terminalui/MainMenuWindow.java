package com.trading.diary.terminalui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.trading.diary.configs.ApplicationShutdownManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class MainMenuWindow {

    private final UiNavigator navigator;
    private final ApplicationShutdownManager shutdownManager;
    private final LogTradeWindow logTradeWindow;
    private final PlanTradeWindow planTradeWindow;
    private final ConfirmPlannedTradeWindow confirmPlannedTradeWindow;
    private final CloseTradeWindow closeTradeWindow;
    private final ViewOpenTradesWindow viewOpenTradesWindow;
    private final ViewAllTradesWindow viewAllTradesWindow;
    private final UpdateTradeWindow updateTradeWindow;

    public void open() {
        AtomicBoolean running = new AtomicBoolean(true);

        while (running.get()) {
            BasicWindow window = new BasicWindow("Trading Journal");
            Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

            panel.addComponent(new Label("Trading Journal"));
            panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

            panel.addComponent(new Button("1. Log Trade", () -> {
                window.close();
                logTradeWindow.open();
            }));

            panel.addComponent(new Button("2. Plan Trade", () -> {
                window.close();
                planTradeWindow.open();
            }));

            panel.addComponent(new Button("3. Confirm Planned Trade", () -> {
                window.close();
                confirmPlannedTradeWindow.open();
            }));

            panel.addComponent(new Button("4. Close Trade", () -> {
                window.close();
                closeTradeWindow.open();
            }));

            panel.addComponent(new Button("5. View Open Trades", () -> {
                window.close();
                viewOpenTradesWindow.open();
            }));

            panel.addComponent(new Button("6. View All Trades", () -> {
                window.close();
                viewAllTradesWindow.open();
            }));

            panel.addComponent(new Button("7. Update Trade", () -> {
                window.close();
                updateTradeWindow.open();
            }));

            panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

            panel.addComponent(new Button("Exit", () -> {
                running.set(false);
                window.close();
            }));

            window.setComponent(panel);
            navigator.show(window);
        }

        shutdownManager.initiateShutdown(0);
    }
}
