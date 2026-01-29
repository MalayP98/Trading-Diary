package com.trading.diary.formations.score;

import com.trading.diary.formations.Formation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormationEvaluationFacade implements Evaluator<Formation> {

    private final FormationEvaluatorFactory evaluatorFactory;

    @Override
    public double evaluate(Formation formation) {
        AbstractFormationEvaluator<Formation> evaluator = evaluatorFactory.getEvaluator(formation.getFormation());
        if (evaluator == null) {
            return 0.0;
        }
        double evaluationResult = evaluator.evaluateFormation(formation);
        double totalWeightage = evaluator.getTotalWeightage();
        return (evaluationResult / totalWeightage) * 100.0;
    }
}
