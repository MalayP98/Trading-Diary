package com.trading.diary.pojo.dao;

import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.TradeState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PlannedTradeConversionDTO {

    private Trade trade;

    private PlannedTrade plannedTrade;
}
