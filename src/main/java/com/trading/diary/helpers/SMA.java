package com.trading.diary.helpers;

import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.emums.PricePosition;
import com.trading.diary.utils.emums.TrendlineDirections;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SMA extends Audit {

    @Enumerated(EnumType.STRING)
    private TrendlineDirections direction;

    @Enumerated(EnumType.STRING)
    private PricePosition pricePosition;

    public SMA perfectSMA(){
        return new SMA(TrendlineDirections.RISING, PricePosition.ABOVE);
    }
}
