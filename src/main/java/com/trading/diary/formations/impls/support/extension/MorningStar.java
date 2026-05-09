package com.trading.diary.formations.impls.support.extension;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.visitor.FormationVisitor;
import com.trading.diary.utils.emums.CandleColor;
import com.trading.diary.utils.emums.PricePosition;
import com.trading.diary.utils.emums.Strength;
import com.trading.diary.utils.emums.TimeFrame;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MorningStar extends SupportReversal {

    private CandleColor dogiColor;

    // Show the volume of the green candle
    @Enumerated(EnumType.STRING)
    private Strength volume;

    private MorningStar(TimeFrame timeFrame, @NonNull CandleColor dogiColor, @NonNull Strength volume,
                        PricePosition pricePositionOnSupport, long supportLength,
                        boolean priceSustained, boolean retest) {
        super(pricePositionOnSupport, timeFrame, supportLength, priceSustained, retest);
        this.dogiColor = dogiColor;
        this.volume = volume;
    }

    @Override
    public FormationType getFormation() {
        return FormationType.MORNING_STAR;
    }

    @Override
    public <R> R accept(FormationVisitor<R> visitor) {
        return visitor.visit(this);
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
            return new MorningStar(timeFrame, dogiColor, volume,
                    pricePositionOnSupport, supportLength,
                    priceSustained, retest);
        }

        @Override
        protected MorningStarBuilder self() {
            return this;
        }
    }
}
