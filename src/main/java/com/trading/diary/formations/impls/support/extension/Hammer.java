package com.trading.diary.formations.impls.support.extension;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.utils.CandleColor;
import com.trading.diary.utils.PricePosition;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Hammer extends SupportReversal {

    @NonNull
    private CandleColor hammerColor;

    // Lower wick is less than 2x of upper wick
    private boolean smallLowerWick;

    private Hammer(CandleColor hammerColor, boolean smallLowerWick,
                   PricePosition pricePositionOnSupport,
                   long supportLength, boolean priceSustained, boolean retest) {
        super(pricePositionOnSupport, supportLength, priceSustained, retest);
        this.hammerColor = hammerColor;
        this.smallLowerWick = smallLowerWick;
    }

    @Override
    public FormationType getFormation() {
        return FormationType.HAMMER;
    }

    @Override
    public String explain() {
        return getFormation().name() + ": Support Length " + getSupportLength() +
                (smallLowerWick ? "with" : "without") + " a good lower wick " + hammerColor + " candle." ;
    }

    public static HammerBuilder builder() {
        return new HammerBuilder();
    }

    public static class HammerBuilder extends SupportReversal.SupportReversalBuilder<HammerBuilder> {

        private CandleColor hammerColor;

        private boolean smallLowerWick;

        public HammerBuilder hammerColor(CandleColor hammerColor) {
            this.hammerColor = hammerColor;
            return this;
        }

        public HammerBuilder smallLowerWick(boolean smallLowerWick) {
            this.smallLowerWick = smallLowerWick;
            return this;
        }

        public Hammer build() {
            return new Hammer(hammerColor, smallLowerWick,
                    pricePositionOnSupport, supportLength,
                    priceSustained, retest);
        }

        @Override
        protected HammerBuilder self() {
            return this;
        }
    }
}
