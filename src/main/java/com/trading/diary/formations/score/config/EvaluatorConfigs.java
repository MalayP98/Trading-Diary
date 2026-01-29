package com.trading.diary.formations.score.config;

import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.formations.score.AbstractFormationEvaluator;
import com.trading.diary.formations.score.support.extensions.BullishEngulfingEvaluatorFormation;
import com.trading.diary.formations.score.support.extensions.HammerEvaluatorFormation;
import com.trading.diary.formations.score.support.extensions.MorningStarEvaluatorFormation;
import com.trading.diary.formations.score.support.general.PricePositionOnSupportEvaluatorFormation;
import com.trading.diary.formations.score.support.general.PriceSustainedEvaluatorFormation;
import com.trading.diary.formations.score.support.general.SupportLengthEvaluatorFormation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EvaluatorConfigs {

    @Bean
    public AbstractFormationEvaluator<BullishEngulfing> bullishEngulfingEvaluator(){
        return new BullishEngulfingEvaluatorFormation(supportLengthEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<Hammer> hammerEvaluator(){
        return new HammerEvaluatorFormation(supportLengthEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<MorningStar> morningStarEvaluator(){
        return new MorningStarEvaluatorFormation(supportLengthEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<SupportReversal> supportLengthEvaluator() {
        return new SupportLengthEvaluatorFormation(priceSustainedEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<SupportReversal> priceSustainedEvaluator() {
        return new PriceSustainedEvaluatorFormation(pricePositionOnSupportEvaluator());
    }

    @Bean
    public AbstractFormationEvaluator<SupportReversal> pricePositionOnSupportEvaluator() {
        return new PricePositionOnSupportEvaluatorFormation(null);
    }
}
