package com.gymloggingapp.gymloggingapp.Controllers;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;
import com.gymloggingapp.gymloggingapp.Entities.SetEntity;
import com.gymloggingapp.gymloggingapp.Service.AuthenticationService;
import com.gymloggingapp.gymloggingapp.Service.MovementService;
import com.gymloggingapp.gymloggingapp.Service.SetService;
import com.gymloggingapp.gymloggingapp.dto.SetDto;
import com.gymloggingapp.gymloggingapp.mappers.SetMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SetController {
    private SetService setService;
    private SetMapper setMapper;
    private MovementService movementService;
    private AuthenticationService authenticationService;

    public SetController(SetService setService, SetMapper setMapper, MovementService movementService
    ,AuthenticationService authenticationService) {
        this.setService = setService;
        this.setMapper = setMapper;
        this.movementService = movementService;
        this.authenticationService = authenticationService;
    }


    @PostMapping(path = "/users/me/sessions/{sessionId}/movements/{movementId}/sets")
    public SetDto createSet(
                            @PathVariable("sessionId") Long sessionId,
                            @PathVariable("movementId") Long movementId,
                            @RequestBody SetDto set){
        SetEntity setEntity = setMapper.mapFrom(set);
        MovementEntity movementEntity = movementService.findbyID(movementId)
                .orElseThrow(()-> new RuntimeException("Movement not found"));
        setEntity.setMovement(movementEntity);
        SetEntity savedSet = setService.save(setEntity);
        return setMapper.mapTo(savedSet);
    }

    @GetMapping(path = "/sets")
    public List<SetDto> findAllSets(){
        List<SetEntity> allSets = setService.findAll();
        return allSets.stream().map(setMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/sets/{id}")
    public ResponseEntity<SetDto> findOne(@PathVariable("id") Long id){
        Optional<SetEntity> foundSet = setService.findByID(id);
        return foundSet.map(existingSet-> {
            SetDto setDto = setMapper.mapTo(existingSet);
            return new ResponseEntity<>(setDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/users/me/sessions/{sessionId}/movements/{movementId}/sets/{id}")
    public ResponseEntity<SetDto> fullUpdate(
                                             @PathVariable("sessionId") Long sessionId,
                                             @PathVariable("movementId") Long movementId,
                                             @PathVariable("id") Long id, @RequestBody SetDto setDto){
        if(!setService.existsByID(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        setDto.setId(id);
        SetEntity set = setMapper.mapFrom(setDto);
        MovementEntity movementEntity = movementService.findbyID(movementId).orElseThrow(()-> new RuntimeException("Movement not found"));
        set.setMovement(movementEntity);
        SetEntity updatedSet = setService.save(set);
        return new ResponseEntity<>(setMapper.mapTo(updatedSet),HttpStatus.OK);
    }


    @PatchMapping(path = "/users/me/sessions/{sessionId}/movements/{movementId}/sets/{id}")
    public ResponseEntity<SetDto> partialUpdate(@PathVariable("userId") Long userId,
                                                @PathVariable("sessionId") Long sessionId,
                                                @PathVariable("movementId") Long movementId,
                                                @PathVariable("id") Long id, @RequestBody SetDto setDto){
        if(!setService.existsByID(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SetEntity setEntity = setMapper.mapFrom(setDto);
        MovementEntity movementEntity = movementService.findbyID(movementId).orElseThrow(()-> new RuntimeException("Movement not found"));
        setEntity.setMovement(movementEntity);
        SetEntity updatedSet = setService.partialUpdate(id, setEntity);
        return new ResponseEntity<>(setMapper.mapTo(updatedSet),HttpStatus.OK);
    }


    @DeleteMapping(path = "/users/me/sessions/{sessionId}/movements/{movementId}/sets/{id}")
    public ResponseEntity<SetDto> delete(@PathVariable("id") Long id){
        if(!setService.existsByID(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        setService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
