package com.gymloggingapp.gymloggingapp.Service;

import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import com.gymloggingapp.gymloggingapp.Entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface SessionService {
    SessionEntity save(SessionEntity session);

    List<SessionEntity> findAll();

    List<SessionEntity> findByUser(UserEntity user);

    Optional<SessionEntity> findOneSession(Long id);

    boolean existsbyID(Long id);

    SessionEntity partialUpdate(Long id, SessionEntity session);

    void delete(Long id);


}
