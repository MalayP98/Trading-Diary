package com.trading.diary.menu;

import com.trading.diary.helpers.Explainer;
import com.trading.diary.menu.formation_menu.support_reversal.paginationMenus.SimplePaginationMenu;
import com.trading.diary.pojo.Person;
import com.trading.diary.services.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonMenu extends AbstractMenu<Person> {

    private final PersonService personService;

    private final SimplePaginationMenu<Void, Person> personPaginationMenu;
    
    private final Explainer explainer;

    public PersonMenu(PersonService personService, Explainer explainer) {
        this.personService = personService;
        this.explainer = explainer;
        personPaginationMenu = new SimplePaginationMenu<>(
                personService::getCount,
                (attr, page) -> personService.getAllPeople(page),
                () -> null,
                explainer
        );
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
        return personPaginationMenu.showMenu();
    }
}
