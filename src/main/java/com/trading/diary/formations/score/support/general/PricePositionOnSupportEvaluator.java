package com.trading.diary.formations.score.support.general;

import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.score.FormationEvaluator;
import com.trading.diary.utils.PricePosition;

import java.util.Map;

public class PricePositionOnSupportEvaluator extends FormationEvaluator<SupportReversal> {

    private final double PRICE_POSITION_ON_SUPPORT_WEIGHTAGE = 5.0;

    private final Map<PricePosition, Double> PRICE_POSITION_SCORES = Map.of(
            PricePosition.BELOW, 0.0,
            PricePosition.ON, 1.0,
            PricePosition.ABOVE, 0.0,
            PricePosition.THROUGH, 0.5
    );

    public PricePositionOnSupportEvaluator(FormationEvaluator<SupportReversal> nextEvaluator) {
        super(nextEvaluator);
    }

    @Override
    protected double evaluate(SupportReversal formation) {
        double x = PRICE_POSITION_ON_SUPPORT_WEIGHTAGE * PRICE_POSITION_SCORES.get(formation.getPricePositionOnSupport());
        System.out.println("Price position on support score: " + x);
        return x;
    }
}
