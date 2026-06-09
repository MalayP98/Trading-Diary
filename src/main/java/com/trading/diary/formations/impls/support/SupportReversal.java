package com.trading.diary.formations.impls.support;

import com.trading.diary.formations.Formation;
import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.emums.PricePosition;
import com.trading.diary.utils.emums.TimeFrame;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import javax.validation.constraints.Min;

/**
 * Base state shared by support-reversal formations such as hammer, bullish engulfing, and morning star. It captures level location, level age, post-candle hold, and retest context used by the support scoring chain.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class SupportReversal extends Audit implements Formation {

    @NonNull
    @Enumerated(EnumType.STRING)
    private PricePosition pricePositionOnSupport;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TimeFrame timeFrame;

    // in days
    @Min(value = 1, message = "Support length cannot be less than 1")
    private long supportLength;

    private boolean priceSustained;

    private boolean retest;

    /**
     * Returns whether price finished directly on the support level, which is the ideal location for a support-reversal signal.
     */
    public boolean onSupport() {
        return PricePosition.ON.equals(pricePositionOnSupport);
    }

    /**
     * Reusable builder base for support-reversal formations so hammer, bullish engulfing, and morning star share the same core context fields.
     */
    public abstract static class SupportReversalBuilder<T extends SupportReversalBuilder<T>> {

        protected PricePosition pricePositionOnSupport;

        protected long supportLength;

        protected boolean priceSustained;

        protected boolean retest;

        protected TimeFrame timeFrame;

        public T pricePositionOnSupport(PricePosition pricePositionOnSupport){
            this.pricePositionOnSupport = pricePositionOnSupport;
            return self();
        }

        public T supportLength(long supportLength) {
            this.supportLength = supportLength;
            return self();
        }

        public T priceSustained(boolean priceSustained) {
            this.priceSustained = priceSustained;
            return self();
        }

        public T retest(boolean retest) {
            this.retest = retest;
            return self();
        }

        public T timeFrame(TimeFrame timeFrame) {
            this.timeFrame = timeFrame;
            return self();
        }

        protected abstract T self();
    }
}
