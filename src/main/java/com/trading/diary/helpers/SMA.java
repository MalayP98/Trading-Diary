package com.trading.diary.helpers;

import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.PricePosition;
import com.trading.diary.utils.TrendlineDirections;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SMA extends Audit {

    private TrendlineDirections direction;

    private PricePosition pricePosition;

    public SMA perfectSMA(){
        return new SMA(TrendlineDirections.RISING, PricePosition.ABOVE);
    }
}
