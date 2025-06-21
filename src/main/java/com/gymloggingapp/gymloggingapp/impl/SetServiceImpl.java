package com.gymloggingapp.gymloggingapp.impl;

import com.gymloggingapp.gymloggingapp.Entities.SetEntity;
import com.gymloggingapp.gymloggingapp.Repositories.SetRepository;
import com.gymloggingapp.gymloggingapp.Service.SetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SetServiceImpl implements SetService {
    private SetRepository setRepository;

    public SetServiceImpl(SetRepository setRepository) {
        this.setRepository = setRepository;
    }

    public SetEntity save(SetEntity set){
        return setRepository.save(set);
    }

    @Override
    public List<SetEntity> findAll() {
        return StreamSupport.stream(setRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<SetEntity> findByID(Long id) {
        return setRepository.findById(id);
    }

    @Override
    public boolean existsByID(Long id) {
        return setRepository.existsById(id);
    }

    @Override
    public SetEntity partialUpdate(Long id, SetEntity setEntity) {
        setEntity.setId(id);
        return setRepository.findById(id).map(set ->{
            Optional.ofNullable(setEntity.getReps()).ifPresent(set::setReps);
            Optional.ofNullable(setEntity.getRir()).ifPresent(set::setRir);
            Optional.ofNullable(setEntity.getWeight()).ifPresent(set::setWeight);
            Optional.ofNullable(setEntity.getMovement()).ifPresent(set::setMovement);
            return setRepository.save(set);
        } ).orElseThrow(()-> new RuntimeException("Set does not exist"));
    }

    @Override
    public void delete(Long id) {
        setRepository.deleteById(id);
    }
}
