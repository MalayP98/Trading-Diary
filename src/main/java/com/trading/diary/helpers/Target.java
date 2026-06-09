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

/**
 * Represents either a profit target or a stoploss attached to a trade. Targets are copied when trades are cloned so state changes on one trade do not leak into another.
 */
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

    /**
     * Creates a pending profit target at the supplied price.
     */
    public static Target getTarget(final float target){
        return new Target(target, TargetStatus.PENDING, TargetType.TARGET);
    }

    /**
     * Creates a pending stoploss level at the supplied price.
     */
    public static Target getStoploss(final float stoploss){
        return new Target(stoploss, TargetStatus.PENDING, TargetType.STOPLOSS);
    }

    public void hit(){
        this.targetStatus = TargetStatus.HIT;
    }

    public void miss(){
        this.targetStatus = TargetStatus.MISS;
    }

    /**
     * Copies the target so cloned trades can track outcomes independently.
     */
    public Target copy(){
        return new Target(this.targetPrice, this.targetStatus, this.type);
    }

    @Override
    public String toString() {
        return "@" + targetPrice + " (" + targetStatus.toString() + ")";
    }
}
