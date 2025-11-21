package com.trading.diary.menu.formation_menu;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import com.trading.diary.menu.AbstractMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FormationMenuFactory extends AbstractMenu<AbstractFormationMenu<? extends Formation>> {

    private final List<AbstractFormationMenu<? extends Formation>> abstractFormationMenus;

    private final Map<FormationType, AbstractFormationMenu<? extends Formation>> menuMap;

    @Autowired
    public FormationMenuFactory(List<AbstractFormationMenu<? extends Formation>> abstractFormationMenus) {
        this.abstractFormationMenus = abstractFormationMenus;
        menuMap = new HashMap<>();
        for (AbstractFormationMenu<? extends Formation> abstractFormationMenu : abstractFormationMenus) {
            menuMap.put(abstractFormationMenu.getFormation(), abstractFormationMenu);
        }
    }

    public AbstractFormationMenu<? extends Formation> getFormationMenu(FormationType type) {
        return menuMap.get(type);
    }

    @Override
    public AbstractFormationMenu<? extends Formation> showMenu() {
        FormationType[] formationTypes = FormationType.values();
        print("=== Select Formation ===");
        for (FormationType type : formationTypes) {
            print(type.ordinal() + 1 + ". " + type.name());
        }
        int choice = InputType.INT.nextInput();
        if(choice < 1 || choice > formationTypes.length){
            print("Choice not in list. Try again!");
            return showMenu();
        }
        return getFormationMenu(formationTypes[choice-1]);
    }
}
