package com.trading.diary.menu.formation_menu;

import com.trading.diary.formations.Formation;
import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.MenuName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FormationSelectionMenu extends AbstractMenu<AbstractMenu<? extends Formation>> {

    private final Map<MenuName, AbstractMenu<? extends Formation>> menuMap;

    private final List<MenuName> menuNames;

    @Autowired
    public FormationSelectionMenu(List<AbstractMenu<? extends Formation>> abstractFormationMenus) {
        menuMap = new HashMap<>();
        menuNames = new ArrayList<>();
        for (AbstractMenu<? extends Formation> abstractFormationMenu : abstractFormationMenus) {
            menuMap.put(abstractFormationMenu.menuName(), abstractFormationMenu);
            menuNames.add(abstractFormationMenu.menuName());
        }
    }

    public AbstractMenu<? extends Formation> getFormationMenu(MenuName menuName) {
        return menuMap.get(menuName);
    }

    @Override
    public AbstractMenu<? extends Formation> showMenu() {
        int i = 1;
        print("=== Select Formation ===");
        for (MenuName menuName : menuNames) {
            print(i + ": " + menuNames.get(i-1));
            i++;
        }
        int choice = InputType.INT.nextInput();
        if (choice < 1 || choice > menuNames.size()) {
            print("Choice not in list. Try again!");
            return showMenu();
        }
        return getFormationMenu(menuNames.get(choice - 1));
    }

    @Override
    public MenuName menuName() {
        return MenuName.FORMATION_SELECTION_MENU;
    }
}
