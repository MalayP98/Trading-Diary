package com.trading.diary.terminalui;

/**
 * Simple abstraction for terminal windows that return a value when the user finishes interacting with them.
 */
public interface Window<T> {

    /**
     * Displays the window and returns the value collected from the interaction, if any.
     */
    T open();
}
