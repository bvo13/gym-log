package com.gymloggingapp.gymloggingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
    private Long id;

    private LocalDate date;

    private List<MovementDto> movements;


}
