package com.trading.diary.terminalui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.trading.diary.configs.ApplicationShutdownManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Top-level menu loop for the terminal journal. It dispatches to the major trade workflows and only shuts the application down once the user explicitly exits.
 */
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

    /**
     * Runs the top-level menu loop until the user chooses to exit the application.
     */
    public void open() {
        AtomicBoolean running = new AtomicBoolean(true);

        while (running.get()) {
            BasicWindow window = new BasicWindow("Trading Journal");
            Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

            panel.addComponent(new Label("Trading Journal"));
            panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

            panel.addComponent(new Button("1. Log Trade", () -> {
                window.close();
                safeRun(logTradeWindow::open);
            }));

            panel.addComponent(new Button("2. Plan Trade", () -> {
                window.close();
                safeRun(planTradeWindow::open);
            }));

            panel.addComponent(new Button("3. Confirm Planned Trade", () -> {
                window.close();
                safeRun(confirmPlannedTradeWindow::open);
            }));

            panel.addComponent(new Button("4. Close Trade", () -> {
                window.close();
                safeRun(closeTradeWindow::open);
            }));

            panel.addComponent(new Button("5. View Open Trades", () -> {
                window.close();
                safeRun(viewOpenTradesWindow::open);
            }));

            panel.addComponent(new Button("6. View All Trades", () -> {
                window.close();
                safeRun(viewAllTradesWindow::open);
            }));

            panel.addComponent(new Button("7. Update Trade", () -> {
                window.close();
                safeRun(updateTradeWindow::open);
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

    private void safeRun(Runnable action) {
        try {
            action.run();
        } catch (Exception e) {
            com.googlecode.lanterna.gui2.dialogs.MessageDialog.showMessageDialog(
                    navigator.getGui(), "Error", e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName());
        }
    }
}
