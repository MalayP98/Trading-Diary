package com.trading.diary.formations.impls.support.extension;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.utils.Strength;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BullishEngulfing extends SupportReversal {

    private boolean partialBottomEngulfing;

    private boolean partialTopEngulfing;

    private Strength volume;

    private BullishEngulfing(boolean partialBottomEngulfing, boolean partialTopEngulfing, Strength volume,
                             boolean aboveSupport, boolean belowSupport, int supportLength,
                             boolean priceSustained, boolean retest) {
        super(aboveSupport, belowSupport, supportLength, priceSustained, retest);
        this.partialBottomEngulfing = partialBottomEngulfing;
        this.partialTopEngulfing = partialTopEngulfing;
        this.volume = volume;
    }

    @Override
    public FormationType getFormation() {
        return FormationType.BULLISH_ENGULFING;
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
            return new BullishEngulfing(partialBottomEngulfing, partialTopEngulfing, volume,
                    aboveSupport, belowSupport, supportLength, priceSustained, retest);
        }
    }
}
