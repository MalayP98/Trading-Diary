package com.trading.diary.terminalui.formations;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import com.trading.diary.terminalui.Window;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormationWindowFactory {

    private final BullishEngulfingFormationWindow bullishEngulfingWindow;
    private final HammerFormationWindow hammerWindow;
    private final MorningStarFormationWindow morningStarWindow;
    private final HorizontalResistanceBreakoutFormationWindow horizontalBreakoutWindow;
    private final FallingResistanceBreakoutFormationWindow fallingBreakoutWindow;
    private final UnexpectedMoveFormationWindow unexpectedMoveWindow;

    public Window<Formation> getWindow(FormationType type) {
        switch (type) {
            case BULLISH_ENGULFING: return bullishEngulfingWindow;
            case HAMMER: return hammerWindow;
            case MORNING_STAR: return morningStarWindow;
            case HORIZONTAL_RESISTANCE_BREAKOUT: return horizontalBreakoutWindow;
            case FALLING_RESISTANCE_BREAKOUT: return fallingBreakoutWindow;
            case UNEXPECTED_MOVE: return unexpectedMoveWindow;
            default: throw new IllegalArgumentException("Unknown formation type: " + type);
        }
    }
}
