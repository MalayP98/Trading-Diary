package com.trading.diary.repositories.formations.support;

import com.trading.diary.formations.impls.support.extension.Hammer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for persisting and querying hammer records.
 */
public interface HammerRepository extends JpaRepository<Hammer, Long> {
}
