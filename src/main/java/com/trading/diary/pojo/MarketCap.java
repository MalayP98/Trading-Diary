package com.trading.diary.pojo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
