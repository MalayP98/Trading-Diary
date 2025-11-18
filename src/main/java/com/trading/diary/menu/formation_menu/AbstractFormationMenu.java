package com.trading.diary.menu.formation_menu;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import com.trading.diary.menu.AbstractMenu;

public abstract class AbstractFormationMenu<T extends Formation> extends AbstractMenu<T> {

    public abstract FormationType getFormation();
}
