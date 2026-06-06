package com.trading.diary.terminalui;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.trading.diary.formations.FormationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormationTypeWindow implements Window<FormationType> {

    private final UiNavigator uiNavigator;

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
