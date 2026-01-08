package com.trading.diary.explainers.impls;

import com.trading.diary.explainers.Explainer;
import com.trading.diary.formations.Formation;
import com.trading.diary.formations.visitor.FormationVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormationExplainer implements Explainer<Formation> {

    private final FormationVisitor<String> formationExplanationVisitor;

    @Override
    public String explain(Formation content) {
        return content.accept(formationExplanationVisitor);
    }
}
