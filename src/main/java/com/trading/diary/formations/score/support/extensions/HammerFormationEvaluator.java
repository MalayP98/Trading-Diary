package com.trading.diary.formations.score.support.extensions;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.formations.score.AbstractFormationEvaluator;

public class HammerFormationEvaluator extends AbstractFormationEvaluator<Hammer> {

    private final double SMALL_WICK_WEIGHTAGE = 30.0;

    public HammerFormationEvaluator(AbstractFormationEvaluator<? super Hammer> nextEvaluator) {
        super(nextEvaluator);
    }

    @Override
    public double evaluate(Hammer formation) {
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
