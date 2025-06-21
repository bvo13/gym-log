package com.gymloggingapp.gymloggingapp.Controllers;

import com.gymloggingapp.gymloggingapp.Entities.UserEntity;
import com.gymloggingapp.gymloggingapp.Service.UserService;
import com.gymloggingapp.gymloggingapp.dto.UserDto;
import com.gymloggingapp.gymloggingapp.mappers.UserMapper;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {


    private UserService userService;

    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/users")
    public UserDto createUser(@RequestBody UserDto user){
        UserEntity userEntity = userMapper.mapFrom(user);
        UserEntity savedUser = userService.save(userEntity);
        return userMapper.mapTo(savedUser);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id){
        Optional<UserEntity> findUser = userService.findOneUser(id);
        return findUser.map(userEntity -> {UserDto userDto = userMapper.mapTo(userEntity);
        return new ResponseEntity<>(userDto, HttpStatus.OK);}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping(path = "/users")
    public List<UserDto> listUsers(){
        List<UserEntity> allUsers = userService.findAll();
        return allUsers.stream().map(userMapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> fullUpdate(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        if(!(userService.exists(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userDto.setId(id);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUser = userService.save(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(updatedUser), HttpStatus.OK);

    }

    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> partialUpdate(@PathVariable("id") Long id, @RequestBody UserDto userDto){

        if(!(userService.exists(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUser = userService.partialUpdate(id, userEntity);
        return new ResponseEntity<>(userMapper.mapTo(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long id){
        if(!(userService.exists(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
