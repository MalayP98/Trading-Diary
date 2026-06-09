package com.trading.diary.terminalui.formations;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.impls.NoneFormation;
import com.trading.diary.terminalui.Window;
import org.springframework.stereotype.Component;

@Component
public class NoneFormationWindow implements Window<Formation> {

    @Override
    public NoneFormation open() {
        return NoneFormation.getInstance();
    }
}
