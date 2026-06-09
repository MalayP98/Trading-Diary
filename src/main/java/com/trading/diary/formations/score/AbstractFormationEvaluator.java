package com.trading.diary.formations.score;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;

/**
 * Base class for formation evaluators that implements the chain-of-responsibility pattern. Each node scores one criterion, then forwards the same formation to the next evaluator so raw scores and total weightage can be accumulated consistently.
 */
public abstract class AbstractFormationEvaluator<T extends Formation> implements Evaluator<T> {

    private final AbstractFormationEvaluator<? super T> nextEvaluator;

    protected AbstractFormationEvaluator(AbstractFormationEvaluator<? super T> nextEvaluator) {
        this.nextEvaluator = nextEvaluator;
    }

    /**
     * Evaluates this criterion and then delegates to the rest of the chain, returning the accumulated raw score for the formation.
     */
    public double evaluateFormation(T formation) {
        double score = evaluate(formation);
        if (nextEvaluator != null) {
            score += nextEvaluator.evaluateFormation(formation);
        }
        return score;
    }

    protected abstract double getWeightage();

    /**
     * Traverses the same evaluator chain to compute the total possible score used later for percentage normalization.
     */
    public double getTotalWeightage() {
        double weightage = getWeightage();
        if (nextEvaluator != null) {
            weightage += nextEvaluator.getTotalWeightage();
        }
        return weightage;
    }

    public abstract FormationType getFormationType();
}
