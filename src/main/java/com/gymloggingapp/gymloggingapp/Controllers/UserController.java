package com.gymloggingapp.gymloggingapp.Controllers;

import com.gymloggingapp.gymloggingapp.Entities.UserEntity;
import com.gymloggingapp.gymloggingapp.Service.AuthenticationService;
import com.gymloggingapp.gymloggingapp.Service.UserService;
import com.gymloggingapp.gymloggingapp.dto.UserInfoDto;
import com.gymloggingapp.gymloggingapp.mappers.UserMapper;
import com.gymloggingapp.gymloggingapp.util.References;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {


    private UserService userService;

    private UserMapper userMapper;

    private AuthenticationService authenticationService;

    public UserController(UserService userService, UserMapper userMapper, AuthenticationService authenticationService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
    }


    @GetMapping(path = "/users/me")
    public ResponseEntity<UserInfoDto> getUser(){
        Long id = authenticationService.getCurrentUserId();
        Optional<UserEntity> findUser = userService.findOneUser(id);
        return findUser.map(userEntity -> {
            UserInfoDto userInfoDto = userMapper.mapTo(userEntity);
        return new ResponseEntity<>(userInfoDto, HttpStatus.OK);}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping(path = "/users")
    public List<UserInfoDto> listUsers(){
        List<UserEntity> allUsers = userService.findAll();
        return allUsers.stream().map(userMapper::mapTo).collect(Collectors.toList());
    }


    @PatchMapping(path = "/users/me")
    public ResponseEntity<UserInfoDto> partialUpdate(@RequestBody UserInfoDto userInfoDto){

        Long id = authenticationService.getCurrentUserId();
        if(!(userService.exists(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserEntity userEntity = userMapper.mapFrom(userInfoDto);
        References.setUserParentReference(userEntity);
        UserEntity updatedUser = userService.partialUpdate(id, userEntity);
        return new ResponseEntity<>(userMapper.mapTo(updatedUser), HttpStatus.OK);
    }


    @DeleteMapping(path = "/users/me")
    public ResponseEntity<UserInfoDto> deleteUser(){
        Long id = authenticationService.getCurrentUserId();
        if(!(userService.exists(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
