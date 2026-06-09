package com.trading.diary.formations.score.config;

import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.formations.impls.resistance.extension.FallingResistanceBreakout;
import com.trading.diary.formations.impls.resistance.extension.HorizontalResistanceBreakout;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.formations.score.resistance.extensions.FallingResistanceBreakoutEvaluatorFormation;
import com.trading.diary.formations.score.resistance.extensions.HorizontalResistanceBreakoutEvaluatorFormation;
import com.trading.diary.formations.score.resistance.general.BreakoutQualityEvaluatorFormation;
import com.trading.diary.formations.score.resistance.general.BreakoutVolumeEvaluatorFormation;
import com.trading.diary.formations.score.resistance.general.ConfirmBreakoutEvaluatorFormation;
import com.trading.diary.formations.score.resistance.general.ResistanceLengthEvaluatorFormation;
import com.trading.diary.formations.score.resistance.general.SMAAlignmentEvaluatorFormation;
import com.trading.diary.formations.score.resistance.general.TouchesEvaluatorFormation;
import com.trading.diary.formations.score.resistance.general.TrendContextEvaluatorFormation;
import com.trading.diary.formations.score.support.extensions.BullishEngulfingFormationEvaluator;
import com.trading.diary.formations.score.support.extensions.HammerFormationEvaluator;
import com.trading.diary.formations.score.support.extensions.MorningStarFormationEvaluator;
import com.trading.diary.formations.score.support.general.PricePositionOnSupportFormationEvaluator;
import com.trading.diary.formations.score.support.general.PriceSustainedFormationEvaluator;
import com.trading.diary.formations.score.support.general.RetestEvaluatorFormation;
import com.trading.diary.formations.score.support.general.SupportLengthFormationEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Manually wires evaluator chains for every scored formation. The chains are assembled in code instead of relying on unordered autowiring because evaluator order directly controls how scores and total weightage are accumulated.
 */
@Configuration
public class EvaluatorConfigs {

    // ── Support reversal chains ──────────────────────────────────────────────

    @Bean
    public AbstractFormationEvaluator<BullishEngulfing> bullishEngulfingEvaluator() {
        return new BullishEngulfingFormationEvaluator(supportLengthEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<Hammer> hammerEvaluator() {
        return new HammerFormationEvaluator(supportLengthEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<MorningStar> morningStarEvaluator() {
        return new MorningStarFormationEvaluator(supportLengthEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<SupportReversal> supportLengthEvaluator() {
        return new SupportLengthFormationEvaluator(priceSustainedEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<SupportReversal> priceSustainedEvaluator() {
        return new PriceSustainedFormationEvaluator(pricePositionOnSupportEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<SupportReversal> pricePositionOnSupportEvaluator() {
        return new PricePositionOnSupportFormationEvaluator(retestEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<SupportReversal> retestEvaluator() {
        return new RetestEvaluatorFormation(null);
    }

    // ── Resistance breakout chains ───────────────────────────────────────────

    @Bean
    public AbstractFormationEvaluator<HorizontalResistanceBreakout> horizontalResistanceBreakoutEvaluator() {
        return new HorizontalResistanceBreakoutEvaluatorFormation(confirmBreakoutEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<FallingResistanceBreakout> fallingResistanceBreakoutEvaluator() {
        return new FallingResistanceBreakoutEvaluatorFormation(confirmBreakoutEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<ResistanceBreakout> confirmBreakoutEvaluator() {
        return new ConfirmBreakoutEvaluatorFormation(breakoutVolumeEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<ResistanceBreakout> breakoutVolumeEvaluator() {
        return new BreakoutVolumeEvaluatorFormation(resistanceLengthEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<ResistanceBreakout> resistanceLengthEvaluator() {
        return new ResistanceLengthEvaluatorFormation(touchesEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<ResistanceBreakout> touchesEvaluator() {
        return new TouchesEvaluatorFormation(smaAlignmentEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<ResistanceBreakout> smaAlignmentEvaluator() {
        return new SMAAlignmentEvaluatorFormation(trendContextEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<ResistanceBreakout> trendContextEvaluator() {
        return new TrendContextEvaluatorFormation(breakoutQualityEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<ResistanceBreakout> breakoutQualityEvaluator() {
        return new BreakoutQualityEvaluatorFormation(null);
    }
}
