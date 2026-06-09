package com.trading.diary.services.formation.impls.breakout;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.extension.HorizontalResistanceBreakout;
import com.trading.diary.repositories.formations.resistance.HorizontalResistanceBreakoutRepository;
import com.trading.diary.services.formation.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Formation service implementation for persisting and retrieving horizontal resistance breakout records.
 */
@Service
@RequiredArgsConstructor
public class HorizontalResistanceBreakoutService implements FormationService<HorizontalResistanceBreakout> {

    private final HorizontalResistanceBreakoutRepository horizontalResistanceBreakoutRepository;

    @Override
    public FormationType getType() {
        return FormationType.HORIZONTAL_RESISTANCE_BREAKOUT;
    }

    @Override
    public JpaRepository<HorizontalResistanceBreakout, Long> getRepository() {
        return horizontalResistanceBreakoutRepository;
    }
}
