package com.trading.diary.menu.helperMenus;

import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.MenuName;
import com.trading.diary.utils.PricePosition;
import org.springframework.stereotype.Service;

@Service
public class PricePositionMenu extends AbstractMenu<PricePosition> {

    @Override
    public PricePosition showMenu() {
        System.out.println("Select Price Position:");
        System.out.println("1. ABOVE");
        System.out.println("2. BELOW");
        System.out.println("3. THROUGH");
        System.out.println("4. ON");
        System.out.print("Enter choice (1-4): ");

        int choice = InputType.INT.nextInput();

        return switch (choice) {
            case 1 -> PricePosition.ABOVE;
            case 2 -> PricePosition.BELOW;
            case 3 -> PricePosition.THROUGH;
            case 4 -> PricePosition.ON;
            default -> {
                System.out.println("Invalid choice. Please try again.");
                yield showMenu();
            }
        };
    }

    @Override
    public MenuName menuName() {
        return MenuName.PRICE_POSITION_MENU;
    }
}
