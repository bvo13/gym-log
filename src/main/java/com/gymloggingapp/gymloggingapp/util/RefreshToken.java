package com.gymloggingapp.gymloggingapp.util;

import com.gymloggingapp.gymloggingapp.Entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_id_seq")
    Long id;

    String jwtToken;

    @ManyToOne
    UserEntity user;

    Instant expirationDate;


}
