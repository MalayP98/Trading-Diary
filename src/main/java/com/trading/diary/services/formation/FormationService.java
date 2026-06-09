package com.trading.diary.services.formation;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Generic persistence contract for formation types. It hides repository details from the rest of the application and exposes a common save and lookup workflow.
 */
public interface FormationService<T extends Formation>{

    FormationType getType();

    /**
     * Persists the supplied formation through the backing repository for its concrete formation type.
     */
    default <S extends T> S save(S formation){
        return getRepository().save(formation);
    }

    /**
     * Retrieves a stored formation by id and returns null when no matching record exists.
     */
    default <S extends T> S getFormation(long id){
        return (S) getRepository().findById(id).orElse(null);
    }

    JpaRepository<T, Long> getRepository();
}
