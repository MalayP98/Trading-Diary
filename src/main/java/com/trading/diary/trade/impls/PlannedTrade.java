package com.trading.diary.trade.impls;

import com.trading.diary.trade.AbstractTrade;
import com.trading.diary.utils.TimeFrame;
import jakarta.persistence.Entity;

@Entity
public class PlannedTrade extends AbstractTrade {

    private PlannedTrade(TimeFrame timeFrame, long formationId, float stoploss, String notes) {
        super(timeFrame, formationId, stoploss, notes);
    }

    public static PlannedTradeBuilder builder(){
        return new PlannedTradeBuilder();
    }

    public static class PlannedTradeBuilder extends AbstractTradeBuilder<PlannedTradeBuilder> {

        public PlannedTrade build() {
            return new PlannedTrade(timeFrame, formationId, stoploss, notes);
        }

        @Override
        protected PlannedTradeBuilder self() {
            return this;
        }
    }

}
