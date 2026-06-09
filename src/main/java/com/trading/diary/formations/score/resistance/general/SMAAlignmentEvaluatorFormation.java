package com.trading.diary.formations.score.resistance.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.helpers.SMA;
import com.trading.diary.utils.emums.PricePosition;
import com.trading.diary.utils.emums.TrendlineDirections;

import java.util.Map;

/**
 * Scores alignment with the 20, 50, and 200 SMAs. Weight: 15. Price above rising averages is ideal and the 200-day average carries the most influence because breakouts below it fail far more often.
 */
public class SMAAlignmentEvaluatorFormation extends AbstractFormationEvaluator<ResistanceBreakout> {

    private final double SMA_ALIGNMENT_WEIGHTAGE = 15.0;

    // Longer-period SMAs carry more weight for trend confirmation
    private final double SMA20_WEIGHT = 3.0;
    private final double SMA50_WEIGHT = 5.0;
    private final double SMA200_WEIGHT = 7.0;

    private final Map<PricePosition, Double> PRICE_POSITION_SCORE = Map.of(
            PricePosition.ABOVE, 1.0,
            PricePosition.ON, 0.6,
            PricePosition.THROUGH, 0.5,
            PricePosition.BELOW, 0.0
    );

    // Rising SMA confirms the trend; falling SMA indicates the breakout fights the trend
    private final Map<TrendlineDirections, Double> DIRECTION_MULTIPLIER = Map.of(
            TrendlineDirections.RISING, 1.0,
            TrendlineDirections.HORIZONTAL, 0.7,
            TrendlineDirections.FALLING, 0.3
    );

    public SMAAlignmentEvaluatorFormation(AbstractFormationEvaluator<ResistanceBreakout> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Scores the 20, 50, and 200 SMAs independently and sums them, giving the 200-day average the highest weight because it most strongly reflects trend quality.
     */
    @Override
    public double evaluate(ResistanceBreakout formation) {
        return scoreSMA(formation.getSma20(), SMA20_WEIGHT)
                + scoreSMA(formation.getSma50(), SMA50_WEIGHT)
                + scoreSMA(formation.getSma200(), SMA200_WEIGHT);
    }

    @Override
    protected double getWeightage() {
        return SMA_ALIGNMENT_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return null;
    }

    private double scoreSMA(SMA sma, double weight) {
        double positionScore = PRICE_POSITION_SCORE.getOrDefault(sma.getPricePosition(), 0.0);
        double directionMultiplier = DIRECTION_MULTIPLIER.getOrDefault(sma.getDirection(), 0.7);
        return weight * positionScore * directionMultiplier;
    }
}
