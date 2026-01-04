package com.trading.diary.explainers.impls;

import com.trading.diary.explainers.Explainer;
import com.trading.diary.formations.Formation;
import org.springframework.stereotype.Service;

@Service
public class FormationExplainer implements Explainer<Formation> {

    @Override
    public String explain(Formation content) {
        return content.getFormation().name();
    }
}
