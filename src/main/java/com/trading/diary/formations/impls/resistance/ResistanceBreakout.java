package com.trading.diary.formations.impls.resistance;

import com.trading.diary.formations.Formation;
import com.trading.diary.helpers.SMA;
import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.Strength;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class ResistanceBreakout extends Audit implements Formation {

    private boolean confirmBreakout;

    @Enumerated(EnumType.STRING)
    private Strength breakoutVolume;

    private boolean allTimeHigh;

    private int touches;

    // in days
    private long resistanceLength;

    private boolean higherLows;

    private boolean priorUptrend;

    private float rsi;

    /**
     * How high price closed from breakout price.
     * Example the breakout prices was 100, and the candle
     * closed at 120 so this field will hold 20.
     **/
    private float breakoutPercentage;

    @OneToOne(cascade = CascadeType.ALL)
    private SMA sma20;

    @OneToOne(cascade = CascadeType.ALL)
    private SMA sma50;

    @OneToOne(cascade = CascadeType.ALL)
    private SMA sma200;

    public abstract static class ResistanceBreakoutBuilder<T extends ResistanceBreakoutBuilder<T>> {

        protected boolean confirmBreakout;

        protected Strength breakoutVolume;

        protected boolean allTimeHigh;

        protected int touches;

        protected long resistanceLength;

        protected boolean higherLows;

        protected float rsi;

        protected boolean priorUptrend;

        protected float breakoutPercentage;

        protected SMA sma20;

        protected SMA sma50;

        protected SMA sma200;

        public T confirmBreakout(boolean confirmBreakout) {
            this.confirmBreakout = confirmBreakout;
            return self();
        }

        public T breakoutVolume(Strength breakoutVolume) {
            this.breakoutVolume = breakoutVolume;
            return self();
        }

        public T allTimeHigh(boolean allTimeHigh) {
            this.allTimeHigh = allTimeHigh;
            return self();
        }

        public T touches(int touches) {
            this.touches = touches;
            return self();
        }

        public T resistanceLength(long resistanceLength) {
            this.resistanceLength = resistanceLength;
            return self();
        }

        public T higherLows(boolean higherLows) {
            this.higherLows = higherLows;
            return self();
        }

        public T rsi(float rsi) {
            this.rsi = rsi;
            return self();
        }

        public T priorUptrend(boolean priorUptrend) {
            this.priorUptrend = priorUptrend;
            return self();
        }

        public T breakoutPercentage(float breakoutPercentage) {
            this.breakoutPercentage = breakoutPercentage;
            return self();
        }

        public T sma20(SMA sma20) {
            this.sma20 = sma20;
            return self();
        }

        public T sma50(SMA sma50) {
            this.sma50 = sma50;
            return self();
        }

        public T sma200(SMA sma200) {
            this.sma200 = sma200;
            return self();
        }

        protected abstract T self();
    }
}
