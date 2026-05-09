package com.trading.diary.formations.impls.resistance.extension;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.formations.visitor.FormationVisitor;
import com.trading.diary.helpers.SMA;
import com.trading.diary.utils.emums.Strength;
import com.trading.diary.utils.emums.TimeFrame;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class HorizontalResistanceBreakout extends ResistanceBreakout {

    public HorizontalResistanceBreakout(TimeFrame timeFrame, boolean confirmBreakout, Strength breakoutVolume,
                                        boolean allTimeHigh, int touches, long resistanceLength,
                                        boolean higherLows, boolean priorUptrend, float rsi, float breakoutPercentage,
                                        SMA sma20, SMA sma50, SMA sma200) {
        super(confirmBreakout, breakoutVolume, timeFrame, allTimeHigh, touches, resistanceLength, higherLows, priorUptrend, rsi, breakoutPercentage, sma20, sma50, sma200);
    }

    @Override
    public FormationType getFormation() {
        return FormationType.HORIZONTAL_RESISTANCE_BREAKOUT;
    }

    @Override
    public <R> R accept(FormationVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public static HorizontalResistanceBreakoutBuilder builder() {
        return new HorizontalResistanceBreakoutBuilder();
    }

    public static class HorizontalResistanceBreakoutBuilder extends ResistanceBreakout.ResistanceBreakoutBuilder<HorizontalResistanceBreakoutBuilder> {

        public HorizontalResistanceBreakout build() {
            return new HorizontalResistanceBreakout(timeFrame,
                    confirmBreakout, breakoutVolume, allTimeHigh,
                    touches, resistanceLength, higherLows,
                    priorUptrend, rsi, breakoutPercentage, sma20, sma50, sma200);
        }

        @Override
        public HorizontalResistanceBreakoutBuilder self() {
            return this;
        }
    }
}
