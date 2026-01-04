package com.trading.diary.pojo.dto;

import com.trading.diary.trade.impls.PlannedTrade;
import com.trading.diary.trade.impls.Trade;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlannedTradeConfirmationDTO {

    private Trade trade;

    private PlannedTrade plannedTrade;
}
