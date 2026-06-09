package com.trading.diary.repositories.formations.support;

import com.trading.diary.formations.impls.support.extension.MorningStar;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for persisting and querying morning star records.
 */
public interface MorningStarRepository extends JpaRepository<MorningStar, Long> {
}
