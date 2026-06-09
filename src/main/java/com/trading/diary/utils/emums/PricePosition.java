package com.trading.diary.utils.emums;

/**
 * Describes where price closed relative to a key support or resistance level. ON is ideal, THROUGH means the level was pierced but reclaimed, ABOVE is early, and BELOW means the level failed.
 */
public enum PricePosition {
    ABOVE, BELOW, THROUGH, ON
}
