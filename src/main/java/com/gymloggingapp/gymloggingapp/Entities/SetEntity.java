package com.gymloggingapp.gymloggingapp.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @JsonBackReference
    private MovementEntity movement;

    private Integer weight;
    private Integer reps;
    private Integer rir;

    public SetEntity(Long id, Integer weight, Integer rir, Integer reps) {
        this.id = id;
        this.weight = weight;
        this.rir = rir;
        this.reps = reps;
        this.movement = null;
    }
}
