package com.trading.diary.formations.score.support.general;

import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.score.FormationEvaluator;

public class PriceSustainedEvaluator extends FormationEvaluator<SupportReversal> {

    private final double PRICE_SUSTAINED_WEIGHTAGE = 15.0;

    public PriceSustainedEvaluator(FormationEvaluator<SupportReversal> nextEvaluator) {
        super(nextEvaluator);
    }

    @Override
    protected double evaluate(SupportReversal formation) {
        return formation.isPriceSustained() ? PRICE_SUSTAINED_WEIGHTAGE : 0.0;
    }
}
