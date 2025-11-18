package com.trading.diary.helpers;

import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.TargetStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Target extends Audit {

    private float targetPrice;

    private TargetStatus targetStatus;

    public static Target getTarget(float target){
        return new Target(target, TargetStatus.PENDING);
    }

    @Override
    public String toString() {
        return "@" + targetPrice + " (" + targetStatus.toString() + ")";
    }
}
