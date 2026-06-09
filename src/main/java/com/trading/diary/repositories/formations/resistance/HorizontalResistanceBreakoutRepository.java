package com.trading.diary.repositories.formations.resistance;

import com.trading.diary.formations.impls.resistance.extension.HorizontalResistanceBreakout;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for persisting and querying horizontal resistance breakout records.
 */
public interface HorizontalResistanceBreakoutRepository extends JpaRepository<HorizontalResistanceBreakout, Long> {
}
