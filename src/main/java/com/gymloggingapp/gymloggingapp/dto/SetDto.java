package com.gymloggingapp.gymloggingapp.dto;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetDto {
    private Long id;


    private MovementDto movement;

    private Integer weight;
    private Integer reps;
    private Integer rir;
}
