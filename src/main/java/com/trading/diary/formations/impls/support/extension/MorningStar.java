package com.trading.diary.formations.impls.support.extension;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.visitor.FormationVisitor;
import com.trading.diary.utils.CandleColor;
import com.trading.diary.utils.PricePosition;
import com.trading.diary.utils.Strength;
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

    @NonNull
    private CandleColor dogiColor;

    // Show the volume of the green candle
    @NonNull
    @Enumerated(EnumType.STRING)
    private Strength volume;

    private MorningStar(CandleColor dogiColor, Strength volume,
                        PricePosition pricePositionOnSupport, long supportLength,
                        boolean priceSustained, boolean retest) {
        super(pricePositionOnSupport, supportLength, priceSustained, retest);
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
            return new MorningStar(dogiColor, volume,
                    pricePositionOnSupport, supportLength,
                    priceSustained, retest);
        }

        @Override
        protected MorningStarBuilder self() {
            return this;
        }
    }
}
