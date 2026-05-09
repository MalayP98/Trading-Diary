package com.trading.diary.formations.score.support.extensions;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.utils.emums.Strength;

import java.util.Map;

public class BullishEngulfingEvaluatorFormation extends AbstractFormationEvaluator<BullishEngulfing> {

    private final double PARTIAL_ENGULFING_WEIGHTAGE = 15.0;

    private final double VOLUME_WEIGHTAGE = 15.0;

    private final Map<Strength, Double> VOLUME_TO_SCORE = Map.of(
            Strength.VERY_WEAK, 0.0,
            Strength.WEAK, 0.2,
            Strength.STRONG, 0.6,
            Strength.VERY_STRONG, 1.0
    );

    public BullishEngulfingEvaluatorFormation(AbstractFormationEvaluator<? super BullishEngulfing> nextEvaluator) {
        super(nextEvaluator);
    }

    @Override
    public double evaluate(BullishEngulfing formation) {
        return evaluatePartialEngulfing(formation) + evaluateVolume(formation);
    }

    @Override
    protected double getWeightage() {
        return PARTIAL_ENGULFING_WEIGHTAGE + VOLUME_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return FormationType.BULLISH_ENGULFING;
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
