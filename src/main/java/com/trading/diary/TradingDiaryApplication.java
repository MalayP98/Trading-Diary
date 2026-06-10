package com.trading.diary;

import com.trading.diary.terminalui.MainMenuWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bootstraps the Spring Boot application and hands control to the Lanterna-driven terminal UI once the application context is ready.
 */
@SpringBootApplication
public class TradingDiaryApplication implements CommandLineRunner {

	@Autowired
	private MainMenuWindow mainMenuWindow;

	/**
	 * Starts the Spring Boot container and the terminal UI application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TradingDiaryApplication.class, args);
	}

	/**
	 * Opens the main menu after all beans have been initialized so the terminal UI can begin interacting with the user.
	 */
	@Override
	public void run(String... args) {
//		mainMenuWindow.open();
	}
}
