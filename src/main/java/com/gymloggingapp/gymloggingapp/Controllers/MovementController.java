package com.gymloggingapp.gymloggingapp.Controllers;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;
import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import com.gymloggingapp.gymloggingapp.Service.AuthenticationService;
import com.gymloggingapp.gymloggingapp.Service.MovementService;
import com.gymloggingapp.gymloggingapp.Service.SessionService;
import com.gymloggingapp.gymloggingapp.dto.MovementDto;
import com.gymloggingapp.gymloggingapp.mappers.MovementMapper;
import com.gymloggingapp.gymloggingapp.util.References;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MovementController {

    private MovementService movementService;
    private MovementMapper movementMapper;
    private SessionService sessionService;
    private AuthenticationService authenticationService;

    public MovementController(MovementService movementService, MovementMapper movementMapper, SessionService sessionService,
                              AuthenticationService authenticationService) {
        this.movementService = movementService;
        this.movementMapper = movementMapper;
        this.sessionService = sessionService;
        this.authenticationService = authenticationService;
    }


    @PostMapping(path = "/users/me/sessions/{sessionId}/movements")
    public MovementDto createMovement(
                                      @PathVariable("sessionId") Long sessionId,
                                      @RequestBody MovementDto movement) {
        MovementEntity movementEntity = movementMapper.mapFrom(movement);
        SessionEntity session = sessionService.findOneSession(sessionId).orElseThrow(()-> new RuntimeException("Session not found"));
        movementEntity.setSession(session);
        References.setMovementParentReference(movementEntity);
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
        }).orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }


    @PutMapping(path = "/users/me/sessions/{sessionId}/movements/{id}")
    public ResponseEntity<MovementDto> fullUpdate(
                                                  @PathVariable("sessionId") Long sessionId,
                                                  @PathVariable("id") Long id,
                                                  @RequestBody MovementDto movementDto){
        if(!movementService.existsByID(id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        movementDto.setId(id);
        MovementEntity movement = movementMapper.mapFrom(movementDto);
        SessionEntity session = sessionService.findOneSession(sessionId).orElseThrow(()-> new RuntimeException("Session not found"));
        movement.setSession(session);
        References.setMovementParentReference(movement);
        MovementEntity updatedMovement = movementService.save(movement);
        return new ResponseEntity<>(movementMapper.mapTo(updatedMovement), HttpStatus.OK);
    }


    @PatchMapping(path = "/users/me/sessions/{sessionId}/movements/{id}")
    public ResponseEntity<MovementDto> partialUpdate(
                                                     @PathVariable("sessionId") Long sessionId,
                                                     @PathVariable("id") Long id,
                                                     @RequestBody MovementDto movementDto){
        if(!movementService.existsByID(id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        MovementEntity movement = movementMapper.mapFrom(movementDto);
        SessionEntity session = sessionService.findOneSession(sessionId).orElseThrow(()-> new RuntimeException("Session not found"));
        movement.setSession(session);
        References.setMovementParentReference(movement);
        MovementEntity updatedMovement = movementService.partialUpdate(id, movement);
        return new ResponseEntity<>(movementMapper.mapTo(updatedMovement),HttpStatus.OK);
    }


    @DeleteMapping(path = "users/me/sessions/{sessionId}/movements/{id}")
    public ResponseEntity<MovementDto> delete(@PathVariable("sessionId") Long sessionId, @PathVariable("id") Long id){
        if(!movementService.existsByID(id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        movementService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}