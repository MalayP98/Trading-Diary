package com.trading.diary.formations.score;

import com.trading.diary.formations.Formation;

public abstract class FormationEvaluator<T extends Formation> {

    private final FormationEvaluator<? super T> nextEvaluator;

    protected FormationEvaluator(FormationEvaluator<? super T> nextEvaluator) {
        this.nextEvaluator = nextEvaluator;
    }

    public double evaluateFormation(T formation) {
        double score = evaluate(formation);
        if (nextEvaluator != null) {
            score += nextEvaluator.evaluateFormation(formation);
        }
        return score;
    }

    protected abstract double evaluate(T formation);
}
