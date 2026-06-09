package com.trading.diary.formations.score.support.extensions;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.utils.emums.CandleColor;
import com.trading.diary.utils.emums.Strength;

import java.util.Map;

/**
 * Scores morning-star details beyond the shared support criteria. Weight: 30. Volume on the final green candle matters most, and a green doji is preferred to a red doji.
 */
public class MorningStarFormationEvaluator extends AbstractFormationEvaluator<MorningStar> {

    private final double VOLUME_WEIGHTAGE = 25.0;

    private final double DOGI_COLOR_WEIGHTAGE = 5.0;

    private final Map<Strength, Double> VOLUME_TO_SCORE = Map.of(
            Strength.VERY_WEAK, 0.0,
            Strength.WEAK, 0.2,
            Strength.NORMAL, 0.4,
            Strength.STRONG, 0.6,
            Strength.VERY_STRONG, 1.0
    );

    // A green dogi signals a slight bullish lean during indecision; red signals bearish lean
    private final Map<CandleColor, Double> DOGI_COLOR_TO_SCORE = Map.of(
            CandleColor.GREEN, 1.0,
            CandleColor.RED, 0.5
    );

    public MorningStarFormationEvaluator(AbstractFormationEvaluator<? super MorningStar> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Scores the green-candle volume and doji colour, with volume carrying most of the weight and green doji candles receiving the stronger bias.
     */
    @Override
    public double evaluate(MorningStar formation) {
        return evaluateVolume(formation) + evaluateDogiColor(formation);
    }

    @Override
    protected double getWeightage() {
        return VOLUME_WEIGHTAGE + DOGI_COLOR_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return FormationType.MORNING_STAR;
    }

    private double evaluateVolume(MorningStar formation) {
        return VOLUME_WEIGHTAGE * VOLUME_TO_SCORE.get(formation.getVolume());
    }

    private double evaluateDogiColor(MorningStar formation) {
        return DOGI_COLOR_WEIGHTAGE * DOGI_COLOR_TO_SCORE.get(formation.getDogiColor());
    }
}
