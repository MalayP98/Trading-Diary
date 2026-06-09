package com.trading.diary.formations.score.resistance.extensions;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.extension.HorizontalResistanceBreakout;
import com.trading.diary.formations.score.AbstractFormationEvaluator;

/**
 * Marker evaluator for horizontal resistance breakouts. Weight: 0. MOTHER patterns do not add subtype-specific criteria because the shared breakout chain already covers the full rule set.
 */
public class HorizontalResistanceBreakoutEvaluatorFormation extends AbstractFormationEvaluator<HorizontalResistanceBreakout> {

    public HorizontalResistanceBreakoutEvaluatorFormation(AbstractFormationEvaluator<? super HorizontalResistanceBreakout> nextEvaluator) {
        super(nextEvaluator);
    }

    // HorizontalResistanceBreakout has no fields beyond the base ResistanceBreakout.
    // Scoring is handled entirely by the general evaluator chain.
    /**
     * Returns zero because horizontal resistance breakouts are scored entirely by the shared breakout evaluator chain.
     */
    @Override
    public double evaluate(HorizontalResistanceBreakout formation) {
        return 0.0;
    }

    @Override
    protected double getWeightage() {
        return 0.0;
    }

    @Override
    public FormationType getFormationType() {
        return FormationType.HORIZONTAL_RESISTANCE_BREAKOUT;
    }
}
