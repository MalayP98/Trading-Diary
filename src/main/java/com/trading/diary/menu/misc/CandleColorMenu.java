package com.trading.diary.menu.misc;

import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.utils.CandleColor;
import org.springframework.stereotype.Service;

@Service
public class CandleColorMenu extends AbstractMenu<CandleColor> {

    @Override
    public CandleColor showMenu() {
        System.out.println("=== Candle Color Menu ===");
        System.out.println("1. RED");
        System.out.println("2. GREEN");
        System.out.print("Choose an option (1-2): ");

        int choice = nextInt();
        return switch (choice) {
            case 1 -> CandleColor.RED;
            case 2 -> CandleColor.GREEN;
            default -> {
                System.out.println("Invalid choice. Try again");
                yield showMenu();
            }
        };
    }
}
