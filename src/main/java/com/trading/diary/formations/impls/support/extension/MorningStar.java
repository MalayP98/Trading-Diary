package com.trading.diary.formations.impls.support.extension;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.utils.CandleColor;
import com.trading.diary.utils.Strength;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MorningStar extends SupportReversal {

    private CandleColor dogiColor;

    private Strength volume;

    private MorningStar(CandleColor dogiColor, Strength volume,
                        boolean aboveSupport,boolean belowSupport,
                       int supportLength, boolean priceSustained, boolean retest) {
        super(aboveSupport, belowSupport, supportLength, priceSustained, retest);
        this.dogiColor = dogiColor;
        this.volume = volume;
    }

    @Override
    public FormationType getFormation() {
        return FormationType.MORNING_STAR;
    }

    public static MorningStarBuilder builder() {
        return new MorningStarBuilder();
    }

    public static class MorningStarBuilder extends SupportReversal.SupportReversalBuilder<MorningStarBuilder> {

        private CandleColor dogiColor;

        private Strength volume;

        public MorningStarBuilder dogiColor(CandleColor dogiColor) {
            this.dogiColor = dogiColor;
            return this;
        }

        public MorningStarBuilder volume(Strength volume) {
            this.volume = volume;
            return this;
        }

        public MorningStar build() {
            return new MorningStar(dogiColor, volume,
                    aboveSupport, belowSupport, supportLength,
                    priceSustained, retest);
        }

        @Override
        protected MorningStarBuilder self() {
            return this;
        }
    }
}
