package com.trading.diary.helpers;

import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.TrendlineDirections;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class SMA extends Audit {

    private static enum PricePosition{
        ABOVE, BELOW, THROUGH
    }

    private TrendlineDirections direction;

    private PricePosition pricePosition;

    public SMA perfectSMA(){
        return new SMA(TrendlineDirections.RISING, PricePosition.ABOVE);
    }
}
