package com.trading.diary.formations.impls.support.extension;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.visitor.FormationVisitor;
import com.trading.diary.utils.emums.CandleColor;
import com.trading.diary.utils.emums.PricePosition;
import com.trading.diary.utils.emums.TimeFrame;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Support-reversal formation for hammer candles. The model tracks hammer colour and whether the lower wick meets the usual two-times-body rule used by the evaluator.
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Hammer extends SupportReversal {

    @NonNull
    private CandleColor hammerColor;

    // Lower wick is less than 2x of real body
    private boolean smallLowerWick;

    private Hammer(TimeFrame timeFrame, CandleColor hammerColor, boolean smallLowerWick,
                   PricePosition pricePositionOnSupport,
                   long supportLength, boolean priceSustained, boolean retest) {
        super(pricePositionOnSupport, timeFrame, supportLength, priceSustained, retest);
        this.hammerColor = hammerColor;
        this.smallLowerWick = smallLowerWick;
    }

    @Override
    public FormationType getFormation() {
        return FormationType.HAMMER;
    }

    @Override
    public <R> R accept(FormationVisitor<R> visitor) {
        return visitor.visit(this);
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
            return new Hammer(timeFrame, hammerColor, smallLowerWick,
                    pricePositionOnSupport, supportLength,
                    priceSustained, retest);
        }

        @Override
        protected HammerBuilder self() {
            return this;
        }
    }
}
