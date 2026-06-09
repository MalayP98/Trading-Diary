package com.trading.diary.formations.score;

/**
 * Minimal scoring contract used throughout the formation scoring subsystem.
 */
public interface Evaluator<T> {

    double evaluate(T entity);
}
