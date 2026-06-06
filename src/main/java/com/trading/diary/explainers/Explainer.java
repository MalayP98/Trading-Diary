package com.trading.diary.explainers;

import org.springframework.stereotype.Service;

@Service
public interface Explainer<R> {

    String explain(R content);

    default String summarize(R item) {
        return explain(item).lines().filter(l -> !l.isBlank()).findFirst().orElse("(item)");
    }
}
