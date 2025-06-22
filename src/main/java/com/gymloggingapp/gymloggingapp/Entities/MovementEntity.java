package com.gymloggingapp.gymloggingapp.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @JsonBackReference
    private SessionEntity session;

    @OneToMany(mappedBy = "movement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SetEntity> sets;

    public MovementEntity(Long id, String name, List<SetEntity> sets) {
        this.id = id;
        this.name = name;
        this.sets = sets;
        this.session = null;
    }
}
