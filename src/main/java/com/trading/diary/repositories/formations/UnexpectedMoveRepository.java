package com.trading.diary.repositories.formations;

import com.trading.diary.formations.impls.UnexpectedMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnexpectedMoveRepository extends JpaRepository<UnexpectedMove, Long> {
}
