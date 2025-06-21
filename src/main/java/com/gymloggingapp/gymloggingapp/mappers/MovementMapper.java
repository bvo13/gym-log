package com.gymloggingapp.gymloggingapp.mappers;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;
import com.gymloggingapp.gymloggingapp.dto.MovementDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MovementMapper implements Mapper<MovementEntity, MovementDto> {
    private ModelMapper modelMapper;

    public MovementMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MovementDto mapTo(MovementEntity movementEntity) {
        return modelMapper.map(movementEntity, MovementDto.class);

    }

    @Override
    public MovementEntity mapFrom(MovementDto movementDto) {
        return modelMapper.map(movementDto, MovementEntity.class);
    }
}
