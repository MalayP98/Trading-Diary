package com.trading.diary.explainers;

import org.springframework.stereotype.Service;

/**
 * Strategy interface for converting domain objects into user-facing terminal text. Implementations provide both a full explanation and a compact list-friendly summary.
 */
@Service
public interface Explainer<R> {

    String explain(R content);

    /**
     * Returns the first non-blank line of the detailed explanation so list views can show a compact summary without each implementation duplicating the same fallback logic.
     */
    default String summarize(R item) {
        return explain(item).lines().filter(l -> !l.isBlank()).findFirst().orElse("(item)");
    }
}
