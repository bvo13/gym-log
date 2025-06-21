package com.gymloggingapp.gymloggingapp.dto;

import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import com.gymloggingapp.gymloggingapp.Entities.SetEntity;
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

    private SessionDto session;

    private List<SetDto> set;
}
