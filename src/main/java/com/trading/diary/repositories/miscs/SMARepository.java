package com.trading.diary.repositories.miscs;

import com.trading.diary.helpers.SMA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMARepository extends JpaRepository<SMA, Long> {
}
