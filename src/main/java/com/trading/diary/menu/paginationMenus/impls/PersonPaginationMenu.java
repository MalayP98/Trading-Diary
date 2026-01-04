package com.trading.diary.menu.paginationMenus.impls;

import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.paginationMenus.SimplePaginationMenu;
import com.trading.diary.pojo.Person;
import com.trading.diary.services.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonPaginationMenu extends SimplePaginationMenu<Void, Person> {

    public PersonPaginationMenu(PersonService personService) {
        super(personService::getCount,
                (attr, page) -> personService.getAllPeople(page),
                () -> null,
                (person) -> ((Person)person).getName());
    }

    @Override
    public MenuName menuName() {
        return MenuName.PERSON_PAGINATION_MENU;
    }
}
