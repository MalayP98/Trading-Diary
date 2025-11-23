package com.trading.diary.services;

import com.trading.diary.pojo.Person;
import com.trading.diary.repositories.miscs.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
