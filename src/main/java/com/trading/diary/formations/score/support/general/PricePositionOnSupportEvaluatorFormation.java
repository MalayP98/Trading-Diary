package com.trading.diary.formations.score.support.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.utils.emums.PricePosition;

import java.util.Map;

public class PricePositionOnSupportFormationEvaluator extends AbstractFormationEvaluator<SupportReversal> {

    private final double PRICE_POSITION_ON_SUPPORT_WEIGHTAGE = 5.0;

    private final Map<PricePosition, Double> PRICE_POSITION_SCORES = Map.of(
            PricePosition.BELOW, 0.0,
            PricePosition.ON, 1.0,
            PricePosition.ABOVE, 0.0,
            PricePosition.THROUGH, 0.5
    );

    public PricePositionOnSupportFormationEvaluator(AbstractFormationEvaluator<SupportReversal> nextEvaluator) {
        super(nextEvaluator);
    }

    @Override
    public double evaluate(SupportReversal formation) {
        return PRICE_POSITION_ON_SUPPORT_WEIGHTAGE * PRICE_POSITION_SCORES.get(formation.getPricePositionOnSupport());
    }

    @Override
    protected double getWeightage() {
        return PRICE_POSITION_ON_SUPPORT_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return null;
    }
}
