package com.trading.diary.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Person {

    @Id
    private String name;

    private static final Person SELF = new Person("Self");

    public Person(String name) {
        this.name = name;
    }

    public static Person self(){
        return SELF;
    }
}
