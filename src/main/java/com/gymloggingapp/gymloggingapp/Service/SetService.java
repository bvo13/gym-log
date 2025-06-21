package com.gymloggingapp.gymloggingapp.Service;

import com.gymloggingapp.gymloggingapp.Entities.SetEntity;

import java.util.List;
import java.util.Optional;


public interface SetService {
    SetEntity save(SetEntity set);

    List<SetEntity> findAll();

    Optional<SetEntity> findByID(Long id);

    boolean existsByID(Long id);

    SetEntity partialUpdate(Long id, SetEntity setEntity);

    void delete(Long id);
}
