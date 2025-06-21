package com.gymloggingapp.gymloggingapp.Repositories;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;
import org.springframework.data.repository.CrudRepository;

public interface MovementRepository extends CrudRepository<MovementEntity, Long> {
}
