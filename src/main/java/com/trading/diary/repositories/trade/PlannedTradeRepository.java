package com.trading.diary.repositories.trade;

import com.trading.diary.trade.impls.PlannedTrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannedTradeRepository extends JpaRepository<PlannedTrade, Long> {

    PlannedTrade getByIsDeletedFalseAndId(Long id);

    @Modifying
    @Query(value = "update #{#entityName} e set e.deleted = true where e.id = ?1", nativeQuery = true)
    PlannedTrade deleteById(long id);

}
