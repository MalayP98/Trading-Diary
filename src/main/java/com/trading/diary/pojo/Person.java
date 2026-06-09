package com.trading.diary.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents the person who suggested a trade idea. A singleton SELF instance is used when the trader logs their own setup.
 */
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

    /**
     * Returns the shared self-reference used when the trader is also the source of the idea.
     */
    public static Person self(){
        return SELF;
    }
}
