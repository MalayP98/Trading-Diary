package com.trading.diary.services.formation;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps formation types to the service capable of persisting and retrieving that concrete formation.
 */
@Service
public class FormationServiceFactory<T extends Formation> {

    private final Map<FormationType, FormationService<T>> formationServiceMap;

    public FormationServiceFactory(List<FormationService<T>> formationServices) {
        formationServiceMap = new HashMap<>();
        for(FormationService<T> formationService : formationServices){
            formationServiceMap.put(formationService.getType(), formationService);
        }
    }

    /**
     * Returns the formation service responsible for the requested formation type.
     */
    public FormationService<T> getFormationService(FormationType type){
        return formationServiceMap.get(type);
    }
}
