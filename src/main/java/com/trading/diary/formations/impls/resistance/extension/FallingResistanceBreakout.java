package com.trading.diary.formations.impls.resistance.extension;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.helpers.SMA;
import com.trading.diary.utils.Strength;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class FallingResistanceBreakout extends ResistanceBreakout {

    private float angle;

    // Difference between start of resistance line and breakout price in percentage
    private float priceDiffPercentage;

    public FallingResistanceBreakout(float angle, boolean priorUptrend,
                                     boolean confirmBreakout, Strength breakoutVolume,
                                     boolean allTimeHigh, int touches, int resistanceLength,
                                     boolean higherLows, float rsi, float breakoutPercentage,
                                     SMA sma20, SMA sma50, SMA sma200, float priceDiffPercentage) {
        super(confirmBreakout, breakoutVolume, allTimeHigh, touches, resistanceLength, higherLows, priorUptrend, rsi, breakoutPercentage, sma20, sma50, sma200);
        this.angle = angle;
        this.priceDiffPercentage = priceDiffPercentage;
    }

    @Override
    public FormationType getFormation() {
        return FormationType.FALLING_RESISTANCE_BREAKOUT;
    }

    public static FallingResistanceBreakoutBuilder builder() {
        return new FallingResistanceBreakoutBuilder();
    }

    public static class FallingResistanceBreakoutBuilder extends ResistanceBreakout.ResistanceBreakoutBuilder<FallingResistanceBreakoutBuilder> {

        private float angle;

        private boolean priorUptrend;

        private float priceDiffPercentage;

        public FallingResistanceBreakoutBuilder angle(float angle) {
            this.angle = angle;
            return this;
        }

        public FallingResistanceBreakoutBuilder priorUptrend(boolean priorUptrend) {
            this.priorUptrend = priorUptrend;
            return this;
        }

        public FallingResistanceBreakoutBuilder priceDiffPercentage(float priceDiffPercentage) {
            this.priceDiffPercentage = priceDiffPercentage;
            return this;
        }

        public FallingResistanceBreakout build() {
            return new FallingResistanceBreakout(
                    angle, priorUptrend,
                    confirmBreakout, breakoutVolume, allTimeHigh,
                    touches, resistanceLength, higherLows,
                    rsi, breakoutPercentage, sma20, sma50, sma200,
                    priceDiffPercentage);
        }

        @Override
        protected FallingResistanceBreakoutBuilder self() {
            return this;
        }
    }
}
