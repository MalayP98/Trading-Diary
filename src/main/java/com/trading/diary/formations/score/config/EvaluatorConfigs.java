package com.trading.diary.formations.score.config;

import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.formations.score.support.extensions.BullishEngulfingFormationEvaluator;
import com.trading.diary.formations.score.support.extensions.HammerFormationEvaluator;
import com.trading.diary.formations.score.support.extensions.MorningStarFormationEvaluator;
import com.trading.diary.formations.score.support.general.PricePositionOnSupportFormationEvaluator;
import com.trading.diary.formations.score.support.general.PriceSustainedFormationEvaluator;
import com.trading.diary.formations.score.support.general.SupportLengthFormationEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EvaluatorConfigs {

    @Bean
    public AbstractFormationEvaluator<BullishEngulfing> bullishEngulfingEvaluator(){
        return new BullishEngulfingFormationEvaluator(supportLengthEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<Hammer> hammerEvaluator(){
        return new HammerFormationEvaluator(supportLengthEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<MorningStar> morningStarEvaluator(){
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
        return new PricePositionOnSupportFormationEvaluator(null);
    }
}
