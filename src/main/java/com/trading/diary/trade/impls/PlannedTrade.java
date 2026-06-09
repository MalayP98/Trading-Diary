package com.trading.diary.trade.impls;

import com.trading.diary.formations.FormationType;
import com.trading.diary.helpers.Target;
import com.trading.diary.pojo.Company;
import com.trading.diary.pojo.MarketCap;
import com.trading.diary.pojo.Person;
import com.trading.diary.trade.AbstractTrade;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a trade idea that has not been executed yet. It reuses the common trade metadata but intentionally omits execution-specific fields such as shares and fill price.
 */
@Entity
@NoArgsConstructor
public class PlannedTrade extends AbstractTrade {

    private PlannedTrade(Company company, long formationId, FormationType formationType, MarketCap marketCap, Person suggestedBy, String notes, List<Target> target, List<Target> stoploss) {
        super(company, formationId, formationType, stoploss, target, marketCap, suggestedBy, notes);
    }

    public static PlannedTradeBuilder builder() {
        return new PlannedTradeBuilder();
    }

    public static class PlannedTradeBuilder extends AbstractTradeBuilder<PlannedTradeBuilder, PlannedTrade> {

        @Override
        public PlannedTrade build() {
            return new PlannedTrade(company, formationId, formationType, marketCap, suggestedBy, notes, target, stoploss);
        }

        @Override
        protected PlannedTradeBuilder self() {
            return this;
        }
    }
}
