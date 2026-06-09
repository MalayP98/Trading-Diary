package com.trading.diary.pojo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Carries the minimum information required to close an open trade from the UI layer.
 */
@RequiredArgsConstructor
@Getter
public class CloseTradeDTO {

    private final long tradeId;

    private final float closingPrice;

    private final LocalDateTime closingDate;
}
