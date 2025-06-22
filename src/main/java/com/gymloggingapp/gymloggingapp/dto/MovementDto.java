package com.gymloggingapp.gymloggingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementDto {
    private Long id;

    private String name;


    private List<SetDto> sets;

}
