package com.trading.diary.trade.impls;

import com.trading.diary.trade.AbstractTrade;
import com.trading.diary.utils.TimeFrame;
import com.trading.diary.utils.TradeState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class Trade extends AbstractTrade {

    @Enumerated(EnumType.STRING)
    private TradeState state = TradeState.BOUGHT;

    private int shares;

    private float averageBuyingPrice;

    private float averageSellingPrice;

    // earliest buying day
    private final LocalDateTime buyingDate;

    private LocalDateTime sellingDate;

    private Trade(TimeFrame timeFrame, long formationId,
                 float stoploss, String notes, LocalDateTime buyingDate,
                 TradeState state, int shares, float averageBuyingPrice,
                 float averageSellingPrice, LocalDateTime sellingDate) {
        super(timeFrame, formationId, stoploss, notes);
        this.buyingDate = buyingDate;
        this.state = state;
        this.shares = shares;
        this.averageBuyingPrice = averageBuyingPrice;
        this.averageSellingPrice = averageSellingPrice;
        this.sellingDate = sellingDate;
    }

    public TradeBuilder builder(){
        return new TradeBuilder();
    }

    public static class TradeBuilder extends AbstractTradeBuilder<TradeBuilder> {

        private int shares;

        private float averageBuyingPrice;

        private float averageSellingPrice;

        // earliest buying day
        private LocalDateTime buyingDate;

        private LocalDateTime sellingDate;

        public TradeBuilder shares(int shares) {
            this.shares = shares;
            return this;
        }

        public TradeBuilder averageBuyingPrice(float averageBuyingPrice) {
            this.averageBuyingPrice = averageBuyingPrice;
            return this;
        }

        public TradeBuilder averageSellingPrice(float averageSellingPrice) {
            this.averageSellingPrice = averageSellingPrice;
            return this;
        }

        public TradeBuilder buyingDate(LocalDateTime buyingDate) {
            this.buyingDate = buyingDate;
            return this;
        }

        public TradeBuilder sellingDate(LocalDateTime sellingDate) {
            this.sellingDate = sellingDate;
            return this;
        }

        @Override
        protected TradeBuilder self() {
            return this;
        }

        public Trade build() {
            return new Trade(timeFrame, formationId, stoploss, notes,
                    buyingDate, TradeState.BOUGHT, shares,
                    averageBuyingPrice, averageSellingPrice, sellingDate);
        }
    }

    public float getDifferencePercentage(){
        if(TradeState.SOLD.equals(state)){
            return (averageSellingPrice/averageBuyingPrice)-1;
        }
        return Float.MAX_VALUE;
    }

    // in days
    public long timeInPortfolio(){
        LocalDateTime lastDateInPortfolio = TradeState.SOLD.equals(state) ? sellingDate : LocalDateTime.now();
        return ChronoUnit.DAYS.between(lastDateInPortfolio, buyingDate);
    }

    public void sold(float averageSellingPrice){
        this.averageSellingPrice = averageSellingPrice;
        this.sellingDate = LocalDateTime.now();
        this.state = TradeState.SOLD;
    }

    public void addShare(int newShareQuantity, float newAverageBuyingPrice){
        this.shares = newShareQuantity;
        this.averageBuyingPrice = newAverageBuyingPrice;
    }
}
