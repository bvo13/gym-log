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

    @PreAuthorize("@authenticationService.checkAccess(#id)")
    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserInfoDto> getUser(@PathVariable("id") Long id){
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

    @PreAuthorize("@authenticationService.checkAccess(#id)")
    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<UserInfoDto> partialUpdate(@PathVariable("id") Long id, @RequestBody UserInfoDto userInfoDto){

        if(!(userService.exists(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserEntity userEntity = userMapper.mapFrom(userInfoDto);
        References.setUserParentReference(userEntity);
        UserEntity updatedUser = userService.partialUpdate(id, userEntity);
        return new ResponseEntity<>(userMapper.mapTo(updatedUser), HttpStatus.OK);
    }

    @PreAuthorize("@authenticationService.checkAccess(#id)")
    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<UserInfoDto> deleteUser(@PathVariable("id") Long id){
        if(!(userService.exists(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
