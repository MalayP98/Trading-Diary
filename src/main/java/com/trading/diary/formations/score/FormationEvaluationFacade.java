package com.trading.diary.formations.score;

import com.trading.diary.formations.Formation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Entry point for formation scoring. It resolves the correct evaluator chain for a formation type, executes the chain, and normalizes the raw weighted score to a percentage.
 */
@Component
@RequiredArgsConstructor
public class FormationEvaluationFacade implements Evaluator<Formation> {

    private final FormationEvaluatorFactory evaluatorFactory;

    /**
     * Scores the supplied formation with its evaluator chain and normalizes the raw weighted total to a percentage of the chain's total weightage.
     */
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
