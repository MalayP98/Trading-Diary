package com.trading.diary.repositories.miscs;

import com.trading.diary.helpers.SMA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for persisting and querying sma records.
 */
@Repository
public interface SMARepository extends JpaRepository<SMA, Long> {
}
