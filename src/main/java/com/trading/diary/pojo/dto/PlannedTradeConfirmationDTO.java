package com.trading.diary.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PlannedTradeConfirmationDTO {

    private long plannedTradeId;

    private float buyingPrice;

    private int quantity;

    private LocalDateTime openingDate;
}
