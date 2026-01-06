package com.trading.diary.menu.targetMenu;

import com.trading.diary.helpers.Target;
import com.trading.diary.menu.AbstractMenu;

import java.util.ArrayList;
import java.util.List;

import static com.trading.diary.utils.Helper.skipLines;

public abstract class AbstractTargetMenu extends AbstractMenu<List<Target>> {

    @Override
    public List<Target> showMenu() {
        List<Target> targets = new ArrayList<>();
        return showMenu(targets);
    }

    protected abstract String targetType();

    private List<Target> showMenu(List<Target> targets){
        print(openingMenu());
        int choice = InputType.INT.nextInput();
        switch (choice) {
            case 1:
                print("Enter " + targetType() + " value:");
                float target = InputType.FLOAT.nextInput();
                targets.add(Target.getTarget(target));
                skipLines(2);
                break;
            case 2:
                removeTarget(targets);
                skipLines(2);
                break;
            case 3:
                viewTargets(targets);
                skipLines(2);
                break;
            case 0:
                return targets;
            default:
                print("Invalid choice. Try again.");
                return showMenu(targets);
        }
        return showMenu(targets);
    }

    private void removeTarget(List<Target> targets) {
        viewTargets(targets);
        System.out.print("Enter the number of the " + targetType() + " to remove: ");
        int index = InputType.INT.nextInput();
        index--;
        if(index >= 0 && index < targets.size()) {
            targets.remove(index);
            print("Target removed.");
        } else {
            print("Invalid index.");
        }
    }

    private void viewTargets(List<Target> targets) {
        if(targets.isEmpty()) {
            print("No " + targetType() + " available.");
            return;
        }
        int i = 1;
        StringBuilder targetView = new StringBuilder();
        int maxLength = Integer.MIN_VALUE;
        for(Target target : targets) {
            String subview = i + ". " + target.toString();
            maxLength = Math.max(maxLength, subview.length());
            targetView.append(subview).append(System.lineSeparator());
            i++;
        }
        print("-".repeat(maxLength));
        print(targetView.toString().trim());
        print("-".repeat(maxLength));
    }

    protected String openingMenu(){
        return  "=== " + targetType() + " Menu ===" +
                System.lineSeparator() +
                "1. Add " + targetType() + System.lineSeparator() +
                "2. Remove " + targetType() + System.lineSeparator() +
                "3. View " + targetType() + System.lineSeparator() +
                "0. Exit" + System.lineSeparator() +
                "Choose an option (0-3):";
    }
}
