package com.gymloggingapp.gymloggingapp.Repositories;

import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
}
