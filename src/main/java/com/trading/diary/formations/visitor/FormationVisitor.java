package com.trading.diary.formations.visitor;

import com.trading.diary.formations.impls.NoneFormation;
import com.trading.diary.formations.impls.UnexpectedMove;
import com.trading.diary.formations.impls.resistance.extension.FallingResistanceBreakout;
import com.trading.diary.formations.impls.resistance.extension.HorizontalResistanceBreakout;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.formations.impls.support.extension.MorningStar;

/**
 * Visitor contract for rendering or processing formations without putting formatting logic inside the formation entities themselves.
 */
public interface FormationVisitor<R> {

    R visit(BullishEngulfing bullishEngulfing);

    R visit(MorningStar morningStar);

    R visit(Hammer hammer);

    R visit(HorizontalResistanceBreakout horizontalResistanceBreakout);

    R visit(FallingResistanceBreakout fallingResistanceBreakout);

    R visit(UnexpectedMove unexpectedMove);

    R visit(NoneFormation noneFormation);
}
