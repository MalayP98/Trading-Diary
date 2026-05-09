package com.trading.diary.pojo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CloseTradeDTO {

    private final long tradeId;

    private final float closingPrice;

    private final LocalDateTime closingDate;
}
