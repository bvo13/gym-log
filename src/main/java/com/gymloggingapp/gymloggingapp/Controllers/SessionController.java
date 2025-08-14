package com.gymloggingapp.gymloggingapp.Controllers;

import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import com.gymloggingapp.gymloggingapp.Entities.UserEntity;
import com.gymloggingapp.gymloggingapp.Service.AuthenticationService;
import com.gymloggingapp.gymloggingapp.Service.SessionService;
import com.gymloggingapp.gymloggingapp.Service.UserService;
import com.gymloggingapp.gymloggingapp.dto.SessionDto;
import com.gymloggingapp.gymloggingapp.mappers.SessionMapper;
import com.gymloggingapp.gymloggingapp.util.References;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SessionController {

    private SessionService sessionService;
    private SessionMapper sessionMapper;
    private UserService userService;
    private AuthenticationService authenticationService;

    public SessionController(SessionService sessionService, SessionMapper sessionMapper, UserService userService,
                             AuthenticationService authenticationService) {
        this.sessionService = sessionService;
        this.sessionMapper = sessionMapper;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/users/me/sessions")
    public SessionDto createSession( @RequestBody SessionDto session){
        Long userId = authenticationService.getCurrentUserId();
        SessionEntity sessionEntity = sessionMapper.mapFrom(session);
        UserEntity user = userService.findOneUser(userId).orElseThrow(()-> new RuntimeException("User not found"));
        sessionEntity.setUser(user);
        References.setSessionParentReference(sessionEntity);
        SessionEntity savedSession = sessionService.save(sessionEntity);
        return sessionMapper.mapTo(savedSession);
    }


    @GetMapping(path = "/sessions")
    public List<SessionDto> findAllSessions(){
        List<SessionEntity> allSessions = sessionService.findAll();
        return allSessions.stream().map(sessionMapper::mapTo).collect(Collectors.toList());
    }


    @GetMapping(path = "/users/me/sessions")
    public List<SessionDto> findAllSessionsForUser(){
        Long userId = authenticationService.getCurrentUserId();
        List<SessionEntity> allSessions = sessionService.findByUser(userService.findOneUser(userId).orElseThrow(()->
                new UsernameNotFoundException("user with this id does not exist")
        ));
        return allSessions.stream().map(sessionMapper::mapTo).collect(Collectors.toList());
    }


    @GetMapping(path = "/users/me/sessions/{id}")
    public ResponseEntity<SessionDto> findOneSession(@PathVariable("id") Long id){

        Optional<SessionEntity> findSession = sessionService.findOneSession(id);
        return findSession.map(sessionEntity -> {
            SessionDto sessionDto = sessionMapper.mapTo(sessionEntity);
            return new ResponseEntity<>(sessionDto, HttpStatus.OK);}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping(path = "/users/me/sessions/{id}")
    public ResponseEntity<SessionDto> fullUpdate(
                                                 @PathVariable("id") Long id,
                                                 @RequestBody SessionDto sessionDto){
        if(!sessionService.existsbyID(id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = authenticationService.getCurrentUserId();
        sessionDto.setId(id);
        SessionEntity session = sessionMapper.mapFrom(sessionDto);
        UserEntity user = userService.findOneUser(userId).orElseThrow(()-> new RuntimeException("User not found"));
        session.setUser(user);
        References.setSessionParentReference(session);
        SessionEntity savedSession = sessionService.save(session);
        return new ResponseEntity<>(sessionMapper.mapTo(savedSession),HttpStatus.OK);
    }


    @PatchMapping(path = "/users/me/sessions/{id}")
    public ResponseEntity<SessionDto> partialUpdate(@PathVariable("id") Long id, @RequestBody SessionDto sessionDto){
        if(!sessionService.existsbyID(id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = authenticationService.getCurrentUserId();
        SessionEntity session = sessionMapper.mapFrom(sessionDto);
        UserEntity user = userService.findOneUser(userId).orElseThrow(()-> new RuntimeException("User not found"));
        session.setUser(user);
        References.setSessionParentReference(session);
        SessionEntity updatedSession = sessionService.partialUpdate(id, session);
        return new ResponseEntity<>(sessionMapper.mapTo(updatedSession), HttpStatus.OK);
    }


    @DeleteMapping(path = "/users/me/sessions/{id}")
    public ResponseEntity<SessionDto> delete(@PathVariable("userId") Long userId, @PathVariable("id") Long id){
        if(!sessionService.existsbyID(id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        sessionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
