package com.trading.diary.formations.impls.support;

import com.trading.diary.formations.Formation;
import com.trading.diary.pojo.Audit;
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

    private boolean aboveSupport;

    private boolean belowSupport;

    // in days
    private int supportLength;

    private boolean priceSustained;

    private boolean retest;

    public boolean onSupport() {
        return !aboveSupport && !belowSupport;
    }

    public abstract static class SupportReversalBuilder<T extends SupportReversalBuilder<T>> {

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
