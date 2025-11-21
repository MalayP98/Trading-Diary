package com.trading.diary.menu;

import com.trading.diary.utils.TimeFrame;
import org.springframework.stereotype.Service;

@Service
public class TimeFrameMenu extends AbstractMenu<TimeFrame> {

    @Override
    public  TimeFrame showMenu() {
        System.out.println("=== Select Time Frame ===");
        System.out.println("1. DAILY");
        System.out.println("2. WEEKLY");
        System.out.println("3. MONTHLY");
        System.out.print("Choose an option (1-3): ");

        int choice = InputType.INT.nextInput();
        return switch (choice) {
            case 1 -> TimeFrame.DAILY;
            case 2 -> TimeFrame.WEEKLY;
            case 3 -> TimeFrame.MONTHLY;
            default -> {
                System.out.println("Invalid choice. Try again!");
                yield showMenu();
            }
        };
    }
}
