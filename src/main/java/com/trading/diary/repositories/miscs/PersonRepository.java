package com.trading.diary.repositories.miscs;

import com.trading.diary.pojo.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for persisting and querying person records.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

}
