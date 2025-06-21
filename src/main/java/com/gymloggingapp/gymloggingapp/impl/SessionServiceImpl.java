package com.gymloggingapp.gymloggingapp.impl;

import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import com.gymloggingapp.gymloggingapp.Repositories.SessionRepository;
import com.gymloggingapp.gymloggingapp.Service.SessionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class SessionServiceImpl implements SessionService {
    private SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public SessionEntity save(SessionEntity session){
        return sessionRepository.save(session);
    }

    @Override
    public List<SessionEntity> findAll() {
        return StreamSupport.stream(sessionRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public Optional<SessionEntity> findOneUser(Long id) {
        return sessionRepository.findById(id);
    }

    @Override
    public boolean existsbyID(Long id) {
        return sessionRepository.existsById(id);
    }

    @Override
    public SessionEntity partialUpdate(Long id, SessionEntity session) {
        session.setId(id);
        return sessionRepository.findById(id).map(existingSession -> {
            Optional.ofNullable(session.getDate()).ifPresent(existingSession::setDate);
            Optional.ofNullable(session.getUser()).ifPresent(existingSession::setUser);
            Optional.ofNullable(session.getMovements()).ifPresent(existingSession::setMovements);
            return sessionRepository.save(existingSession);
        }).orElseThrow(()->new RuntimeException("Session not Found"));
    }

    @Override
    public void delete(Long id) {
        sessionRepository.deleteById(id);
    }
}
