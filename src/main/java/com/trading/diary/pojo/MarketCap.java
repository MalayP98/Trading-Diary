package com.trading.diary.pojo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Embeddable market-cap classification used to distinguish whether a trade belongs to the Nifty 50, Nifty 200, or the broader market.
 */
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class MarketCap {

    private boolean nifty50;

    private boolean nifty200;

    @Override
    public String toString() {
        if (nifty50) {
            return "Nifty 50";
        } else if (nifty200) {
            return "Nifty 200";
        } else {
            return "Others";
        }
    }
}
