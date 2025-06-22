package com.gymloggingapp.gymloggingapp.mappers;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;
import com.gymloggingapp.gymloggingapp.Entities.SetEntity;
import com.gymloggingapp.gymloggingapp.dto.MovementDto;
import com.gymloggingapp.gymloggingapp.dto.SetDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovementMapper implements Mapper<MovementEntity, MovementDto> {

    private SetMapper setMapper;

    public MovementMapper(SetMapper setMapper) {
        this.setMapper = setMapper;
    }

    @Override
    public MovementDto mapTo(MovementEntity movementEntity) {
        List<SetDto> setDtos = null;
        if(movementEntity.getSets()!=null){
            setDtos = movementEntity.getSets().stream().map(setMapper::mapTo).toList();
        }
        return new MovementDto(movementEntity.getId(), movementEntity.getName(), setDtos);

    }

    @Override
    public MovementEntity mapFrom(MovementDto movementDto) {
        List<SetEntity> setEntities = null;
        if(movementDto.getSets()!=null){
            setEntities = movementDto.getSets().stream().map(setMapper::mapFrom).toList();
        }
        return new MovementEntity(movementDto.getId(), movementDto.getName(), setEntities);
    }
}
