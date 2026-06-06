package com.trading.diary.services.formation;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationService<T extends Formation>{

    FormationType getType();

    default <S extends T> S save(S formation){
        return getRepository().save(formation);
    }

    default <S extends T> S getFormation(long id){
        return (S) getRepository().findById(id).orElse(null);
    }

    JpaRepository<T, Long> getRepository();
}
