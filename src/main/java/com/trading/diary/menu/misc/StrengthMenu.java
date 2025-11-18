package com.trading.diary.menu.misc;

import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.utils.Strength;
import org.springframework.stereotype.Service;

@Service
public class StrengthMenu extends AbstractMenu<Strength> {

    @Override
    public Strength showMenu() {
        System.out.println("=== Choose Volume Strength ===");
        System.out.println("1. VERY WEAK");
        System.out.println("2. WEAK");
        System.out.println("3. STRONG");
        System.out.println("4. VERY_STRONG");
        System.out.print("Choose an option (1-4): ");

        int choice = nextInt();
        return switch (choice) {
            case 1 -> Strength.VERY_WEAK;
            case 2 -> Strength.WEAK;
            case 3 -> Strength.STRONG;
            case 4 -> Strength.VERY_STRONG;
            default -> {
                System.out.println("Invalid choice. Try again");
                yield showMenu();
            }
        };
    }
}
