package com.trading.diary.formations;

import com.trading.diary.formations.visitor.FormationVisitor;

public interface Formation {

    FormationType getFormation();

    <R> R accept(FormationVisitor<R> visitor);
}
