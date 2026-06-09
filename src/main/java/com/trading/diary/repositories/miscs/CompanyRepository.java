package com.trading.diary.repositories.miscs;

import com.trading.diary.pojo.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for persisting and querying company records.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
}
