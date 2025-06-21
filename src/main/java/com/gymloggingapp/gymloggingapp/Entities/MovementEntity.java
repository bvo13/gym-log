package com.gymloggingapp.gymloggingapp.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "movements")
public class MovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movement_id_seq")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private SessionEntity session;

    @OneToMany(mappedBy = "movement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SetEntity> sets;
}
