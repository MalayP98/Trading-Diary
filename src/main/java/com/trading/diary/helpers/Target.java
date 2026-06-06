package com.trading.diary.helpers;

import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.emums.TargetStatus;
import com.trading.diary.utils.emums.TargetType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Target extends Audit {

    private float targetPrice;

    @Setter
    @Enumerated(EnumType.STRING)
    private TargetStatus targetStatus;

    @Enumerated(EnumType.STRING)
    private TargetType type;

    public static Target getTarget(final float target){
        return new Target(target, TargetStatus.PENDING, TargetType.TARGET);
    }

    public static Target getStoploss(final float stoploss){
        return new Target(stoploss, TargetStatus.PENDING, TargetType.STOPLOSS);
    }

    public void hit(){
        this.targetStatus = TargetStatus.HIT;
    }

    public void miss(){
        this.targetStatus = TargetStatus.MISS;
    }

    public Target copy(){
        return new Target(this.targetPrice, this.targetStatus, this.type);
    }

    @Override
    public String toString() {
        return "@" + targetPrice + " (" + targetStatus.toString() + ")";
    }
}
