package com.trading.diary.formations.impls;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.visitor.FormationVisitor;
import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.TimeFrame;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UnexpectedMove extends Audit implements Formation {

    @Min(value = 1, message = "Percentage move cannot be less than 1")
    private float percentageMove;

    @Min(value = 1, message = "Days cannot be less than 1")
    private long days;

    private TimeFrame timeFrame;

    @Override
    public FormationType getFormation() {
        return FormationType.UNEXPECTED_MOVE;
    }

    @Override
    public <R> R accept(FormationVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public static UnexpectedMoveBuilder builder() {
        return new UnexpectedMoveBuilder();
    }

    public static class UnexpectedMoveBuilder {

        private float percentageMove;

        private long days;

        private TimeFrame timeFrame;

        public UnexpectedMoveBuilder percentageMove(float percentageMove){
            this.percentageMove = percentageMove;
            return this;
        }

        public UnexpectedMoveBuilder days(long days){
            this.days = days;
            return this;
        }

        public UnexpectedMoveBuilder timeFrame(TimeFrame timeFrame) {
            this.timeFrame = timeFrame;
            return this;
        }

        public UnexpectedMove build() {
            return new UnexpectedMove(percentageMove, days, timeFrame);
        }
    }
}
