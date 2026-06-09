package com.trading.diary.terminalui.formations;

import com.googlecode.lanterna.gui2.*;
import com.trading.diary.formations.Formation;
import com.trading.diary.formations.impls.UnexpectedMove;
import com.trading.diary.terminalui.UiNavigator;
import com.trading.diary.terminalui.Window;
import com.trading.diary.utils.emums.TimeFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Lanterna form window that captures the inputs for a unexpected move setup before the caller persists the resulting formation.
 */
@Component
@RequiredArgsConstructor
public class UnexpectedMoveFormationWindow implements Window<Formation> {

    private final UiNavigator navigator;

    /**
     * Collects the minimal inputs needed to record an unexpected price move.
     */
    @Override
    public Formation open() {
        BasicWindow window = new BasicWindow("Unexpected Move - Formation Details");
        Panel panel = new Panel(new GridLayout(2));

        TextBox percentageMoveBox = new TextBox();
        TextBox daysBox = new TextBox();
        ComboBox<TimeFrame> timeFrameCombo = new ComboBox<>(TimeFrame.values());

        panel.addComponent(new Label("Percentage Move (min 1)"));
        panel.addComponent(percentageMoveBox);
        panel.addComponent(new Label("Days (min 1)"));
        panel.addComponent(daysBox);
        panel.addComponent(new Label("Time Frame"));
        panel.addComponent(timeFrameCombo);

        AtomicReference<Formation> result = new AtomicReference<>();

        panel.addComponent(
                new Button("Confirm", () -> {
                    result.set(UnexpectedMove.builder()
                            .percentageMove(Float.parseFloat(percentageMoveBox.getText().trim()))
                            .days(Long.parseLong(daysBox.getText().trim()))
                            .timeFrame(timeFrameCombo.getSelectedItem())
                            .build());
                    window.close();
                }),
                GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER, true, false, 2, 1)
        );

        window.setComponent(panel);
        navigator.show(window);
        return result.get();
    }
}
