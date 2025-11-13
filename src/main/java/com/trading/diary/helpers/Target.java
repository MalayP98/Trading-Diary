package com.trading.diary.helpers;

import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.TargetStatus;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Entity
public class Target extends Audit {

    private final float targetPrice;

    private final TargetStatus targetStatus;

    public static Target getTarget(float target){
        return new Target(target, TargetStatus.PENDING);
    }
}
