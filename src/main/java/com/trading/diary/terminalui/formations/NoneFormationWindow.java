package com.trading.diary.terminalui.formations;

import com.trading.diary.formations.Formation;
import com.trading.diary.terminalui.Window;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoneFormationWindow implements Window<Formation> {

    @Override
    public Formation open() {
        return null;
    }
}
