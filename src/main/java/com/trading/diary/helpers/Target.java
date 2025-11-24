package com.trading.diary.helpers;

import com.trading.diary.pojo.Audit;
import com.trading.diary.utils.TargetStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Target extends Audit {

    private float targetPrice;

    @Setter
    @Enumerated(EnumType.STRING)
    private TargetStatus targetStatus;

    public static Target getTarget(float target){
        return new Target(target, TargetStatus.PENDING);
    }

    @Override
    public String toString() {
        return "@" + targetPrice + " (" + targetStatus.toString() + ")";
    }
}
