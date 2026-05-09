package com.trading.diary.trade;

import com.trading.diary.formations.FormationType;
import com.trading.diary.helpers.Target;
import com.trading.diary.pojo.Audit;
import com.trading.diary.pojo.Company;
import com.trading.diary.pojo.MarketCap;
import com.trading.diary.pojo.Person;
import com.trading.diary.utils.emums.TimeFrame;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractTrade extends Audit {

    @ManyToOne
    @JoinColumn(name = "COMPANY")
    private Company company;

    private long formationId;

    private FormationType formationType;

    @Setter
    @OneToMany(targetEntity = Target.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Collection<Target> stoploss;

    @Setter
    @OneToMany(targetEntity = Target.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Collection<Target> targets;

    @Embedded
    private MarketCap marketCap;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "SUGGESTED_BY")
    private Person suggestedBy;

    @Setter
    private String notes;

    public AbstractTrade(@NonNull Company company, long formationId, @NonNull FormationType formationType, Collection<Target> stoploss, Collection<Target> targets,
                         @NonNull MarketCap marketCap, Person suggestedBy, String notes) {
        if(suggestedBy == null){
            suggestedBy = Person.self();
        }
        if(CollectionUtils.isEmpty(targets)){
            targets = new ArrayBlockingQueue<>(3); // can only accept 3 targets
        }
        if(CollectionUtils.isEmpty(stoploss)){
            stoploss = new ArrayBlockingQueue<>(3); // can only accept 3 stoploss
        }
        this.formationId = formationId;
        this.formationType = formationType;
        this.stoploss = clone(stoploss);
        this.targets = clone(targets);
        this.marketCap = marketCap;
        this.company = company;
        this.suggestedBy = suggestedBy;
        this.notes = notes;
    }

    public abstract static class AbstractTradeBuilder<T extends AbstractTradeBuilder<T, R>, R extends AbstractTrade> {

        protected Company company;

        protected TimeFrame timeFrame;

        protected long formationId;

        protected String notes;

        protected MarketCap marketCap;

        protected Person suggestedBy;

        protected List<Target> stoploss;

        protected List<Target> target;

        protected FormationType formationType;

        public T company(Company company) {
            this.company = company;
            return self();
        }

        public T suggestedBy(Person suggestedBy) {
            this.suggestedBy = suggestedBy;
            return self();
        }

        public T timeFrame(TimeFrame timeFrame) {
            this.timeFrame = timeFrame;
            return self();
        }

        public T formationId(long formationId) {
            this.formationId = formationId;
            return self();
        }

        public T notes(String notes) {
            this.notes = notes;
            return self();
        }

        public T marketCap(MarketCap marketCap) {
            this.marketCap = marketCap;
            return self();
        }

        public T addStoploss(List<Target> stoploss){
            this.stoploss = stoploss;
            return self();
        }

        public T addTarget(List<Target> target){
            this.target = target;
            return self();
        }

        public T formationType(FormationType formationType){
            this.formationType = formationType;
            return self();
        }

        public abstract R build();

        protected abstract T self();
    }

    private Collection<Target> clone(Collection<Target> targets){
        return targets.stream().map(t -> t.getId() == 0 ? t : t.copy()).toList();
    }

    public void addNotes(String notes){
        this.notes += (StringUtils.isNotEmpty(this.notes) ? "\n" : "") + notes;
    }
}
