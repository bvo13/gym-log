package com.gymloggingapp.gymloggingapp.Repositories;

import com.gymloggingapp.gymloggingapp.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
