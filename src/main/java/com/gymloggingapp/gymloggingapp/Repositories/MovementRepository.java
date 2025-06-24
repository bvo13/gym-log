package com.gymloggingapp.gymloggingapp.Repositories;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<MovementEntity, Long> {
}
