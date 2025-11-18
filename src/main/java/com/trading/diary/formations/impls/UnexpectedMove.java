package com.trading.diary.formations.impls;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import com.trading.diary.pojo.Audit;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UnexpectedMove extends Audit implements Formation {

    private float percentageMove;

    private long days;

    @Override
    public FormationType getFormation() {
        return FormationType.UNEXPECTED_MOVE;
    }

    public static UnexpectedMoveBuilder builder() {
        return new UnexpectedMoveBuilder();
    }

    public static class UnexpectedMoveBuilder {

        private float percentageMove;

        private long days;

        public UnexpectedMoveBuilder percentageMove(float percentageMove){
            this.percentageMove = percentageMove;
            return this;
        }

        public UnexpectedMoveBuilder days(long days){
            this.days = days;
            return this;
        }

        public UnexpectedMove build() {
            return new UnexpectedMove(percentageMove, days);
        }
    }
}
