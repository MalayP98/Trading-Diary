package com.trading.diary.pojo.dto;

import com.trading.diary.helpers.Target;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class CloseTradeDTO {

    private final long tradeId;

    private final float closingPrice;

    private final LocalDateTime closingDate;

    private final List<Target> targets;

    private final List<Target> stoplosses;
}
