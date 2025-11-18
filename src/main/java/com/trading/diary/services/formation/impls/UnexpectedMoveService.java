package com.trading.diary.services.formation.impls;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.UnexpectedMove;
import com.trading.diary.repositories.formations.UnexpectedMoveRepository;
import com.trading.diary.services.formation.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnexpectedMoveService implements FormationService<UnexpectedMove> {

    private final UnexpectedMoveRepository unexpectedMoveRepository;

    @Override
    public FormationType getType() {
        return FormationType.UNEXPECTED_MOVE;
    }

    @Override
    public JpaRepository<UnexpectedMove, Long> getRepository() {
        return unexpectedMoveRepository;
    }

}
