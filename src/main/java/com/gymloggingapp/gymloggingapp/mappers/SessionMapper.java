package com.gymloggingapp.gymloggingapp.mappers;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;
import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import com.gymloggingapp.gymloggingapp.dto.MovementDto;
import com.gymloggingapp.gymloggingapp.dto.SessionDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionMapper implements Mapper<SessionEntity, SessionDto> {
    private MovementMapper movementMapper;


    public SessionMapper(MovementMapper movementMapper) {
        this.movementMapper = movementMapper;
    }

    @Override
    public SessionDto mapTo(SessionEntity sessionEntity) {
        List<MovementDto> movementDtos = null;
        if(sessionEntity.getMovements()!=null){
            movementDtos = sessionEntity.getMovements().stream().map(movementMapper::mapTo).toList();
        }
        return new SessionDto(sessionEntity.getId(), sessionEntity.getDate(), movementDtos);
    }

    @Override
    public SessionEntity mapFrom(SessionDto sessionDto) {
        List<MovementEntity> movementEntities = null;
        if(sessionDto.getMovements()!=null){
            movementEntities = sessionDto.getMovements().stream().map(movementMapper::mapFrom).toList();
        }
        return new SessionEntity(sessionDto.getId(), sessionDto.getDate(), movementEntities);
    }
}
