package com.trading.diary.formations.impls.support;

import com.trading.diary.formations.Formation;
import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.PricePosition;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class SupportReversal extends Audit implements Formation {

    @Enumerated(EnumType.STRING)
    private PricePosition pricePositionOnSupport;

    // in days
    private long supportLength;

    private boolean priceSustained;

    private boolean retest;

    public boolean onSupport() {
        return PricePosition.ON.equals(pricePositionOnSupport);
    }

    public abstract static class SupportReversalBuilder<T extends SupportReversalBuilder<T>> {

        protected PricePosition pricePositionOnSupport;

        protected long supportLength;

        protected boolean priceSustained;

        protected boolean retest;

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

        protected abstract T self();
    }
}
