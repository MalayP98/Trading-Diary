package com.trading.diary.formations.score;

public interface Evaluator<T> {

    double evaluate(T entity);
}
