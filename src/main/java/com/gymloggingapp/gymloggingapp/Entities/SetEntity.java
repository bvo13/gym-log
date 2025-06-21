package com.gymloggingapp.gymloggingapp.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sets")
public class SetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "set_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movement_id")
    private MovementEntity movement;

    private Integer weight;
    private Integer reps;
    private Integer rir;
}
