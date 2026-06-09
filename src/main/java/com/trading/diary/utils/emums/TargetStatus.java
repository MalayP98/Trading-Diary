package com.trading.diary.utils.emums;

/**
 * Tracks whether a target or stoploss was still pending, was hit, or was missed once the trade outcome became known.
 */
public enum TargetStatus {

    HIT, MISS, PENDING
}
