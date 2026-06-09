package com.trading.diary.services;

import com.trading.diary.pojo.Person;
import com.trading.diary.repositories.miscs.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service for resolving the person associated with a trade idea while avoiding duplicate person records.
 */
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    /**
     * Resolves a person by name or creates the record if this is the first time the name has been used in the journal.
     */
    public Person getOrCreatePerson(String name){
        return personRepository.findById(name)
                .orElseGet(() -> personRepository.save(new Person(name)));
    }

    public List<Person> getAllPeople(){
        return personRepository.findAll();
    }

    public List<Person> getAllPeople(Pageable pageable){
        return personRepository.findAll(pageable).getContent();
    }

    public long getCount(){
        return personRepository.count();
    }
}
