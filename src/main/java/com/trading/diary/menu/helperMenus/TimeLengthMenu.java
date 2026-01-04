package com.trading.diary.menu.helperMenus;

import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.MenuName;
import com.trading.diary.utils.Period;
import org.springframework.stereotype.Service;

@Service
public class TimeLengthMenu extends AbstractMenu<Long> {

    @Override
    public Long showMenu() {
        print("Enter Years:");
        long years = InputType.LONG.nextSkipableInput();
        print("Enter Months:");
        long months = InputType.LONG.nextSkipableInput();
        print("Enter Weeks:");
        long weeks = InputType.LONG.nextSkipableInput();
        print("Enter Days:");
        long days = InputType.LONG.nextSkipableInput();
        long periodLength = new Period(years, months, weeks, days).convertToDays();
        if(periodLength == 0){
            print("Period length 0. Try again!");
            return showMenu();
        }
        return periodLength;
    }

    @Override
    public MenuName menuName() {
        return MenuName.TIME_LENGTH_MENU;
    }
}
