package com.trading.diary.explainers.impls;

import com.trading.diary.explainers.Explainer;
import com.trading.diary.formations.Formation;
import com.trading.diary.formations.visitor.FormationVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Delegates formation descriptions to the visitor layer so each formation type can render its own explanation without leaking switch statements into the UI.
 */
@Service
@RequiredArgsConstructor
public class FormationExplainer implements Explainer<Formation> {

    private final FormationVisitor<String> formationExplanationVisitor;

    @Override
    public String explain(Formation content) {
        return content.accept(formationExplanationVisitor);
    }
}
