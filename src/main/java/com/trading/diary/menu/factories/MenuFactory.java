package com.trading.diary.menu.factories;


import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.MenuName;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MenuFactory {

    private static final Map<MenuName, AbstractMenu<?>> menuMap = new ConcurrentHashMap<>();

    public AbstractMenu<?> getMenu(MenuName menuName) {
        if (!menuMap.containsKey(menuName)) {
            throw new IllegalArgumentException("No menu of type " + menuName.name() + " found.");
        }
        return menuMap.get(menuName);
    }

    public static boolean addMenu(AbstractMenu<?> menu){
        if(menuMap.containsKey(menu.menuName())){
            return false;
        }
        menuMap.put(menu.menuName(), menu);
        return true;
    }
}
