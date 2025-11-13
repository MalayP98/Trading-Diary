package com.trading.diary.formations.impls.support.extension;

import com.trading.diary.formations.impls.support.SupportReversal;
import com.trading.diary.utils.CandleColor;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class Hammer extends SupportReversal {

    private final static String name = "HAMMER";

    private final CandleColor hammerColor;

    // Lower wick is less than 2x of upper wick
    private final boolean smallLowerWick;

    private Hammer(CandleColor hammerColor, boolean smallLowerWick,
                   boolean aboveSupport, boolean belowSupport,
                   int supportLength, boolean priceSustained, boolean retest) {
        super(aboveSupport, belowSupport, supportLength, priceSustained, retest);
        this.hammerColor = hammerColor;
        this.smallLowerWick = smallLowerWick;
    }

    @Override
    public String getFormationName() {
        return name;
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
                    aboveSupport, belowSupport, supportLength,
                    priceSustained, retest);
        }

        @Override
        public HammerBuilder self() {
            return this;
        }
    }
}
