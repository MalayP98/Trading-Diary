package com.trading.diary.helpers;

import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.emums.PricePosition;
import com.trading.diary.utils.emums.TrendlineDirections;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

/**
 * Represents one moving-average snapshot used in breakout scoring. Each record stores both slope direction and where price sits relative to the average.
 */
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

    /**
     * Creates a strong-trend SMA snapshot where price sits above a rising average, which represents the ideal breakout context.
     */
    public SMA perfectSMA(){
        return new SMA(TrendlineDirections.RISING, PricePosition.ABOVE);
    }
}
