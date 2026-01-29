package com.trading.diary.formations.score;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;

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

    protected abstract double getWeightage();

    public double getTotalWeightage() {
        double weightage = getWeightage();
        if (nextEvaluator != null) {
            weightage += nextEvaluator.getTotalWeightage();
        }
        return weightage;
    }

    public abstract FormationType getFormationType();
}
