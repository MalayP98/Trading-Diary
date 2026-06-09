package com.trading.diary.repositories.formations.support;

import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for persisting and querying bullish engulfing records.
 */
public interface BullishEngulfingRepository extends JpaRepository<BullishEngulfing, Long> {
}
