package com.gymloggingapp.gymloggingapp.dto;

import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String name;

    private List<SessionDto> session;
}
