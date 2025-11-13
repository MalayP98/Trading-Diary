package com.trading.diary.formations.impls.resistance.extension;

import com.trading.diary.formations.impls.resistance.ResistanceBreakout;
import com.trading.diary.helpers.SMA;
import com.trading.diary.utils.Strength;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class HorizontalResistanceBreakout extends ResistanceBreakout {

    private final static String name = "HORIZONTAL RESISTANCE BREAKOUT";

    public HorizontalResistanceBreakout(boolean confirmBreakout, Strength breakoutVolume,
                                        boolean allTimeHigh, int touches, int resistanceLength,
                                        boolean higherLows, float rsi, float breakoutPercentage,
                                        SMA sma20, SMA sma50, SMA sma200) {
        super(confirmBreakout, breakoutVolume, allTimeHigh, touches, resistanceLength, higherLows, rsi, breakoutPercentage, sma20, sma50, sma200);
    }

    @Override
    public String getFormationName() {
        return name;
    }

    public static HorizontalResistanceBreakoutBuilder builder() {
        return new HorizontalResistanceBreakoutBuilder();
    }

    public static class HorizontalResistanceBreakoutBuilder extends ResistanceBreakout.ResistanceBreakoutBuilder<HorizontalResistanceBreakoutBuilder> {

        public HorizontalResistanceBreakout build() {
            return new HorizontalResistanceBreakout(
                    confirmBreakout, breakoutVolume, allTimeHigh,
                    touches, resistanceLength, higherLows,
                    rsi, breakoutPercentage, sma20, sma50, sma200);
        }

        @Override
        public HorizontalResistanceBreakoutBuilder self() {
            return this;
        }
    }
}
