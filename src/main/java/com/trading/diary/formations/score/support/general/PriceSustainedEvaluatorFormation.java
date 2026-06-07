package com.trading.diary.formations.score.support.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.score.AbstractFormationEvaluator;

public class PriceSustainedFormationEvaluator extends AbstractFormationEvaluator<SupportReversal> {

    private final double PRICE_SUSTAINED_WEIGHTAGE = 15.0;

    public PriceSustainedFormationEvaluator(AbstractFormationEvaluator<SupportReversal> nextEvaluator) {
        super(nextEvaluator);
    }

    @Override
    public double evaluate(SupportReversal formation) {
        return formation.isPriceSustained() ? PRICE_SUSTAINED_WEIGHTAGE : 0.0;
    }

    @Override
    protected double getWeightage() {
        return PRICE_SUSTAINED_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return null;
    }
}
