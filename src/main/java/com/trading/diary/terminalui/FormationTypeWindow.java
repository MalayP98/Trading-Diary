package com.trading.diary.terminalui;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.trading.diary.formations.FormationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Small picker window that lets the user choose which chart formation they want to describe next.
 */
@Service
@RequiredArgsConstructor
public class FormationTypeWindow implements Window<FormationType> {

    private final UiNavigator uiNavigator;

    /**
     * Displays a small chooser for the formation type that should be configured next.
     */
    @Override
    public FormationType open() {
        BasicWindow window =
                new BasicWindow("Formation Type");
        Panel panel =
                new Panel(new GridLayout(2));
        ComboBox<FormationType> formationTypeComboBox = new ComboBox<>(
                FormationType.values()
        );
        panel.addComponent(formationTypeComboBox);
        window.setComponent(panel);
        uiNavigator.show(window);
        FormationType formationType = formationTypeComboBox.getSelectedItem();
        window.close();
        return formationType;
    }
}
