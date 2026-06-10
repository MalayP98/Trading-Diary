package com.trading.diary.formations.impls;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.visitor.FormationVisitor;
import com.trading.diary.pojo.Audit;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

// ideally it should be a singleton, but for simplicity, we will just create a new instance every time
@Entity
@Getter
public class NoneFormation extends Audit implements Formation, Serializable {

    private volatile static NoneFormation INSTANCE = null;

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Object lock = new Object();

    public NoneFormation() {

    }


    @Override
    public FormationType getFormation() {
        return FormationType.NONE;
    }

    @Override
    public <R> R accept(FormationVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public static NoneFormation getInstance() {
        if (INSTANCE == null) {
            synchronized (lock) {
                INSTANCE = new NoneFormation();
                INSTANCE.setId(1L);
            }
        }
        return INSTANCE;
    }
}
