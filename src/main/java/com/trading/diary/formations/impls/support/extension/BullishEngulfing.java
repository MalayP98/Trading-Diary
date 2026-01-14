package com.trading.diary.formations.impls.support.extension;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.formations.visitor.FormationVisitor;
import com.trading.diary.utils.PricePosition;
import com.trading.diary.utils.Strength;
import com.trading.diary.utils.TimeFrame;
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
public class BullishEngulfing extends SupportReversal {

    private boolean partialBottomEngulfing;

    private boolean partialTopEngulfing;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Strength volume;

    private BullishEngulfing(TimeFrame timeFrame, boolean partialBottomEngulfing, boolean partialTopEngulfing, Strength volume,
                             PricePosition pricePositionOnSupport, long supportLength, boolean priceSustained,
                             boolean retest) {
        super(pricePositionOnSupport, timeFrame, supportLength, priceSustained, retest);
        this.partialBottomEngulfing = partialBottomEngulfing;
        this.partialTopEngulfing = partialTopEngulfing;
        this.volume = volume;
    }

    @Override
    public FormationType getFormation() {
        return FormationType.BULLISH_ENGULFING;
    }

    @Override
    public <R> R accept(FormationVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public boolean fullyEngulfed() {
        return !partialBottomEngulfing && !partialTopEngulfing;
    }

    public static BullishEngulfingBuilder builder() {
        return new BullishEngulfingBuilder();
    }

    public static class BullishEngulfingBuilder extends SupportReversal.SupportReversalBuilder<BullishEngulfingBuilder> {

        private boolean partialBottomEngulfing;

        private boolean partialTopEngulfing;

        private Strength volume;

        public BullishEngulfingBuilder partialBottomEngulfing(boolean partialBottomEngulfing) {
            this.partialBottomEngulfing = partialBottomEngulfing;
            return this;
        }

        public BullishEngulfingBuilder partialTopEngulfing(boolean partialTopEngulfing) {
            this.partialTopEngulfing = partialTopEngulfing;
            return this;
        }

        public BullishEngulfingBuilder volume(Strength volume) {
            this.volume = volume;
            return this;
        }

        @Override
        protected BullishEngulfingBuilder self() {
            return this;
        }

        public BullishEngulfing build() {
            return new BullishEngulfing(timeFrame, partialBottomEngulfing, partialTopEngulfing, volume,
                    pricePositionOnSupport, supportLength, priceSustained, retest);
        }
    }
}
