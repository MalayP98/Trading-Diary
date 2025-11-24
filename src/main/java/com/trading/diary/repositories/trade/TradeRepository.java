package com.trading.diary.repositories.trade;

import com.trading.diary.trade.impls.Trade;
import com.trading.diary.utils.TradeState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Modifying
    @Query(value = "update #{#entityName} e set e.deleted = true where e.id = ?1", nativeQuery = true)
    void deleteById(long id);

    Page<Trade> findAllByDeletedFalseAndState(TradeState state, Pageable pageable);

    long countByDeletedFalse();
}