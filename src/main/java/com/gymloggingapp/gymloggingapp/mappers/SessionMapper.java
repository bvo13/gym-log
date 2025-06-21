package com.gymloggingapp.gymloggingapp.mappers;

import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import com.gymloggingapp.gymloggingapp.dto.SessionDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper implements Mapper<SessionEntity, SessionDto> {
    private ModelMapper modelMapper;

    public SessionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public SessionDto mapTo(SessionEntity sessionEntity) {
        return modelMapper.map(sessionEntity, SessionDto.class);
    }

    @Override
    public SessionEntity mapFrom(SessionDto sessionDto) {
        return modelMapper.map(sessionDto, SessionEntity.class);
    }
}
