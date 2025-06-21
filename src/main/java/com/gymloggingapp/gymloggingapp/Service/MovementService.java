package com.gymloggingapp.gymloggingapp.Service;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;

import java.util.List;
import java.util.Optional;

public interface MovementService {
    MovementEntity save(MovementEntity movement);

    List<MovementEntity> findAll();

    boolean existsByID(Long id);

    Optional<MovementEntity> findbyID(Long id);

    MovementEntity partialUpdate(Long id, MovementEntity movement);

    void delete(Long id);
}
