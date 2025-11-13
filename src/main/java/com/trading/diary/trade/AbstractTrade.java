package com.trading.diary.trade;

import com.trading.diary.helpers.Target;
import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.TimeFrame;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@AllArgsConstructor
public abstract class AbstractTrade extends Audit {

    private final TimeFrame timeFrame;

    @NonNull
    private final long formationId;

    private float stoploss;

    @OneToMany
    private final List<Target> targets = new ArrayList<>();

    private String notes;

    public void addTarget(Target target){
        this.targets.add(target);
    }

    protected abstract static class AbstractTradeBuilder<T extends AbstractTradeBuilder<T>> {

        protected TimeFrame timeFrame;

        protected long formationId;

        protected float stoploss;

        protected String notes;

        public T timeFrame(TimeFrame timeFrame) {
            this.timeFrame = timeFrame;
            return self();
        }

        public T formationId(long formationId) {
            this.formationId = formationId;
            return self();
        }

        public T stoploss(float stoploss) {
            this.stoploss = stoploss;
            return self();
        }

        public T notes(String notes) {
            this.notes = notes;
            return self();
        }

        protected abstract T self();
    }
}
