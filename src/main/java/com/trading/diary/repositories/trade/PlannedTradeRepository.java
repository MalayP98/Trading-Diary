package com.trading.diary.repositories.trade;

import com.trading.diary.pojo.Company;
import com.trading.diary.trade.impls.PlannedTrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for persisting and querying planned trade records.
 */
@Repository
public interface PlannedTradeRepository extends JpaRepository<PlannedTrade, Long> {

    PlannedTrade getByDeletedFalseAndId(Long id);

    long countByDeletedFalse();

    /**
     * Soft-deletes a planned trade instead of physically removing it so trade history can remain auditable.
     */
    @Modifying
    @Query(value = "update Planned_Trade set deleted = true where id = :id", nativeQuery = true)
    void deleteById(@Param("id") long id);

    List<PlannedTrade> findAllByCompanyAndDeletedFalse(Company company, Pageable pageable);

    Page<PlannedTrade> findAllByDeletedFalse(Pageable pageable);

    long countByCompanyAndDeletedFalse(Company company);
}
