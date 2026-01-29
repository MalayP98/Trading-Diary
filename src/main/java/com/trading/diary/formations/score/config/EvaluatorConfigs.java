package com.trading.diary.formations.score.config;

import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.formations.score.FormationEvaluator;
import com.trading.diary.formations.score.support.extensions.BullishEngulfingEvaluator;
import com.trading.diary.formations.score.support.extensions.HammerEvaluator;
import com.trading.diary.formations.score.support.extensions.MorningStarEvaluator;
import com.trading.diary.formations.score.support.general.PricePositionOnSupportEvaluator;
import com.trading.diary.formations.score.support.general.PriceSustainedEvaluator;
import com.trading.diary.formations.score.support.general.SupportLengthEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EvaluatorConfigs {

    @Bean
    public FormationEvaluator<BullishEngulfing> bullishEngulfingEvaluator(){
        return new BullishEngulfingEvaluator(supportLengthEvaluator());
    }

    @Bean
    public FormationEvaluator<Hammer> hammerEvaluator(){
        return new HammerEvaluator(supportLengthEvaluator());
    }

    @Bean
    public FormationEvaluator<MorningStar> morningStarEvaluator(){
        return new MorningStarEvaluator(supportLengthEvaluator());
    }

    @Bean
    public FormationEvaluator<SupportReversal> supportLengthEvaluator() {
        return new SupportLengthEvaluator(priceSustainedEvaluator());
    }

    @Bean
    public FormationEvaluator<SupportReversal> priceSustainedEvaluator() {
        return new PriceSustainedEvaluator(pricePositionOnSupportEvaluator());
    }

    @Bean
    public FormationEvaluator<SupportReversal> pricePositionOnSupportEvaluator() {
        return new PricePositionOnSupportEvaluator(null);
    }
}
