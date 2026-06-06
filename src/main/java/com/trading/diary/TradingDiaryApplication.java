package com.trading.diary;

import com.trading.diary.terminalui.MainMenuWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradingDiaryApplication implements CommandLineRunner {

	@Autowired
	private MainMenuWindow mainMenuWindow;

	public static void main(String[] args) {
		SpringApplication.run(TradingDiaryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mainMenuWindow.open();
	}
}
