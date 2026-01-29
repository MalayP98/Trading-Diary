package com.trading.diary.formations.score.support.extensions;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.utils.Strength;

import java.util.Map;

public class MorningStarEvaluatorFormation extends AbstractFormationEvaluator<MorningStar> {

    private final double VOLUME_WEIGHTAGE = 30.0;

    private final Map<Strength, Double> VOLUME_TO_SCORE = Map.of(
            Strength.VERY_WEAK, 0.0,
            Strength.WEAK, 0.2,
            Strength.STRONG, 0.6,
            Strength.VERY_STRONG, 1.0
    );


    public MorningStarEvaluatorFormation(AbstractFormationEvaluator<? super MorningStar> nextEvaluator) {
        super(nextEvaluator);
    }

    @Override
    public double evaluate(MorningStar formation) {
        return VOLUME_WEIGHTAGE * VOLUME_TO_SCORE.get(formation.getVolume());
    }

    @Override
    protected double getWeightage() {
        return VOLUME_WEIGHTAGE;
    }

    @Override
    public FormationType getFormationType() {
        return FormationType.MORNING_STAR;
    }
}
