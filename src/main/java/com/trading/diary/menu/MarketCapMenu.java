package com.trading.diary.menu;

import com.trading.diary.pojo.MarketCap;
import org.springframework.stereotype.Service;

@Service
public class MarketCapMenu extends AbstractMenu<MarketCap>{

    @Override
    public MarketCap showMenu() {
        System.out.println("=== Select Market Capitalization ===");
        System.out.println("1. Is NIFTY 50");
        System.out.println("2. Is NIFTY 200");
        System.out.println("3. NONE");
        System.out.print("Choose an option (1-3): ");

        int choice = nextInt();
        return switch (choice) {
            case 1 -> new MarketCap(true, true);
            case 2 -> new MarketCap(false, true);
            case 3 -> new MarketCap(false, false);
            default -> {
                System.out.println("Invalid choice. Trying again.");
                yield showMenu();
            }
        };
    }
}
