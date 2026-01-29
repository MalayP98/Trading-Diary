package com.trading.diary.formations.visitor.impls;

import com.trading.diary.formations.impls.UnexpectedMove;
import com.trading.diary.formations.impls.resistance.extension.FallingResistanceBreakout;
import com.trading.diary.formations.impls.resistance.extension.HorizontalResistanceBreakout;
import com.trading.diary.formations.impls.support.extension.BullishEngulfing;
import com.trading.diary.formations.impls.support.extension.Hammer;
import com.trading.diary.formations.impls.support.extension.MorningStar;
import com.trading.diary.formations.score.FormationEvaluationFacade;
import com.trading.diary.formations.visitor.FormationVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortExplanationVisitor implements FormationVisitor<String> {

    private final FormationEvaluationFacade formationEvaluationFacade;

    @Override
    public String visit(BullishEngulfing bullishEngulfing) {
        return "Formation Name: " + bullishEngulfing.getFormation() + "\n" +
                "Timeframe: " + bullishEngulfing.getTimeFrame() + "\n" +
                "Support Length: " + bullishEngulfing.getSupportLength() + "\n" +
                "Volume: " + bullishEngulfing.getVolume() + "\n" +
                "Partially engulfed? " + !bullishEngulfing.fullyEngulfed() + "\n" +
                "Score: " + formationEvaluationFacade.evaluate(bullishEngulfing);
    }

    @Override
    public String visit(MorningStar morningStar) {
        return "Formation Name: " + morningStar.getFormation() + "\n" +
                "Timeframe: " + morningStar.getTimeFrame() + "\n" +
                "Support Length: " + morningStar.getSupportLength() + "\n" +
                "Volume: " + morningStar.getVolume() + "\n" +
                "Dogi color: " + morningStar.getDogiColor() + "\n" +
                "Score: " + formationEvaluationFacade.evaluate(morningStar);
    }

    @Override
    public String visit(Hammer hammer) {
        return "Formation Name: " + hammer.getFormation() + "\n" +
                "Timeframe: " + hammer.getTimeFrame() + "\n" +
                "Support Length: " + hammer.getSupportLength() + "\n" +
                "Hammer Color: " + hammer.getHammerColor() + "\n" +
                "Big lower wick? " + !hammer.isSmallLowerWick() + "\n"+
                "Score: " + formationEvaluationFacade.evaluate(hammer);
    }

    @Override
    public String visit(HorizontalResistanceBreakout horizontalResistanceBreakout) {
        return "Formation Name: " + horizontalResistanceBreakout.getFormation() + "\n" +
                "Timeframe: " + horizontalResistanceBreakout.getTimeFrame() + "\n" +
                "Is confirm B/O? " + horizontalResistanceBreakout.isConfirmBreakout() + "\n" +
                "Breakout Volume: " + horizontalResistanceBreakout.getBreakoutVolume() + "\n" +
                "Resistance Length: " + horizontalResistanceBreakout.getResistanceLength() + "\n"+
                "Score: " + formationEvaluationFacade.evaluate(horizontalResistanceBreakout);
    }

    @Override
    public String visit(FallingResistanceBreakout fallingResistanceBreakout) {
        return "Formation Name: " + fallingResistanceBreakout.getFormation() + "\n" +
                "Timeframe: " + fallingResistanceBreakout.getTimeFrame() + "\n" +
                "Is confirm B/O? " + fallingResistanceBreakout.isConfirmBreakout() + "\n" +
                "Breakout Volume: " + fallingResistanceBreakout.getBreakoutVolume() + "\n" +
                "Resistance Length: " + fallingResistanceBreakout.getResistanceLength() + "\n"+
                "Score: " + formationEvaluationFacade.evaluate(fallingResistanceBreakout);
    }

    @Override
    public String visit(UnexpectedMove unexpectedMove) {
        return "Formation Name: " + unexpectedMove.getFormation() + "\n" +
                "Timeframe: " + unexpectedMove.getTimeFrame() + "\n" +
                "Move Percentage: " + unexpectedMove.getPercentageMove() + "\n" +
                "Days took to move: " + unexpectedMove.getDays() + "\n"+
                "Score: " + formationEvaluationFacade.evaluate(unexpectedMove);
    }
}
