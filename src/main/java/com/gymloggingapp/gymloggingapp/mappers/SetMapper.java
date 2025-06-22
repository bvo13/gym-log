package com.gymloggingapp.gymloggingapp.mappers;

import com.gymloggingapp.gymloggingapp.Entities.SetEntity;
import com.gymloggingapp.gymloggingapp.dto.SetDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SetMapper implements Mapper<SetEntity, SetDto> {

    @Override
    public SetDto mapTo(SetEntity setEntity) {
        return new SetDto(setEntity.getId(), setEntity.getWeight(),
                setEntity.getReps(), setEntity.getRir());
    }
    @Override
    public SetEntity mapFrom(SetDto setDto) {
        return new SetEntity(setDto.getId(), setDto.getWeight(), setDto.getReps(), setDto.getRir());
    }
}
