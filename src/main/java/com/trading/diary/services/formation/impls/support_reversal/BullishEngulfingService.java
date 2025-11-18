package com.trading.diary.services.formation.impls.support_reversal;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.repositories.formations.support.BullishEngulfingRepository;
import com.trading.diary.services.formation.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BullishEngulfingService implements FormationService {

    private final BullishEngulfingRepository bullishEngulfingRepository;

    @Override
    public FormationType getType() {
        return FormationType.BULLISH_ENGULFING;
    }


    @Override
    public JpaRepository<BullishEngulfing, Long> getRepository() {
        return bullishEngulfingRepository;
    }
}
