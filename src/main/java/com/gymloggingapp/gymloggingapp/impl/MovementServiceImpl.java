package com.gymloggingapp.gymloggingapp.impl;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;
import com.gymloggingapp.gymloggingapp.Repositories.MovementRepository;
import com.gymloggingapp.gymloggingapp.Service.MovementService;
import com.gymloggingapp.gymloggingapp.util.References;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovementServiceImpl implements MovementService {
    private MovementRepository movementRepository;

    public MovementServiceImpl(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public MovementEntity save(MovementEntity movement){
        return movementRepository.save(movement);
    }

    @Override
    public List<MovementEntity> findAll() {
        return StreamSupport.stream(movementRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public boolean existsByID(Long id) {
        return movementRepository.existsById(id);
    }

    @Override
    public Optional<MovementEntity> findbyID(Long id) {
        return movementRepository.findById(id);
    }

    @Override
    public MovementEntity partialUpdate(Long id, MovementEntity movement) {
        movement.setId(id);
        return movementRepository.findById(id).map(existingMovement -> {
            Optional.ofNullable(movement.getName()).ifPresent(existingMovement::setName);
            Optional.ofNullable(movement.getSets()).ifPresent(set-> {
                existingMovement.setSets(set);
                References.setMovementParentReference(existingMovement);
            });
            Optional.ofNullable(movement.getSession()).ifPresent(existingMovement::setSession);
            return movementRepository.save(existingMovement);
        }).orElseThrow(()-> new RuntimeException("Movement does not exist"));
    }

    @Override
    public void delete(Long id) {
        movementRepository.deleteById(id);
    }
}
