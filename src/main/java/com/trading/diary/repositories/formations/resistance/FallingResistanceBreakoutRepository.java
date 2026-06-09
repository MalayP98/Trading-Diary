package com.trading.diary.repositories.formations.resistance;

import com.trading.diary.formations.impls.resistance.extension.FallingResistanceBreakout;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for persisting and querying falling resistance breakout records.
 */
public interface FallingResistanceBreakoutRepository extends JpaRepository<FallingResistanceBreakout, Long> {
}
