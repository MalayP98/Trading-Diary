package com.trading.diary.services.formation.impls.breakout;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.extension.FallingResistanceBreakout;
import com.trading.diary.repositories.formations.resistance.FallingResistanceBreakoutRepository;
import com.trading.diary.services.formation.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Formation service implementation for persisting and retrieving falling resistance breakout records.
 */
@Service
@RequiredArgsConstructor
public class FallingResistanceBreakoutService implements FormationService<FallingResistanceBreakout> {

    private final FallingResistanceBreakoutRepository fallingResistanceBreakoutRepository;

    @Override
    public FormationType getType() {
        return FormationType.FALLING_RESISTANCE_BREAKOUT;
    }

    @Override
    public JpaRepository<FallingResistanceBreakout, Long> getRepository() {
        return fallingResistanceBreakoutRepository;
    }
}
