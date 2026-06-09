package com.trading.diary.formations.score.resistance.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.formations.score.AbstractFormationEvaluator;

/**
 * Scores whether the breakout is confirmed by a close above resistance. Weight: 20. A wick through resistance does not count as a valid breakout.
 */
public class ConfirmBreakoutEvaluatorFormation extends AbstractFormationEvaluator<ResistanceBreakout> {

    private final double CONFIRM_BREAKOUT_WEIGHTAGE = 20.0;

    public ConfirmBreakoutEvaluatorFormation(AbstractFormationEvaluator<ResistanceBreakout> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Awards points only when the candle actually closes above resistance rather than merely wicking through it.
     */
    @Override
    public double evaluate(ResistanceBreakout formation) {
        return formation.isConfirmBreakout() ? CONFIRM_BREAKOUT_WEIGHTAGE : 0.0;
    }

    @Override
    protected double getWeightage() {
        return CONFIRM_BREAKOUT_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return null;
    }
}
