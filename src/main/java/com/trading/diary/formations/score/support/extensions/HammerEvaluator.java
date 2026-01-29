package com.trading.diary.formations.score.support.extensions;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.formations.score.FormationEvaluator;

public class HammerEvaluator extends FormationEvaluator<Hammer> {

    private final double SMALL_WICK_WEIGHTAGE = 30.0;

    public HammerEvaluator(FormationEvaluator<? super Hammer> nextEvaluator) {
        super(nextEvaluator);
    }

    @Override
    protected double evaluate(Hammer formation) {
        return formation.isSmallLowerWick() ? 0 : SMALL_WICK_WEIGHTAGE;
    }

    @Override
    protected double getWeightage() {
        return SMALL_WICK_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return FormationType.HAMMER;
    }
}
