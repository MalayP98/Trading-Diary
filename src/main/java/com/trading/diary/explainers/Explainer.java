package com.trading.diary.explainers;

import org.springframework.stereotype.Service;

@Service
public interface Explainer<R> {

    String explain(R content);
}
