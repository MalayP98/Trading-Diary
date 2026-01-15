package com.trading.diary.formations.score.support.extensions;

import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.formations.score.FormationEvaluator;
import com.trading.diary.utils.Strength;

import java.util.Map;

public class BullishEngulfingEvaluator extends FormationEvaluator<BullishEngulfing> {

    private final double PARTIAL_ENGULFING_WEIGHTAGE = 15.0;

    private final double VOLUME_WEIGHTAGE = 15.0;

    private final Map<Strength, Double> VOLUME_TO_SCORE = Map.of(
            Strength.VERY_WEAK, 0.0,
            Strength.WEAK, 0.2,
            Strength.STRONG, 0.7,
            Strength.VERY_STRONG, 1.0
    );

    public BullishEngulfingEvaluator(FormationEvaluator<? super BullishEngulfing> nextEvaluator) {
        super(nextEvaluator);
    }

    @Override
    protected double evaluate(BullishEngulfing formation) {
        double x = evaluatePartialEngulfing(formation);
        double y = evaluateVolume(formation);
        System.out.println("Partial engulfing score: " + x);
        System.out.println("Volume score: " + y);
        return x+y;
    }

    private double evaluateVolume(BullishEngulfing formation) {
        return VOLUME_WEIGHTAGE * VOLUME_TO_SCORE.get(formation.getVolume());
    }

    private double evaluatePartialEngulfing(BullishEngulfing formation) {
        if(formation.fullyEngulfed()) {
            return PARTIAL_ENGULFING_WEIGHTAGE;
        }
        return 0.0;
    }
}
