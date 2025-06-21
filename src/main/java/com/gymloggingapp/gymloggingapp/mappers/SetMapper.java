package com.gymloggingapp.gymloggingapp.mappers;

import com.gymloggingapp.gymloggingapp.Entities.SetEntity;
import com.gymloggingapp.gymloggingapp.dto.SetDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SetMapper implements Mapper<SetEntity, SetDto> {
    private ModelMapper modelMapper;

    public SetMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SetDto mapTo(SetEntity setEntity) {
        return modelMapper.map(setEntity, SetDto.class);
    }
    @Override
    public SetEntity mapFrom(SetDto setDto) {
        return modelMapper.map(setDto, SetEntity.class);
    }
}
