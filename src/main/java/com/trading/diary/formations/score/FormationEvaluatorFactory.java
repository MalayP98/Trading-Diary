package com.trading.diary.formations.score;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Registers the head evaluator for each scored formation type. General-purpose chain nodes report a null type, so only top-level evaluators become lookup entries.
 */
@Component
public class FormationEvaluatorFactory {

    private final Map<FormationType, AbstractFormationEvaluator<Formation>> evaluatorCollection = new HashMap<>();

    public FormationEvaluatorFactory(List<AbstractFormationEvaluator<? extends Formation>> evaluators) {
        for (AbstractFormationEvaluator<? extends Formation> evaluator : evaluators) {
            if (Objects.isNull(evaluator.getFormationType())) {
                continue;
            }
            @SuppressWarnings("unchecked") AbstractFormationEvaluator<Formation> evaluator_ = (AbstractFormationEvaluator<Formation>) evaluator;
            evaluatorCollection.put(evaluator.getFormationType(), evaluator_);
        }
    }

    /**
     * Returns the head of the evaluator chain registered for the requested formation type, or null when the type is not scored.
     */
    public AbstractFormationEvaluator<Formation> getEvaluator(FormationType type) {
        return evaluatorCollection.get(type);
    }
}
