package com.trading.diary.formations.score.support.extensions;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.utils.emums.CandleColor;

import java.util.Map;

/**
 * Scores hammer-specific reversal quality. Weight: 30. A proper hammer needs a lower wick at least twice the real body, and green hammers score better than red ones.
 */
public class HammerFormationEvaluator extends AbstractFormationEvaluator<Hammer> {

    private final double LONG_WICK_WEIGHTAGE = 20.0;

    private final double HAMMER_COLOR_WEIGHTAGE = 10.0;

    private final Map<CandleColor, Double> COLOR_TO_SCORE = Map.of(
            CandleColor.GREEN, 1.0,
            CandleColor.RED, 0.5
    );

    public HammerFormationEvaluator(AbstractFormationEvaluator<? super Hammer> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Scores hammer structure by checking the lower-wick rule first and then adjusting for candle colour, with green hammers preferred over red ones.
     */
    @Override
    public double evaluate(Hammer formation) {
        return evaluateWick(formation) + evaluateColor(formation);
    }

    @Override
    protected double getWeightage() {
        return LONG_WICK_WEIGHTAGE + HAMMER_COLOR_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return FormationType.HAMMER;
    }

    private double evaluateWick(Hammer formation) {
        // A proper hammer has a long lower wick (>= 2x real body); small wick is a weaker signal
        return formation.isSmallLowerWick() ? 0.0 : LONG_WICK_WEIGHTAGE;
    }

    private double evaluateColor(Hammer formation) {
        return HAMMER_COLOR_WEIGHTAGE * COLOR_TO_SCORE.get(formation.getHammerColor());
    }
}
