package com.example.demo.controllers;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(path = "/health-check")
    public String healthCheck(){
        return "Hello";
    }

    @GetMapping()
    public ResponseEntity<List<UserDto> > getAllUser(){
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        Optional<UserDto> user=userService.getUserById(userId);
        return  user.map(user1-> ResponseEntity.ok(user1)).orElseThrow(()->new ResourceNotFoundException("Element Not Found"));
    }

    @PostMapping()
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userDto){
        UserDto newUser =userService.addNewUser(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId , @RequestBody UserDto userDetails){
        return ResponseEntity.ok(userService.updateUser(userId,userDetails));
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long userId){
        boolean isDeleted=userService.deleteUser(userId);
        if(!isDeleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(true);
    }

    @PatchMapping(path = "/{userId}")
        public ResponseEntity<UserDto> updatePartially(@RequestBody Map<String,Object> update, @PathVariable Long userId){
        UserDto user= userService.updateUserPartially(userId,update);
        if(user==null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

}
