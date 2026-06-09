package com.trading.diary.formations.score.support.extensions;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.utils.emums.Strength;

import java.util.Map;

/**
 * Scores bullish-engulfing rules beyond the shared support criteria. Weight: 30. Fully engulfed candles score best, partial engulfing earns partial credit, and strong volume on the engulfing candle is essential.
 */
public class BullishEngulfingFormationEvaluator extends AbstractFormationEvaluator<BullishEngulfing> {

    private final double PARTIAL_ENGULFING_WEIGHTAGE = 15.0;

    private final double VOLUME_WEIGHTAGE = 15.0;

    private final Map<Strength, Double> VOLUME_TO_SCORE = Map.of(
            Strength.VERY_WEAK, 0.0,
            Strength.WEAK, 0.2,
            Strength.NORMAL, 0.4,
            Strength.STRONG, 0.6,
            Strength.VERY_STRONG, 1.0
    );

    public BullishEngulfingFormationEvaluator(AbstractFormationEvaluator<? super BullishEngulfing> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Scores engulfing completeness and confirmation volume, reflecting the rule that full engulfing with strong volume is the highest-quality signal.
     */
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
        if (formation.fullyEngulfed()) {
            return PARTIAL_ENGULFING_WEIGHTAGE;
        }
        boolean onlyOnePartial = formation.isPartialBottomEngulfing() ^ formation.isPartialTopEngulfing();
        if (onlyOnePartial) {
            return PARTIAL_ENGULFING_WEIGHTAGE * 0.5;
        }
        return 0.0;
    }
}
