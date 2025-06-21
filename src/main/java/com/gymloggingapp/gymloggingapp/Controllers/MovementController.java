package com.gymloggingapp.gymloggingapp.Controllers;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;
import com.gymloggingapp.gymloggingapp.Service.MovementService;
import com.gymloggingapp.gymloggingapp.dto.MovementDto;
import com.gymloggingapp.gymloggingapp.mappers.MovementMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MovementController {

    private MovementService movementService;
    private MovementMapper movementMapper;

    public MovementController(MovementService movementService, MovementMapper movementMapper) {
        this.movementService = movementService;
        this.movementMapper = movementMapper;
    }

    @PostMapping(path = "/movements")
    public MovementDto createMovement(@RequestBody MovementDto movement) {
        MovementEntity movementEntity = movementMapper.mapFrom(movement);
        MovementEntity savedMovement = movementService.save(movementEntity);
        return movementMapper.mapTo(savedMovement);
    }

    @GetMapping(path = "/movements")
    public List<MovementDto> getAllMovements() {
        List<MovementEntity> allMovements = movementService.findAll();
        return allMovements.stream().map(movementMapper::mapTo).collect(Collectors.toList());

    }

    @GetMapping(path = "/movements/{id}")
    public ResponseEntity<MovementDto> findOneMovement(@PathVariable("id") Long id) {
        Optional<MovementEntity> foundMovement = movementService.findbyID(id);
        return foundMovement.map(movementEntity -> {
            MovementDto movementDto = movementMapper.mapTo(movementEntity);
            return new ResponseEntity<>(movementDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/movements/{id}")
    public ResponseEntity<MovementDto> fullUpdate(@PathVariable("id") Long id, @RequestBody MovementDto movementDto){
        if(!movementService.existsByID(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        movementDto.setId(id);
        MovementEntity movement = movementMapper.mapFrom(movementDto);
        MovementEntity updatedMovement = movementService.save(movement);
        return new ResponseEntity<>(movementMapper.mapTo(updatedMovement), HttpStatus.OK);
    }

    @PatchMapping(path = "/movements/{id}")
    public ResponseEntity<MovementDto> partialUpdate(@PathVariable("id") Long id, @RequestBody MovementDto movementDto){
        if(!movementService.existsByID(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MovementEntity movement = movementMapper.mapFrom(movementDto);
        MovementEntity updatedMovement = movementService.partialUpdate(id, movement);
        return new ResponseEntity<>(movementMapper.mapTo(updatedMovement),HttpStatus.OK);
    }

    @DeleteMapping(path = "/movements/{id}")
    public ResponseEntity<MovementDto> delete(@PathVariable("id") Long id){
        if(!movementService.existsByID(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        movementService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}