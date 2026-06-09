package com.trading.diary.formations.score.support.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.score.AbstractFormationEvaluator;

/**
 * Scores whether price held above support after the reversal signal. Weight: 15. The setup only earns credit when support remains defended after the candle forms.
 */
public class PriceSustainedFormationEvaluator extends AbstractFormationEvaluator<SupportReversal> {

    private final double PRICE_SUSTAINED_WEIGHTAGE = 15.0;

    public PriceSustainedFormationEvaluator(AbstractFormationEvaluator<SupportReversal> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Awards points only when price continued to hold above support after the reversal candle, confirming that the level actually held.
     */
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
