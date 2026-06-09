package com.trading.diary.repositories.formations;

import com.trading.diary.formations.impls.NoneFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoneFormationRepository extends JpaRepository<NoneFormation, Long> {
}
