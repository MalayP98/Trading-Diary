package com.trading.diary.pojo.dao;

import com.trading.diary.utils.TradeState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TradeDTO {

    private TradeState state;

    private int shares;

    private float averageBuyingPrice;

    private float averageSellingPrice;

    // earliest buying day
    private LocalDateTime buyingDate;

    private LocalDateTime sellingDate;
}
