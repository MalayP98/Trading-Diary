package com.trading.diary.configs;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class LanternaConfig {

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

    @Bean
    public MultiWindowTextGUI gui(Screen screen) {
        return new MultiWindowTextGUI(screen);
    }
}