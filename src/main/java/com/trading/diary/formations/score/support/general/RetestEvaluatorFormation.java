package com.trading.diary.formations.score.support.general;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.score.AbstractFormationEvaluator;

/**
 * Scores whether a broken resistance was later retested as support. Weight: 15. Retests improve entry quality by giving better risk-reward and confirming the level flip.
 */
public class RetestEvaluatorFormation extends AbstractFormationEvaluator<SupportReversal> {

    private final double RETEST_WEIGHTAGE = 15.0;

    public RetestEvaluatorFormation(AbstractFormationEvaluator<SupportReversal> nextEvaluator) {
        super(nextEvaluator);
    }

    /**
     * Rewards the additional confirmation and cleaner risk-reward that comes from buying after a successful retest.
     */
    @Override
    public double evaluate(SupportReversal formation) {
        return formation.isRetest() ? RETEST_WEIGHTAGE : 0.0;
    }

    @Override
    protected double getWeightage() {
        return RETEST_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return null;
    }
}
