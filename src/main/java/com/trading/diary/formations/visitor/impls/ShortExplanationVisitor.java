package com.trading.diary.formations.visitor.impls;

import com.trading.diary.formations.impls.UnexpectedMove;
import com.trading.diary.formations.impls.resistance.extension.FallingResistanceBreakout;
import com.trading.diary.formations.impls.resistance.extension.HorizontalResistanceBreakout;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.formations.visitor.FormationVisitor;

// TODO: Fill explanations
public class ShortExplanationVisitor implements FormationVisitor<String> {

    @Override
    public String visit(BullishEngulfing bullishEngulfing) {
        return "";
    }

    @Override
    public String visit(MorningStar morningStar) {
        return "";
    }

    @Override
    public String visit(Hammer hammer) {
        return "";
    }

    @Override
    public String visit(HorizontalResistanceBreakout horizontalResistanceBreakout) {
        return "";
    }

    @Override
    public String visit(FallingResistanceBreakout fallingResistanceBreakout) {
        return "";
    }

    @Override
    public String visit(UnexpectedMove unexpectedMove) {
        return "";
    }
}
