package com.trading.diary.trade.impls;

import com.trading.diary.helpers.Target;
import com.trading.diary.pojo.Company;
import com.trading.diary.pojo.MarketCap;
import com.trading.diary.pojo.Person;
import com.trading.diary.trade.AbstractTrade;
import com.trading.diary.utils.TimeFrame;
import com.trading.diary.utils.TradeState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Getter
public class LongTrade extends AbstractTrade {

    @Enumerated(EnumType.STRING)
    private TradeState state = TradeState.OPEN;

    private int shares;

    private float averageBuyingPrice;

    private Float averageClosingPrice;

    // earliest buying day
    private final LocalDateTime openingDate;

    private LocalDateTime closingDate;

    private LongTrade(Company company, TimeFrame timeFrame, long formationId,
                      String notes, LocalDateTime openingDate,
                      TradeState state, int shares, float averageBuyingPrice,
                      LocalDateTime sellingDate, MarketCap marketCap, Person suggestedBy, List<Target> target, List<Target> stoploss ) {
        super(company, timeFrame, formationId, stoploss, target, marketCap, suggestedBy, notes);
        this.openingDate = openingDate;
        this.state = state;
        this.shares = shares;
        this.averageBuyingPrice = averageBuyingPrice;
        this.closingDate = sellingDate;
    }

    private LongTrade(Company company, LocalDateTime openingDate,
                      TradeState state, int shares, float averageBuyingPrice,
                      LocalDateTime sellingDate, PlannedTrade plannedTrade) {
        super(company,plannedTrade.getTimeFrame(), plannedTrade.getFormationId(), plannedTrade.getStoploss(),
                plannedTrade.getTargets(), plannedTrade.getMarketCap(), plannedTrade.getSuggestedBy(),
                plannedTrade.getNotes());
        this.openingDate = openingDate;
        this.state = state;
        this.shares = shares;
        this.averageBuyingPrice = averageBuyingPrice;
        this.closingDate = sellingDate;
    }

    public static SimpleTradeBuilder builder(){
        return new SimpleTradeBuilder();
    }

    public static TradeWithPlannedTradeBuilder tradeWithPlannedTradeBuilder(){
        return new TradeWithPlannedTradeBuilder();
    }

    public static class TradeWithPlannedTradeBuilder {

        protected int shares;

        protected float averageBuyingPrice;

        // earliest buying day
        protected LocalDateTime buyingDate;

        private PlannedTrade plannedTrade;

        public TradeWithPlannedTradeBuilder shares(int shares) {
            this.shares = shares;
            return this;
        }

        public TradeWithPlannedTradeBuilder averageBuyingPrice(float averageBuyingPrice) {
            this.averageBuyingPrice = averageBuyingPrice;
            return this;
        }

        public TradeWithPlannedTradeBuilder buyingDate(LocalDateTime buyingDate) {
            this.buyingDate = buyingDate;
            return this;
        }

        public TradeWithPlannedTradeBuilder plannedTrade(PlannedTrade plannedTrade){
            this.plannedTrade = plannedTrade;
            return this;
        }

        public LongTrade build() {
            return new LongTrade(plannedTrade.getCompany(), buyingDate, TradeState.OPEN, shares,
                    averageBuyingPrice, null, plannedTrade);
        }
    }

    public static class SimpleTradeBuilder extends AbstractTradeBuilder<SimpleTradeBuilder, LongTrade> {

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
        public LongTrade build() {
            return new LongTrade(company, timeFrame, formationId, notes,
                    openingDate, TradeState.OPEN, shares, averageBuyingPrice,
                    null, marketCap, suggestedBy, target, stoploss);
        }

        @Override
        protected SimpleTradeBuilder self() {
            return this;
        }
    }

    public float getDifferencePercentage(){
        if(TradeState.CLOSE.equals(state)){
            return (averageClosingPrice /averageBuyingPrice)-1;
        }
        return Float.MAX_VALUE;
    }

    // in days
    public long timeInPortfolio(){
        LocalDateTime lastDateInPortfolio = TradeState.CLOSE.equals(state) ? closingDate : LocalDateTime.now();
        return ChronoUnit.DAYS.between(lastDateInPortfolio, openingDate);
    }

    public void close(float averageClosingPrice, LocalDateTime closingDate){
        this.averageClosingPrice = averageClosingPrice;
        this.closingDate = closingDate;
        this.state = TradeState.CLOSE;
    }

    public void addShare(int newShareQuantity, float newAverageBuyingPrice){
        this.shares = newShareQuantity;
        this.averageBuyingPrice = newAverageBuyingPrice;
    }
}
