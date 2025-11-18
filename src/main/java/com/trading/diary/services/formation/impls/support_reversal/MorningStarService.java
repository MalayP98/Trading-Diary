package com.trading.diary.services.formation.impls.support_reversal;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.repositories.formations.support.MorningStarRepository;
import com.trading.diary.services.formation.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MorningStarService implements FormationService<MorningStar> {

    private final MorningStarRepository morningStarRepository;

    @Override
    public FormationType getType() {
        return FormationType.MORNING_STAR;
    }

    @Override
    public JpaRepository<MorningStar, Long> getRepository() {
        return morningStarRepository;
    }

}
