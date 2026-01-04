package com.trading.diary.menu.helperMenus;

import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.MenuName;
import com.trading.diary.utils.TrendlineDirections;
import org.springframework.stereotype.Service;

@Service
public class TrendlineDirectionMenu extends AbstractMenu<TrendlineDirections> {

    @Override
    public TrendlineDirections showMenu() {
        System.out.println("Select Trendline Direction:");
        System.out.println("1. RISING");
        System.out.println("2. FALLING");
        System.out.println("3. HORIZONTAL");
        System.out.print("Enter choice (1-3): ");

        int choice = InputType.INT.nextInput();

        switch (choice) {
            case 1:
                return TrendlineDirections.RISING;
            case 2:
                return TrendlineDirections.FALLING;
            case 3:
                return TrendlineDirections.HORIZONTAL;
            default:
                System.out.println("Invalid choice. Please try again.");
                return showMenu();
        }
    }

    @Override
    public MenuName menuName() {
        return MenuName.TRENDLINE_MENU;
    }
}
