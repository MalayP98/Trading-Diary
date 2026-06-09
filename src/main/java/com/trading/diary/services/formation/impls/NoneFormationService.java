package com.trading.diary.services.formation.impls;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.NoneFormation;
import com.trading.diary.repositories.formations.NoneFormationRepository;
import com.trading.diary.services.formation.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoneFormationService implements FormationService<NoneFormation> {

    private final NoneFormationRepository noneFormationRepository;

    @Override
    public FormationType getType() {
        return FormationType.NONE;
    }

     @Override
    public JpaRepository<NoneFormation, Long> getRepository() {
        return noneFormationRepository;
    }
}
