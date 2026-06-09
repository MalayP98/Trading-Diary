package com.trading.diary.formations.score.resistance.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.utils.emums.Strength;

import java.util.Map;

/**
 * Scores the volume expansion on the breakout candle. Weight: 20. Higher volume implies stronger buyer conviction, matching the trading rule that clean breakouts should occur on meaningfully elevated activity.
 */
public class BreakoutVolumeEvaluatorFormation extends AbstractFormationEvaluator<ResistanceBreakout> {

    private final double BREAKOUT_VOLUME_WEIGHTAGE = 20.0;

    private final Map<Strength, Double> VOLUME_TO_SCORE = Map.of(
            Strength.VERY_WEAK, 0.0,
            Strength.WEAK, 0.2,
            Strength.NORMAL, 0.4,
            Strength.STRONG, 0.6,
            Strength.VERY_STRONG, 1.0
    );

    public BreakoutVolumeEvaluatorFormation(AbstractFormationEvaluator<ResistanceBreakout> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Translates qualitative breakout volume into weighted score, rewarding the 2-4x average-volume behaviour described in the trading notes.
     */
    @Override
    public double evaluate(ResistanceBreakout formation) {
        return BREAKOUT_VOLUME_WEIGHTAGE * VOLUME_TO_SCORE.get(formation.getBreakoutVolume());
    }

    @Override
    protected double getWeightage() {
        return BREAKOUT_VOLUME_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return null;
    }
}
