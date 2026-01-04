package com.trading.diary.helpers;

import com.trading.diary.formations.Formation;
import com.trading.diary.services.formation.FormationServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Explainer {

    private final FormationServiceFactory<? extends Formation> formationServiceFactory;

    public <R> int explain(R content) {
        return 1;
    }
}
