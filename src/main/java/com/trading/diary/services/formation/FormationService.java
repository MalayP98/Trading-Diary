package com.trading.diary.services.formation;

import com.trading.diary.formations.Formation;
import com.trading.diary.formations.FormationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Locale;

public interface FormationService<T extends Formation>{

    FormationType getType();

    default <S extends T> S save(S formation){
        return getRepository().save(formation);
    }

    JpaRepository<T, Long> getRepository();
}
