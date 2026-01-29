package com.trading.diary.formations.score;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class FormationEvaluatorFactory {

    private final Map<FormationType, FormationEvaluator<? extends Formation>> evaluatorCollection = new HashMap<>();

    @Autowired
    public FormationEvaluatorFactory(List<FormationEvaluator<? extends Formation>> evaluators) {
        evaluators
                .stream()
                .filter(evaluator -> Objects.nonNull(evaluator.getFormationType()))
                .forEach(evaluator -> evaluatorCollection.put(evaluator.getFormationType(), evaluator));
    }

    public FormationEvaluator<? extends Formation> getEvaluator(FormationType type) {
        return evaluatorCollection.get(type);
    }
}
