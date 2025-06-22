package com.gymloggingapp.gymloggingapp.mappers;

import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import com.gymloggingapp.gymloggingapp.Entities.UserEntity;
import com.gymloggingapp.gymloggingapp.dto.SessionDto;
import com.gymloggingapp.gymloggingapp.dto.UserDto;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@org.mapstruct.Mapper(componentModel = "spring")
public class UserMapper implements Mapper<UserEntity, UserDto>{

    private SessionMapper sessionMapper;

    public UserMapper(SessionMapper sessionMapper) {
        this.sessionMapper = sessionMapper;
    }

    @Override
    public UserDto mapTo(UserEntity userEntity) {
        List<SessionDto> sessionDtos = null;
        if(userEntity.getSessions()!=null){
            sessionDtos = userEntity.getSessions().stream().map(sessionMapper::mapTo).toList();
        }
        return new UserDto(userEntity.getId(), userEntity.getName(), sessionDtos);
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        List<SessionEntity> sessionEntities = null;
        if(userDto.getSessions()!= null){
            sessionEntities = userDto.getSessions().stream().map(sessionMapper::mapFrom).toList();
        }
        return new UserEntity(userDto.getId(), userDto.getName(), sessionEntities);
    }
}
