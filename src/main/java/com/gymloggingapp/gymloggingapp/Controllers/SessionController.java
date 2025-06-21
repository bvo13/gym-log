package com.gymloggingapp.gymloggingapp.Controllers;

import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import com.gymloggingapp.gymloggingapp.Service.SessionService;
import com.gymloggingapp.gymloggingapp.dto.SessionDto;
import com.gymloggingapp.gymloggingapp.mappers.SessionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SessionController {

    private SessionService sessionService;
    private SessionMapper sessionMapper;

    public SessionController(SessionService sessionService, SessionMapper sessionMapper) {
        this.sessionService = sessionService;
        this.sessionMapper = sessionMapper;
    }

    @PostMapping(path = "/sessions")
    public SessionDto createSession(@RequestBody SessionDto session){
        SessionEntity sessionEntity = sessionMapper.mapFrom(session);
        SessionEntity savedSession = sessionService.save(sessionEntity);
        return sessionMapper.mapTo(savedSession);
    }

    @GetMapping(path = "/sessions")
    public List<SessionDto> findAllSessions(){
        List<SessionEntity> allSessions = sessionService.findAll();
        return allSessions.stream().map(sessionMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/sessions/{id}")
    public ResponseEntity<SessionDto> findOneSession(@PathVariable("id") Long id){
        Optional<SessionEntity> findSession = sessionService.findOneUser(id);
        return findSession.map(sessionEntity -> {
            SessionDto sessionDto = sessionMapper.mapTo(sessionEntity);
            return new ResponseEntity<>(sessionDto, HttpStatus.OK);}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/sessions/{id}")
    public ResponseEntity<SessionDto> fullUpdate(@PathVariable("id") Long id, @RequestBody SessionDto sessionDto){
        if(!sessionService.existsbyID(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        sessionDto.setId(id);
        SessionEntity session = sessionMapper.mapFrom(sessionDto);
        SessionEntity savedSession = sessionService.save(session);
        return new ResponseEntity<>(sessionMapper.mapTo(savedSession),HttpStatus.OK);
    }

    @PatchMapping(path = "/sessions/{id}")
    public ResponseEntity<SessionDto> partialUpdate(@PathVariable("id") Long id, @RequestBody SessionDto sessionDto){
        if(!sessionService.existsbyID(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SessionEntity session = sessionMapper.mapFrom(sessionDto);
        SessionEntity updatedSession = sessionService.partialUpdate(id, session);
        return new ResponseEntity<>(sessionMapper.mapTo(updatedSession), HttpStatus.OK);
    }

    @DeleteMapping(path = "/sessions/{id}")
    public ResponseEntity<SessionDto> delete(@PathVariable("id") Long id){
        if(!sessionService.existsbyID(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        sessionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
