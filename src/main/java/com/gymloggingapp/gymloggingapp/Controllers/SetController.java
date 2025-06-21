package com.gymloggingapp.gymloggingapp.Controllers;

import com.gymloggingapp.gymloggingapp.Entities.SetEntity;
import com.gymloggingapp.gymloggingapp.Service.SetService;
import com.gymloggingapp.gymloggingapp.dto.SetDto;
import com.gymloggingapp.gymloggingapp.mappers.SetMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SetController {
    private SetService setService;
    private SetMapper setMapper;

    public SetController(SetService setService, SetMapper setMapper) {
        this.setService = setService;
        this.setMapper = setMapper;
    }

    @PostMapping(path = "/sets")
    public SetDto createSet(@RequestBody SetDto set){
        SetEntity setEntity = setMapper.mapFrom(set);
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

    @PutMapping(path = "/sets/{id}")
    public ResponseEntity<SetDto> fullUpdate(@PathVariable("id") Long id, @RequestBody SetDto setDto){
        if(!setService.existsByID(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        setDto.setId(id);
        SetEntity set = setMapper.mapFrom(setDto);
        SetEntity updatedSet = setService.save(set);
        return new ResponseEntity<>(setMapper.mapTo(updatedSet),HttpStatus.OK);
    }

    @PatchMapping(path = "/sets/{id}")
    public ResponseEntity<SetDto> partialUpdate(@PathVariable("id") Long id, @RequestBody SetDto setDto){
        if(!setService.existsByID(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SetEntity setEntity = setMapper.mapFrom(setDto);
        SetEntity updatedSet = setService.partialUpdate(id, setEntity);
        return new ResponseEntity<>(setMapper.mapTo(updatedSet),HttpStatus.OK);
    }

    @DeleteMapping(path = "/sets/{id}")
    public ResponseEntity<SetDto> delete(@PathVariable("id") Long id){
        if(!setService.existsByID(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        setService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
