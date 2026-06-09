package com.trading.diary.formations;

import com.trading.diary.formations.visitor.FormationVisitor;

/**
 * Common contract for every chart formation stored by the journal. Each formation exposes its type and participates in the visitor-based explanation flow.
 */
public interface Formation {

    /**
     * Identifies the concrete formation so factories and evaluators can route the instance correctly.
     */
    FormationType getFormation();

    /**
     * Applies a visitor to the formation, allowing explanation and formatting logic to stay outside the entity hierarchy.
     */
    <R> R accept(FormationVisitor<R> visitor);
}
