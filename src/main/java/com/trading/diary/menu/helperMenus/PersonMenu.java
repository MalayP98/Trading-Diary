package com.trading.diary.menu.helperMenus;

import com.trading.diary.helpers.Explainer;
import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.MenuName;
import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.pojo.Person;
import com.trading.diary.services.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonMenu extends AbstractMenu<Person> {

    private final PersonService personService;

    private final MenuFactory menuFactory;

    public PersonMenu(PersonService personService, Explainer explainer, MenuFactory menuFactory) {
        this.personService = personService;
        this.menuFactory = menuFactory;
    }

    @Override
    public Person showMenu() {
        print("=== Select Person ===");
        print("1. Add new person\n2. Choose from list");
        int choice = InputType.INT.nextInput();
        Person person = switch (choice) {
            case 1 -> addPerson();
            case 2 -> selectFromPeople();
            default -> showMenu();
        };
        if(person == null){
            print("No person selected. Try again!");
            return showMenu();
        }
        return person;
    }

    private Person addPerson() {
        print("Enter name of the person:");
        return personService.getOrCreatePerson(InputType.STRING.nextInput());
    }

    private Person selectFromPeople(){
        return (Person) menuFactory.getMenu(MenuName.PERSON_PAGINATION_MENU).showMenu();
    }

    @Override
    public MenuName menuName() {
        return MenuName.PERSON_MENU;
    }
}
