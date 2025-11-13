package com.trading.diary.formations.impls;

import com.trading.diary.formations.Formation;
import com.trading.diary.pojo.Audit;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class UnexpectedMove extends Audit implements Formation {

    private final String formationName = "Unexpected Move";

    private final String percentageMove;

    private final String days;

    private final boolean nifty200Stock;

    private UnexpectedMove(String percentageMove, String days, boolean nifty200Stock) {
        this.percentageMove = percentageMove;
        this.days = days;
        this.nifty200Stock = nifty200Stock;
    }

    @Override
    public String getFormationName() {
        return formationName;
    }

    public UnexpectedMoveBuilder builder() {
        return new UnexpectedMoveBuilder();
    }

    private class UnexpectedMoveBuilder {

        private String percentageMove;

        private String days;

        private boolean nifty200Stock;

        public UnexpectedMoveBuilder percentageMove(String percentageMove) {
            this.percentageMove = percentageMove;
            return this;
        }

        public UnexpectedMoveBuilder days(String days) {
            this.days = days;
            return this;
        }

        public UnexpectedMoveBuilder nifty200Stock(boolean nifty200Stock) {
            this.nifty200Stock = nifty200Stock;
            return this;
        }

        public UnexpectedMove build() {
            return new UnexpectedMove(percentageMove, days, nifty200Stock);
        }
    }
}
