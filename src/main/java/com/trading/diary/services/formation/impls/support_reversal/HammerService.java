package com.trading.diary.services.formation.impls.support_reversal;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.repositories.formations.support.HammerRepository;
import com.trading.diary.services.formation.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HammerService implements FormationService<Hammer> {
    
    private final HammerRepository hammerRepository;
    
    @Override
    public FormationType getType() {
        return FormationType.HAMMER;
    }


    @Override
    public JpaRepository<Hammer, Long> getRepository() {
        return hammerRepository;
    }
}