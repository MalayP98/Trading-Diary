package com.trading.diary.repositories.miscs;

import com.trading.diary.helpers.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for persisting and querying target records.
 */
@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {
}
