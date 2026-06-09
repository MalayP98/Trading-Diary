package com.trading.diary.formations.score.resistance.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.formations.score.AbstractFormationEvaluator;

/**
 * Scores how many times price has tested resistance. Weight: 10. Three or more touches strengthen the level, while two touches are only acceptable when the stock is already at an all-time high with no overhead supply.
 */
public class TouchesEvaluatorFormation extends AbstractFormationEvaluator<ResistanceBreakout> {

    private final double TOUCHES_WEIGHTAGE = 10.0;

    public TouchesEvaluatorFormation(AbstractFormationEvaluator<ResistanceBreakout> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Scores the number of resistance tests, with special handling for the two-touch all-time-high exception from the trading notes.
     */
    @Override
    public double evaluate(ResistanceBreakout formation) {
        int touches = formation.getTouches();
        double score;
        if (touches >= 4) {
            score = 1.0;
        } else if (touches == 3) {
            score = 0.7;
        } else {
            // 2 touches: only acceptable at all-time high per notes Session 9
            // ("2 touch can be considered if the stock is on all time high")
            score = formation.isAllTimeHigh() ? 0.4 : 0.0;
        }
        return TOUCHES_WEIGHTAGE * score;
    }

    @Override
    protected double getWeightage() {
        return TOUCHES_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return null;
    }
}
