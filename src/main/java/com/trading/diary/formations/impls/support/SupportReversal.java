package com.trading.diary.formations.impls.support;

import com.trading.diary.formations.Formation;
import com.trading.diary.pojo.Audit;
import jakarta.persistence.MappedSuperclass;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@MappedSuperclass
public abstract class SupportReversal extends Audit implements Formation {

    private final boolean aboveSupport;

    private final boolean belowSupport;

    // in days
    private final int supportLength;

    private final boolean priceSustained;

    private final boolean retest;

    public boolean onSupport() {
        return !aboveSupport && !belowSupport;
    }

    protected abstract static class SupportReversalBuilder<T extends SupportReversalBuilder<T>> {

        protected boolean aboveSupport;

        protected boolean belowSupport;

        protected int supportLength;

        protected boolean priceSustained;

        protected boolean retest;

        public T aboveSupport(boolean aboveSupport) {
            this.aboveSupport = aboveSupport;
            return self();
        }

        public T belowSupport(boolean belowSupport) {
            this.belowSupport = belowSupport;
            return self();
        }

        public T supportLength(int supportLength) {
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
