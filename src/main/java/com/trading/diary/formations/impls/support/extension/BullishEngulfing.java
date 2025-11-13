package com.trading.diary.formations.impls.support.extension;

import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.utils.Strength;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class BullishEngulfing extends SupportReversal {

    private static final String name = "BULLISH ENGULFING";

    private final boolean partialBottomEngulfing;

    private final boolean partialTopEngulfing;

    private final Strength volume;

    private BullishEngulfing(boolean partialBottomEngulfing, boolean partialTopEngulfing, Strength volume,
                             boolean aboveSupport, boolean belowSupport, int supportLength,
                             boolean priceSustained, boolean retest) {
        super(aboveSupport, belowSupport, supportLength, priceSustained, retest);
        this.partialBottomEngulfing = partialBottomEngulfing;
        this.partialTopEngulfing = partialTopEngulfing;
        this.volume = volume;
    }

    @Override
    public String getFormationName() {
        return name;
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
        public BullishEngulfingBuilder self() {
            return this;
        }

        public BullishEngulfing build() {
            return new BullishEngulfing(partialBottomEngulfing, partialTopEngulfing, volume,
                    aboveSupport, belowSupport, supportLength, priceSustained, retest);
        }
    }
}
