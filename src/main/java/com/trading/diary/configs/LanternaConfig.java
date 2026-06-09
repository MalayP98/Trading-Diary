package com.trading.diary.configs;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Creates the shared Lanterna terminal primitives used by every window in the terminal journal. The beans are kept in one configuration class so the UI stack is initialized exactly once.
 */
@Configuration
public class LanternaConfig {

    /**
     * Creates and starts the shared terminal screen used by the entire Lanterna UI.
     */
    @Bean
    public Screen screen() throws IOException {

        DefaultTerminalFactory factory =
                new DefaultTerminalFactory();

        Terminal terminal =
                factory.createTerminal();

        Screen screen =
                new TerminalScreen(terminal);

        screen.startScreen();

        return screen;
    }

    /**
     * Builds the window manager that all terminal workflows use for modal navigation.
     */
    @Bean
    public MultiWindowTextGUI gui(Screen screen) {
        return new MultiWindowTextGUI(screen);
    }
}
