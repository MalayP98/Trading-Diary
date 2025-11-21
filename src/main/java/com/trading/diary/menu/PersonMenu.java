package com.trading.diary.menu;

import com.trading.diary.pojo.Person;
import com.trading.diary.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonMenu extends AbstractMenu<Person> {

    private final PersonService personService;

    @Override
    public  Person showMenu() {
        return showMenu(personService.getAllPersons());
    }

    private Person showMenu(List<Person> people) {
        System.out.println("=== Select a Person ===");
        for (int i = 0; i < people.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, people.get(i).getName());
        }
        System.out.print("Choose an option (0-" + people.size() + "): ");
        int choice = InputType.INT.nextInput();
        if (choice < 1 || choice > people.size()) {
            System.out.println("Invalid choice. Please try again.");
            return showMenu(people);
        }
        return people.get(choice - 1);
    }
}
