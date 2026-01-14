package com.trading.diary.trade.impls;

import com.trading.diary.formations.FormationType;
import com.trading.diary.helpers.Target;
import com.trading.diary.pojo.Company;
import com.trading.diary.pojo.MarketCap;
import com.trading.diary.pojo.Person;
import com.trading.diary.trade.AbstractTrade;
import com.trading.diary.utils.TradeState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Trade extends AbstractTrade {

    @Enumerated(EnumType.STRING)
    private TradeState state = TradeState.OPEN;

    private int shares;

    private float averageBuyingPrice;

    private Float averageClosingPrice;

    // earliest buying day
    private LocalDateTime openingDate;

    private LocalDateTime closingDate;

    private Trade(Company company, long formationId, FormationType formationType,
                  String notes, LocalDateTime openingDate,
                  TradeState state, int shares, float averageBuyingPrice,
                  LocalDateTime sellingDate, MarketCap marketCap, Person suggestedBy,
                  List<Target> target, List<Target> stoploss) {
        super(company, formationId, formationType, stoploss, target, marketCap, suggestedBy, notes);
        this.openingDate = openingDate;
        this.state = state;
        this.shares = shares;
        this.averageBuyingPrice = averageBuyingPrice;
        this.closingDate = sellingDate;
    }

    private Trade(LocalDateTime openingDate,
                  TradeState state, int shares, float averageBuyingPrice,
                  LocalDateTime sellingDate, PlannedTrade plannedTrade) {
        super(plannedTrade.getCompany(), plannedTrade.getFormationId(),
                plannedTrade.getFormationType(), plannedTrade.getStoploss(), plannedTrade.getTargets(),
                plannedTrade.getMarketCap(), plannedTrade.getSuggestedBy(), plannedTrade.getNotes());
        this.openingDate = openingDate;
        this.state = state;
        this.shares = shares;
        this.averageBuyingPrice = averageBuyingPrice;
        this.closingDate = sellingDate;
    }

    public static SimpleTradeBuilder builder() {
        return new SimpleTradeBuilder();
    }

    public static class SimpleTradeBuilder extends AbstractTradeBuilder<SimpleTradeBuilder, Trade> {

        protected int shares;

        protected float averageBuyingPrice;

        // earliest buying day
        protected LocalDateTime openingDate;

        public SimpleTradeBuilder shares(int shares) {
            this.shares = shares;
            return this;
        }

        public SimpleTradeBuilder averageBuyingPrice(float averageBuyingPrice) {
            this.averageBuyingPrice = averageBuyingPrice;
            return this;
        }

        public SimpleTradeBuilder openingDate(LocalDateTime openingDate) {
            this.openingDate = openingDate;
            return this;
        }

        @Override
        public Trade build() {
            return new Trade(company, formationId, formationType, notes,
                    openingDate, TradeState.OPEN, shares, averageBuyingPrice,
                    null, marketCap, suggestedBy, target, stoploss);
        }

        public Trade buildWithPlannedTrade(PlannedTrade plannedTrade) {
            return new Trade(openingDate, TradeState.OPEN, shares,
                    averageBuyingPrice, null, plannedTrade);
        }

        @Override
        protected SimpleTradeBuilder self() {
            return this;
        }
    }

    public float getDifferencePercentage() {
        if (TradeState.CLOSE.equals(state)) {
            return (averageClosingPrice / averageBuyingPrice) - 1;
        }
        return Float.MAX_VALUE;
    }

    // in days
    public long timeInPortfolio() {
        LocalDateTime lastDateInPortfolio = TradeState.CLOSE.equals(state) ? closingDate : LocalDateTime.now();
        return ChronoUnit.DAYS.between(lastDateInPortfolio, openingDate);
    }

    public void close(float averageClosingPrice, LocalDateTime closingDate,
                      List<Target> targets, List<Target> stoplosses) {
        this.averageClosingPrice = averageClosingPrice;
        this.closingDate = closingDate;
        this.state = TradeState.CLOSE;
        setTargets(targets);
        setStoploss(stoplosses);
    }

    public void addShare(int newShareQuantity, float newAverageBuyingPrice) {
        this.shares = newShareQuantity;
        this.averageBuyingPrice = newAverageBuyingPrice;
    }
}
