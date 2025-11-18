package com.trading.diary.pojo.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CloseTradeDAO {

    private final long tradeId;

    private final float closingPrice;

    private final LocalDateTime closingDate;

}
