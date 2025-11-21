package com.trading.diary.menu.formation_menu;

import com.trading.diary.formations.FormationType;
import com.trading.diary.formations.impls.UnexpectedMove;

public class UnexpectedMoveMenuAbstract extends AbstractFormationMenu<UnexpectedMove> {

    @Override
    public UnexpectedMove showMenu() {
        print("=== Unexpected Move ===");
        UnexpectedMove.UnexpectedMoveBuilder builder = UnexpectedMove.builder();

        print("Numbers of days the move took?");
        builder.days(InputType.LONG.nextInput());

        print("Percentage move in price?");
        builder.percentageMove(InputType.FLOAT.nextInput());

        return builder.build();
    }

    @Override
    public FormationType getFormation() {
        return FormationType.UNEXPECTED_MOVE;
    }
}
