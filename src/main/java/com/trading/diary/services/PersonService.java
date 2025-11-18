package com.trading.diary.services;

import com.trading.diary.pojo.Person;
import com.trading.diary.repositories.miscs.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person getOrCreatePerson(String name){
        return personRepository.findById(name)
                .orElseGet(() -> personRepository.save(new Person(name)));
    }

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }
}
