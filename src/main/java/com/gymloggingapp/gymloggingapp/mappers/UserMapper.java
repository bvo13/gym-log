package com.gymloggingapp.gymloggingapp.mappers;

import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import com.gymloggingapp.gymloggingapp.Entities.UserEntity;
import com.gymloggingapp.gymloggingapp.dto.SessionDto;
import com.gymloggingapp.gymloggingapp.dto.UserInfoDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@org.mapstruct.Mapper(componentModel = "spring")
public class UserMapper implements Mapper<UserEntity, UserInfoDto>{

    private SessionMapper sessionMapper;

    public UserMapper(SessionMapper sessionMapper) {
        this.sessionMapper = sessionMapper;
    }

    @Override
    public UserInfoDto mapTo(UserEntity userEntity) {
        List<SessionDto> sessionDtos = null;
        if(userEntity.getSessions()!=null){
            sessionDtos = userEntity.getSessions().stream().map(sessionMapper::mapTo).toList();
        }
        return new UserInfoDto(userEntity.getId(), userEntity.getName(), sessionDtos);
    }

    @Override
    public UserEntity mapFrom(UserInfoDto userInfoDto) {
        List<SessionEntity> sessionEntities = null;
        if(userInfoDto.getSessions()!= null){
            sessionEntities = userInfoDto.getSessions().stream().map(sessionMapper::mapFrom).toList();
        }
        return new UserEntity(userInfoDto.getId(), userInfoDto.getName(), sessionEntities);
    }
}
