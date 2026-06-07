package com.trading.diary.formations.impls;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.visitor.FormationVisitor;

public class NoneFormation implements Formation {
    @Override
    public FormationType getFormation() {
        return FormationType.NONE;
    }

    @Override
    public <R> R accept(FormationVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
