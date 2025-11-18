package com.trading.diary.repositories.trade;

import com.trading.diary.trade.impls.LongTrade;
import com.trading.diary.utils.TradeState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<LongTrade, Long> {

    @Modifying
    @Query(value = "update #{#entityName} e set e.deleted = true where e.id = ?1", nativeQuery = true)
    LongTrade deleteById(long id);

    List<LongTrade> findAllByIsDeletedFalseAndState(TradeState state);

}